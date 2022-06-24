package com.example.publictoilet_back.service.toilet

import com.example.publictoilet_back.domain.toilet.Toilet
import com.example.publictoilet_back.domain.toilet.ToiletRepository
import com.example.publictoilet_back.web.dto.ToiletResponseDto
import org.springframework.stereotype.Service
import kotlin.math.*

@Service
class ToiletService(val toiletRepository: ToiletRepository) {
    fun findById(id : Long) : ToiletResponseDto{
        val entity = toiletRepository.findById(id).orElseThrow{
            java.lang.IllegalArgumentException("해당 화장실은 존재하지 않습니다. id=$id")
        }

        return ToiletResponseDto(entity)
    }

    fun findNearToilet(latitude : Double, longitude : Double, range : Int) : MutableList<Toilet>{
        val toilets =  toiletRepository.findAll()
        val result = mutableListOf<Toilet>()
        for (toilet in toilets){
            if(toilet.latitude == null || toilet.longitude == null) continue
            val distance = getDistance(latitude, longitude, toilet.latitude, toilet.longitude) / 1000
            if(distance <= range) result.add(toilet)
        }
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