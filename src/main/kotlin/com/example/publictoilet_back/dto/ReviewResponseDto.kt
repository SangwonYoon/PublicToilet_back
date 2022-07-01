package com.example.publictoilet_back.dto

import com.example.publictoilet_back.entity.Review

class ReviewResponseDto(val score : Float, val comment : String?) {
    constructor(entity : Review) : this(entity.score, entity.comment)
}