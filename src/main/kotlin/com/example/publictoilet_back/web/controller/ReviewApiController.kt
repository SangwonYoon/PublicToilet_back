package com.example.publictoilet_back.web.controller

import com.example.publictoilet_back.service.review.ReviewService
import com.example.publictoilet_back.web.dto.ReviewResponseDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ReviewApiController(val reviewService : ReviewService) {

    @GetMapping("/reviews/{id}")
    fun findByToiletId(id : Long) : MutableList<ReviewResponseDto>{
        return reviewService.findByToiletId(id)
    }
}