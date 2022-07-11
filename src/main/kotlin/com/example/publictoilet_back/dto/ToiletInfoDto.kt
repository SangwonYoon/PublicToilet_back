package com.example.publictoilet_back.dto

import com.example.publictoilet_back.entity.Statistics
import com.example.publictoilet_back.entity.Toilet
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import io.swagger.annotations.ApiParam
import java.time.LocalTime
import java.util.*

@ApiModel(value = "ToiletInfoDto (화장실 조회 정보)", description = "화장실 조회할 때 사용하는 model")
class ToiletInfoDto(
        @ApiModelProperty(value = "화장실 id") val id : Long? = null,
        @ApiModelProperty(value = "화장실 위도", required = true) val longitude : Double,
        @ApiModelProperty(value = "화장실 경도", required = true) val latitude : Double,
        @ApiModelProperty(value = "화장실 이름") val toiletName : String?,
        @ApiModelProperty(value = "전화번호") val tel : String?,
        @ApiModelProperty(value = "개방 시작 시간") val openTime : LocalTime?,
        @ApiModelProperty(value = "개방 마감 시간") val closeTime : LocalTime?,
        @ApiModelProperty(value = "남녀 공용 화장실 여부") val mw : Boolean?,
        @ApiModelProperty(value = "남성용 대변기 수", required = true) val m1 : Int,
        @ApiModelProperty(value = "남성용 소변기 수", required = true) val m2 : Int,
        @ApiModelProperty(value = "남성 장애인용 대변기 수", required = true) val m3 : Int,
        @ApiModelProperty(value = "남성 장애인용 소변기 수", required = true) val m4 : Int,
        @ApiModelProperty(value = "남성 어린이용 대변기 수", required = true) val m5 : Int,
        @ApiModelProperty(value = "남성 어린이용 소변기 수", required = true) val m6 : Int,
        @ApiModelProperty(value = "여성용 대변기 수", required = true) val w1 : Int,
        @ApiModelProperty(value = "여성 장애인용 대변기 수", required = true) val w2 : Int,
        @ApiModelProperty(value = "여성 어린이용 대변기 수", required = true) val w3 : Int,
        @ApiModelProperty(value = "평점 평균") val score_avg : Float? = null,
        @ApiModelProperty(value = "화장실과 사용자와의 거리") val distance : Double? = null) {

    constructor(toilet: Toilet) : this(toilet.id, toilet.longitude, toilet.latitude, toilet.toiletName, toilet.tel, toilet.openTime, toilet.closeTime, toilet.mw, toilet.m1, toilet.m2, toilet.m3, toilet.m4, toilet.m5, toilet.m6, toilet.w1, toilet.w2, toilet.w3, null, null)
    constructor(statisticsDto: Statistics, toilet: Toilet) : this(toilet.id, toilet.longitude, toilet.latitude, toilet.toiletName, toilet.tel, toilet.openTime, toilet.closeTime, toilet.mw, toilet.m1, toilet.m2, toilet.m3, toilet.m4, toilet.m5, toilet.m6, toilet.w1, toilet.w2, toilet.w3, statisticsDto.score_avg, null)

    constructor(toilet : Toilet, statisticsDto: Statistics, distance: Double) : this(toilet.id, toilet.longitude, toilet.latitude, toilet.toiletName, toilet.tel, toilet.openTime, toilet.closeTime, toilet.mw, toilet.m1, toilet.m2, toilet.m3, toilet.m4, toilet.m5, toilet.m6, toilet.w1, toilet.w2, toilet.w3, statisticsDto.score_avg, distance)

    fun toEntity() : Toilet {
        return Toilet(longitude = longitude, latitude = latitude, toiletName = toiletName, tel = tel, openTime = openTime, closeTime = closeTime, mw = mw, m1 = m1, m2 = m2, m3 = m3, m4 = m4, m5 = m5, m6 = m6, w1 = w1, w2 = w2, w3 = w3)
    }
}