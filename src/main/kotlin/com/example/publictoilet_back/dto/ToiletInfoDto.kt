package com.example.publictoilet_back.dto

import com.example.publictoilet_back.entity.Statistics
import com.example.publictoilet_back.entity.Toilet
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import io.swagger.annotations.ApiParam
import java.util.*

@ApiModel(value = "ToiletInfoDto (화장실 조회 정보)", description = "화장실 조회할 때 사용하는 model")
class ToiletInfoDto(
        @ApiModelProperty(value = "평점 평균") val score_avg : Float?,
        @ApiModelProperty(value = "화장실 이름") var toiletName : String?,
        @ApiModelProperty(value = "전화번호") var tel : String?,
        @ApiModelProperty(value = "개방 시작 시간") var openTime : Date?,
        @ApiModelProperty(value = "개방 마감 시간") var closeTime : Date?,
        @ApiModelProperty(value = "남녀 공용 화장실 여부") var mw : Boolean?,
        @ApiModelProperty(value = "남성용 대변기 수", required = true) var m1 : Int?,
        @ApiModelProperty(value = "남성용 소변기 수", required = true) var m2 : Int?,
        @ApiModelProperty(value = "남성 장애인용 대변기 수", required = true) var m3 : Int?,
        @ApiModelProperty(value = "남성 장애인용 소변기 수", required = true) var m4 : Int?,
        @ApiModelProperty(value = "남성 어린이용 대변기 수", required = true) var m5 : Int?,
        @ApiModelProperty(value = "남성 어린이용 소변기 수", required = true) var m6 : Int?,
        @ApiModelProperty(value = "여성용 대변기 수", required = true) var w1 : Int?,
        @ApiModelProperty(value = "여성 장애인용 대변기 수", required = true) var w2 : Int?,
        @ApiModelProperty(value = "여성 어린이용 대변기 수", required = true) var w3 : Int?) {

    constructor(toilet: Toilet) : this(null, toilet.toiletName, toilet.tel, toilet.openTime, toilet.closeTime, toilet.mw, toilet.m1, toilet.m2, toilet.m3, toilet.m4, toilet.m5, toilet.m6, toilet.w1, toilet.w2, toilet.w3)
    constructor(statisticsDto: Statistics, toilet: Toilet) : this(statisticsDto.score_avg, toilet.toiletName, toilet.tel, toilet.openTime, toilet.closeTime, toilet.mw, toilet.m1, toilet.m2, toilet.m3, toilet.m4, toilet.m5, toilet.m6, toilet.w1, toilet.w2, toilet.w3)
}