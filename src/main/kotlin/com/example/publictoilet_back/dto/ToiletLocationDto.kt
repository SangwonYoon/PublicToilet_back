package com.example.publictoilet_back.dto

import com.example.publictoilet_back.entity.Statistics
import com.example.publictoilet_back.entity.Toilet
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.util.DoubleSummaryStatistics

@ApiModel(value = "ToiletLocationDto (화장실 위치 정보)", description = "화장실 위치 정보와 사용자와의 거리 및 주요 정보를 담는 model")
class ToiletLocationDto(@ApiModelProperty(value = "화장실 id") val id : Long?,
                        @ApiModelProperty(value = "화장실 위치의 위도") val latitude : Double,
                        @ApiModelProperty(value = "화장실 위치의 경도") val longitude : Double,
                        @ApiModelProperty(value = "화장실 이름") val toiletName : String?,
                        @ApiModelProperty(value = "화장실과 사용자와의 거리") val distance : Double?,
                        @ApiModelProperty(value = "화장실 평점 평균") val score_avg : Float?) {
    constructor(entity : Toilet) : this(entity.id, entity.latitude, entity.longitude, entity.toiletName, null, null)

    constructor(entity : Toilet, distance : Double) : this(entity.id, entity.latitude, entity.longitude, entity.toiletName, distance, null)

    constructor(entity : Toilet, distance : Double, statistics: Statistics) : this(entity.id, entity.latitude, entity.longitude, entity.toiletName, distance, statistics.score_avg)
}