package com.example.publictoilet_back.dto

import com.example.publictoilet_back.entity.Statistics
import com.example.publictoilet_back.entity.Toilet
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import io.swagger.annotations.ApiParam
import java.io.Serializable
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

@ApiModel(value = "ToiletInfoDto (화장실 조회 정보)", description = "화장실 조회할 때 사용하는 model")
class ToiletInfoDto(
        @ApiModelProperty(value = "화장실 id") val id : Long? = null,
        @ApiModelProperty(value = "화장실 위도", required = true) val longitude : Double,
        @ApiModelProperty(value = "화장실 경도", required = true) val latitude : Double,
        @ApiModelProperty(value = "화장실 이름") val toiletName : String?,
        @ApiModelProperty(value = "전화번호") val tel : String?,
        @ApiModelProperty(value = "개방 시간") val openTime : String?,
        //@ApiModelProperty(value = "개방 마감 시간") val closeTime : LocalTime?,
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
        @ApiModelProperty(value = "평점 평균") var score_avg : Float? = null,
        @ApiModelProperty(value = "화장실과 사용자와의 거리") val distance : Double? = null) : Serializable {

    constructor(toilet: Toilet) : this(toilet.id, toilet.longitude, toilet.latitude, toilet.toiletName, toilet.tel, toilet.openTime, toilet.mw, toilet.m1, toilet.m2, toilet.m3, toilet.m4, toilet.m5, toilet.m6, toilet.w1, toilet.w2, toilet.w3, null, null)
    constructor(statisticsDto: Statistics, toilet: Toilet) : this(toilet.id, toilet.longitude, toilet.latitude, toilet.toiletName, toilet.tel, toilet.openTime, toilet.mw, toilet.m1, toilet.m2, toilet.m3, toilet.m4, toilet.m5, toilet.m6, toilet.w1, toilet.w2, toilet.w3, statisticsDto.score_avg, null)
    constructor(toiletInfoProjection: ToiletInfoProjection) : this(toiletInfoProjection.id, toiletInfoProjection.longitude, toiletInfoProjection.latitude, toiletInfoProjection.toiletName, toiletInfoProjection.tel, toiletInfoProjection.openTime, toiletInfoProjection.mw, toiletInfoProjection.m1, toiletInfoProjection.m2, toiletInfoProjection.m3, toiletInfoProjection.m4, toiletInfoProjection.m5, toiletInfoProjection.m6, toiletInfoProjection.w1, toiletInfoProjection.w2, toiletInfoProjection.w3, toiletInfoProjection.score_avg, toiletInfoProjection.distance)
    constructor(toilet : Toilet, statisticsDto: Statistics, distance: Double) : this(toilet.id, toilet.longitude, toilet.latitude, toilet.toiletName, toilet.tel, toilet.openTime, toilet.mw, toilet.m1, toilet.m2, toilet.m3, toilet.m4, toilet.m5, toilet.m6, toilet.w1, toilet.w2, toilet.w3, statisticsDto.score_avg, distance)

}

interface ToiletInfoProjection {
    val id : Long
    val longitude : Double
    val latitude : Double
    val toiletName : String?
    val tel : String?
    val openTime : String?
    //val closeTime : LocalTime?
    val mw : Boolean?
    val m1 : Int
    val m2 : Int
    val m3 : Int
    val m4 : Int
    val m5 : Int
    val m6 : Int
    val w1 : Int
    val w2 : Int
    val w3 : Int
    val score_avg : Float?
    val distance : Double?
}