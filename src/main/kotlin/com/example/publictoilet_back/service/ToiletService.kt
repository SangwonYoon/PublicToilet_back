package com.example.publictoilet_back.service

import com.example.publictoilet_back.dto.ToiletLocationDto
import com.example.publictoilet_back.dto.ToiletInfoDto
import com.example.publictoilet_back.repository.StatisticsRepository
import com.example.publictoilet_back.repository.ToiletRepository
import org.springframework.stereotype.Service
import kotlin.math.*

@Service
class ToiletService(val toiletRepository: ToiletRepository, val statisticsRepository: StatisticsRepository) {
    fun findById(id : Long) : ToiletLocationDto {
        val entity = toiletRepository.findById(id).orElseThrow{
            java.lang.IllegalArgumentException("해당 화장실은 존재하지 않습니다. id=$id")
        }

        return ToiletLocationDto(entity)
    }

    fun findInfo(id: Long) : ToiletInfoDto {
        val entity = toiletRepository.findById(id).orElseThrow{
            IllegalArgumentException("해당 화장실은 존재하지 않습니다. id=$id")
        }

        val statistics = statisticsRepository.findById(id).orElseThrow{
            IllegalArgumentException("해당 화장실은 존재하지 않습니다. id=$id")
        }

        return ToiletInfoDto(statistics, entity)
    }

    fun findNearToilet(latitude : Double, longitude : Double, range : Int) : MutableList<ToiletLocationDto>{
        val toilets = toiletRepository.findAll() // TODO findAll()을 조건문 쿼리로 수정 필요
        val result = mutableListOf<ToiletLocationDto>()
        for (toilet in toilets){
            val distance = (getDistance(latitude, longitude, toilet.latitude, toilet.longitude)).toDouble() / 1000
            if(distance <= range) {
                val statistics = statisticsRepository.findById(toilet.id!!).orElseThrow {
                    IllegalArgumentException("해당 화장실은 존재하지 않습니다. id=${toilet.id}")
                }
                val temp = ToiletLocationDto(toilet, distance, statistics)
                result.add(temp)
            }
        }

        val comparator = compareBy<ToiletLocationDto> { it.distance }
        result.sortWith(comparator) // 거리 기준으로 리스트 정렬

        return result
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

    companion object{
        const val R =  6372.8 * 1000
    }
}