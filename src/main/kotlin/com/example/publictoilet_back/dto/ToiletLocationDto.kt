package com.example.publictoilet_back.dto

import com.example.publictoilet_back.entity.Statistics
import com.example.publictoilet_back.entity.Toilet
import java.util.DoubleSummaryStatistics

class ToiletLocationDto(val id : Long?, val latitude : Double?, val longitude : Double?, val distance : Double?, val score_avg : Float?) {
    constructor(entity : Toilet) : this(entity.id, entity.latitude, entity.longitude, null, null)

    constructor(entity : Toilet, distance : Double) : this(entity.id, entity.latitude, entity.longitude, distance, null)

    constructor(entity : Toilet, distance : Double, statistics: Statistics) : this(entity.id, entity.latitude, entity.longitude, distance, statistics.score_avg)
}