package com.example.publictoilet_back.service

import com.example.publictoilet_back.dto.ReviewRequestDto
import com.example.publictoilet_back.repository.ReviewRepository
import com.example.publictoilet_back.dto.ReviewResponseDto
import org.springframework.stereotype.Service

@Service
class ReviewService(val reviewRepository: ReviewRepository) {

    fun findByToiletId(toilet_id: Long) : MutableList<ReviewResponseDto>{
        val entities = reviewRepository.findByToiletId(toilet_id).orElseThrow {
            IllegalArgumentException("해당 화장실은 존재하지 않습니다. id=$toilet_id")
        }

        val result = mutableListOf<ReviewResponseDto>()

        for(entity in entities){
            result.add(ReviewResponseDto(entity))
        }

        return result
    }

    fun save(reviewRequestDto : ReviewRequestDto) : Long?{
        return reviewRepository.save(reviewRequestDto.toEntity()).id
    }
}