package com.example.publictoilet_back.dto

import com.example.publictoilet_back.entity.Statistics
import com.example.publictoilet_back.entity.Toilet
import java.util.*

class ToiletInfoDto(val score_avg : Float?, var toiletName : String?, var tel : String?, var openTime : Date?, var closeTime : Date?, var mw : Boolean?, var m1 : Int?, var m2 : Int?, var m3 : Int?, var m4 : Int?, var m5 : Int?, var m6 : Int?, var w1 : Int?, var w2 : Int?, var w3 : Int?) {
    constructor(statisticsDto: Statistics, toiletInfoDto: Toilet) : this(statisticsDto.score_avg, toiletInfoDto.toiletName, toiletInfoDto.tel, toiletInfoDto.openTime, toiletInfoDto.closeTime, toiletInfoDto.mw, toiletInfoDto.m1, toiletInfoDto.m2, toiletInfoDto.m3, toiletInfoDto.m4, toiletInfoDto.m5, toiletInfoDto.m6, toiletInfoDto.w1, toiletInfoDto.w2, toiletInfoDto.w3)
}