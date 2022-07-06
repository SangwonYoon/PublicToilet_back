package com.example.publictoilet_back.dto

import com.example.publictoilet_back.entity.Toilet
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import io.swagger.annotations.ApiParam
import java.time.LocalTime
import java.util.*

@ApiModel(value = "ToiletUpdateDto (화장실 업데이트 정보)", description = "화장실 정보를 업데이트할 때 사용하는 model")
class ToiletUpdateDto (
        @ApiModelProperty(value = "화장실 이름", required = true) val toiletName : String,
        @ApiModelProperty(value = "화장실 전화번호") val tel : String?,
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
        @ApiModelProperty(value = "여성 어린이용 대변기 수", required = true) val w3 : Int) {

        //constructor(toilet: Toilet) : this(toilet.toiletName, toilet.tel, toilet.openTime, toilet.closeTime, toilet.mw, toilet.m1, toilet.m2, toilet.m3, toilet.m4, toilet.m5, toilet.m6, toilet.w1, toilet.w2, toilet.w3)
}