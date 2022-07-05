package com.example.publictoilet_back.dto

import com.example.publictoilet_back.entity.Statistics

class StatisticsDto(val score_avg : Float?) {
    constructor(entity : Statistics) : this(entity.score_avg)
}