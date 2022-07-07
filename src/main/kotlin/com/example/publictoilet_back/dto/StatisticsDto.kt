package com.example.publictoilet_back.dto

import com.example.publictoilet_back.entity.Statistics
import springfox.documentation.annotations.ApiIgnore

@ApiIgnore
class StatisticsDto(val score_avg : Float?) {
    constructor(entity : Statistics) : this(entity.score_avg)
}