package com.example.publictoilet_back.entity

import com.example.publictoilet_back.dto.ToiletInfoDto
import org.jetbrains.annotations.NotNull
import java.util.*
import javax.persistence.*

@Entity
class Toilet(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id : Long = 0,
        val longitude : Double = 0.0,
        val latitude : Double = 0.0,
        var toiletName : String? = null,
        var tel : String? = null,
        var openTime : Date? = null,
        var closeTime : Date? = null,
        var mw : Boolean? = null, // 남녀 공용 화장실 여부
        var m1 : Int? = null, // 남성용 대변기 수
        var m2 : Int? = null, // 남성용 소변기 수
        var m3 : Int? = null, // 남성용 장애인용 대변기 수
        var m4 : Int? = null, // 남성용 장애인용 소변기 수
        var m5 : Int? = null, // 남성용 어린이용 대변기 수
        var m6 : Int? = null, // 남성용 어린이용 소변기 수
        var w1 : Int? = null, // 여성용 대변기 수
        var w2 : Int? = null, // 여성용 장애인용 대변기 수
        var w3 : Int? = null, // 여성용 어린이용 대변기 수
        @OneToMany(mappedBy = "toilet")
        val reviewList : MutableList<Review> = mutableListOf(),
        @OneToOne(mappedBy = "toilet")
        val statistics : Statistics? = null
) {
        fun update(toiletInfoDto: ToiletInfoDto){
                this.toiletName = toiletInfoDto.toiletName
                this.tel = toiletInfoDto.tel
                this.openTime = toiletInfoDto.openTime
                this.closeTime = toiletInfoDto.closeTime
                this.mw = toiletInfoDto.mw
                this.m1 = toiletInfoDto.m1
                this.m2 = toiletInfoDto.m2
                this.m3 = toiletInfoDto.m3
                this.m4 = toiletInfoDto.m4
                this.m5 = toiletInfoDto.m5
                this.m6 = toiletInfoDto.m6
                this.w1 = toiletInfoDto.w1
                this.w2 = toiletInfoDto.w2
                this.w3 = toiletInfoDto.w3
        }
}