package com.example.publictoilet_back.service

import com.example.publictoilet_back.dto.ReviewRequestDto
import com.example.publictoilet_back.dto.ReviewResponseDto
import com.example.publictoilet_back.dto.ToiletLocationDto
import com.example.publictoilet_back.dto.ToiletInfoDto
import com.example.publictoilet_back.repository.ReviewRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class AppService(val reviewService: ReviewService, val toiletService: ToiletService) {

    fun findToiletById(id : Long) : ToiletLocationDto{
        return toiletService.findById(id)
    }

    fun findToiletInfo(id : Long) : ToiletInfoDto {
        return toiletService.findInfo(id)
    }

    fun findNearToilet(latitude : Double, longitude : Double, range : Int) : MutableList<ToiletLocationDto>{
        return toiletService.findNearToilet(latitude, longitude, range)
    }

    fun findReviewByToiletId(toilet_id : Long) : MutableList<ReviewResponseDto>{
        return reviewService.findByToiletId(toilet_id)
    }

    //TODO review 저장 -> score_avg 갱신
    @Transactional
    fun saveReview(reviewRequestDto : ReviewRequestDto) : Long? {
        return reviewService.save(reviewRequestDto)
    }
}