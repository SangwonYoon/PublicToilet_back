package com.example.publictoilet_back.dto

import com.example.publictoilet_back.entity.Review

class ReviewRequestDto(val toilet_id : Long, val comment : String, val score : Float) {

    fun toEntity() : Review {
        return Review(toilet = toilet_id, comment = comment, score = score) ///TODO
    }
}