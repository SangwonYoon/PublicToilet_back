package com.example.publictoilet_back.service

import com.example.publictoilet_back.dto.*
import com.example.publictoilet_back.repository.ReviewRepository
import org.apache.commons.logging.Log
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.springframework.cache.annotation.Cacheable
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import javax.transaction.Transactional

@Service
class AppService(val reviewService: ReviewService, val toiletService: ToiletService) {

    @Transactional
    fun createToiletData() : Boolean{
        return toiletService.createToiletData()
    }

    @Transactional
    fun saveToilet(toiletSaveDto: ToiletSaveDto) : Long?{
        return toiletService.saveToilet(toiletSaveDto)
    }

    fun findToiletById(id : Long) : ToiletInfoDto{
        return toiletService.findById(id)
    }

    fun findToiletInfo(id : Long) : ToiletInfoDto? {
        return toiletService.findInfo(id)
    }

    fun findNearToilet(latitude : Double, longitude : Double, range : Int) : MutableList<ToiletInfoDto>{
        return toiletService.findNearToilet(latitude, longitude, range)
    }

    fun findReviewByToiletId(toilet_id : Long) : MutableList<ReviewResponseDto>{
        return reviewService.findByToiletId(toilet_id)
    }

    @Transactional
    fun saveReview(toilet_id : Long, reviewRequestDto : ReviewRequestDto) : Long? {
        return reviewService.save(toilet_id, reviewRequestDto)
    }

    @Transactional
    fun updateToiletInfo(id : Long, toiletUpdateDto: ToiletUpdateDto) : Long?{
        return toiletService.updateToiletInfo(id, toiletUpdateDto)
    }
}