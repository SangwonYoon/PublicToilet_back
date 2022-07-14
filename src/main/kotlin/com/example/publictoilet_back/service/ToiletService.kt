package com.example.publictoilet_back.service

import com.example.publictoilet_back.dto.ToiletInfoDto
import com.example.publictoilet_back.dto.ToiletSaveDto
import com.example.publictoilet_back.dto.ToiletUpdateDto
import com.example.publictoilet_back.entity.Statistics
import com.example.publictoilet_back.entity.Toilet
import com.example.publictoilet_back.repository.StatisticsRepository
import com.example.publictoilet_back.repository.ToiletRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import javax.transaction.Transactional
import kotlin.math.*

@Service
class ToiletService(val toiletRepository: ToiletRepository, val statisticsRepository: StatisticsRepository) {

    fun saveToilet(toiletSaveDto: ToiletSaveDto) : Long?{
        val savedToilet = toiletRepository.save(toiletSaveDto.toEntity())
        statisticsRepository.save(Statistics(toilet = savedToilet))
        return savedToilet.id
    }

    fun findById(id : Long) : ToiletInfoDto {
        val entity = toiletRepository.findById(id).orElseThrow{
            java.lang.IllegalArgumentException("해당 화장실은 존재하지 않습니다. id=$id")
        }

        return ToiletInfoDto(entity)
    }

    @Cacheable(value = ["toilet"], key = "#id", cacheManager = "cacheManager")
    fun findInfo(id: Long) : ToiletInfoDto {
        val entity = toiletRepository.findById(id).orElseThrow{
            IllegalArgumentException("해당 화장실은 존재하지 않습니다. id=$id")
        }

        val statistics = findOrCreateStatistics(entity)

        return ToiletInfoDto(statistics, entity)
    }

    fun findNearToilet(latitude : Double, longitude : Double, range : Int) : MutableList<ToiletInfoDto>{
        val result = mutableListOf<ToiletInfoDto>()
        val toiletInfoProjections = toiletRepository.findNearToilet(latitude, longitude, range) ?: return result
        for (toiletInfoProjection in toiletInfoProjections){
            val toiletInfoDto = ToiletInfoDto(toiletInfoProjection)
            result.add(toiletInfoDto)
        }

        return result
    }

    @Transactional
    fun updateToiletInfo(id : Long, toiletUpdateDto: ToiletUpdateDto) : Long?{
        val toilet = toiletRepository.findById(id).orElseThrow {
            IllegalArgumentException("해당 화장실은 존재하지 않습니다. id=$id")
        }
        toilet.update(toiletUpdateDto)
        return id
    }

    /**
     * 두 좌표의 거리를 계산한다.
     *
     * @param lat1 위도1
     * @param lon1 경도1
     * @param lat2 위도2
     * @param lon2 경도2
     * @return 두 좌표의 거리(m)
     */
    fun getDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Int {
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = sin(dLat / 2).pow(2.0) + sin(dLon / 2).pow(2.0) * cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2))
        val c = 2 * asin(sqrt(a))
        return (R * c).toInt()
    }

    fun findOrCreateStatistics(toilet : Toilet) : Statistics{
        val findStatistics = statisticsRepository.findByToiletId(toilet.id)

        return if(findStatistics.isPresent){
            findStatistics.get()
        }else{
            statisticsRepository.save(Statistics(toilet = toilet))
        }
    }

    companion object{
        const val R =  6372.8 * 1000
    }
}