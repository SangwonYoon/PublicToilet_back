package com.example.publictoilet_back.dto

import com.example.publictoilet_back.entity.Review
import com.example.publictoilet_back.entity.Toilet

class ReviewRequestDto(val toilet_id : Long, val comment : String, val score : Float) {

    fun toEntity(toilet : Toilet) : Review {
        return Review(toilet = toilet, comment = comment, score = score) //TODO
    }
}