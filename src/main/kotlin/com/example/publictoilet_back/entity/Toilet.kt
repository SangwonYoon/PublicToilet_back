package com.example.publictoilet_back.entity

import com.example.publictoilet_back.dto.ToiletInfoDto
import com.example.publictoilet_back.dto.ToiletUpdateDto
import org.jetbrains.annotations.NotNull
import java.time.LocalTime
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
        var openTime : LocalTime? = null,
        var closeTime : LocalTime? = null,
        var mw : Boolean? = null, // 남녀 공용 화장실 여부
        var m1 : Int = 0, // 남성용 대변기 수
        var m2 : Int = 0, // 남성용 소변기 수
        var m3 : Int = 0, // 남성용 장애인용 대변기 수
        var m4 : Int = 0, // 남성용 장애인용 소변기 수
        var m5 : Int = 0, // 남성용 어린이용 대변기 수
        var m6 : Int = 0, // 남성용 어린이용 소변기 수
        var w1 : Int = 0, // 여성용 대변기 수
        var w2 : Int = 0, // 여성용 장애인용 대변기 수
        var w3 : Int = 0, // 여성용 어린이용 대변기 수
        @OneToMany(mappedBy = "toilet")
        val reviewList : MutableList<Review> = mutableListOf(),
        @OneToOne(mappedBy = "toilet")
        val statistics : Statistics? = null
) {
        fun update(toiletUpdateDto: ToiletUpdateDto){
                this.toiletName = toiletUpdateDto.toiletName
                this.tel = toiletUpdateDto.tel
                this.openTime = toiletUpdateDto.openTime
                this.closeTime = toiletUpdateDto.closeTime
                this.mw = toiletUpdateDto.mw
                this.m1 = toiletUpdateDto.m1
                this.m2 = toiletUpdateDto.m2
                this.m3 = toiletUpdateDto.m3
                this.m4 = toiletUpdateDto.m4
                this.m5 = toiletUpdateDto.m5
                this.m6 = toiletUpdateDto.m6
                this.w1 = toiletUpdateDto.w1
                this.w2 = toiletUpdateDto.w2
                this.w3 = toiletUpdateDto.w3
        }
}