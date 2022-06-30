package com.example.publictoilet_back.service.review

import com.example.publictoilet_back.domain.review.ReviewRepository
import com.example.publictoilet_back.domain.toilet.Toilet
import com.example.publictoilet_back.web.dto.ReviewResponseDto
import org.springframework.stereotype.Service

@Service
class ReviewService(val reviewRepository: ReviewRepository) {

    fun findByToiletId(toilet_id: Long) : MutableList<ReviewResponseDto>{
        val entities = reviewRepository.findByToiletId(toilet_id)
        // TODO orElseThrow() 사용법

        val result = mutableListOf<ReviewResponseDto>()

        for(entity in entities){
            result.add(ReviewResponseDto(entity))
        }

        return result
    }
}