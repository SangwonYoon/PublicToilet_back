package com.example.publictoilet_back.dto

import com.example.publictoilet_back.entity.Statistics
import com.example.publictoilet_back.entity.Toilet
import java.util.*

class ToiletInfoDto(val score_avg : Float?, var toiletName : String?, var tel : String?, var openTime : Date?, var closeTime : Date?, var mw : Boolean?, var m1 : Int?, var m2 : Int?, var m3 : Int?, var m4 : Int?, var m5 : Int?, var m6 : Int?, var w1 : Int?, var w2 : Int?, var w3 : Int?) {

    constructor(toilet: Toilet) : this(null, toilet.toiletName, toilet.tel, toilet.openTime, toilet.closeTime, toilet.mw, toilet.m1, toilet.m2, toilet.m3, toilet.m4, toilet.m5, toilet.m6, toilet.w1, toilet.w2, toilet.w3)
    constructor(statisticsDto: Statistics, toilet: Toilet) : this(statisticsDto.score_avg, toilet.toiletName, toilet.tel, toilet.openTime, toilet.closeTime, toilet.mw, toilet.m1, toilet.m2, toilet.m3, toilet.m4, toilet.m5, toilet.m6, toilet.w1, toilet.w2, toilet.w3)
}