package com.example.publictoilet_back.web.dto

import com.example.publictoilet_back.domain.review.Review

class ReviewResponseDto(val score : Double, val comment : String?) {
    constructor(entity : Review) : this(entity.score, entity.comment)
}