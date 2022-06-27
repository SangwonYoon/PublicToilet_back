package com.example.publictoilet_back.web.dto

import com.example.publictoilet_back.domain.info.Info
import java.util.*

class InfoResponseDto(var toiletName : String?, var tel : String?, var openTime : Date?, var closeTime : Date?, var mw : Boolean?, var m1 : Int?, var m2 : Int?, var m3 : Int?, var m4 : Int?, var m5 : Int?, var m6 : Int?, var w1 : Int?, var w2 : Int?, var w3 : Int?) {
    constructor(entity : Info) : this(entity.toiletName, entity.tel, entity.openTime, entity.closeTime, entity.mw, entity.m1, entity.m2, entity.m3, entity.m4, entity.m5, entity.m6, entity.w1, entity.w2, entity.w3) {}
}