package com.example.publictoilet_back.entity

import org.jetbrains.annotations.NotNull
import java.util.*
import javax.persistence.*

@Entity
class Toilet(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id : Long = 0,
        @NotNull
        val longitude : Double = 0.0,
        @NotNull
        val latitude : Double = 0.0,
        val toiletName : String? = null,
        val tel : String? = null,
        val openTime : Date? = null,
        val closeTime : Date? = null,
        val mw : Boolean? = null, // 남녀 공용 화장실 여부
        val m1 : Int? = null, // 남성용 대변기 수
        val m2 : Int? = null, // 남성용 소변기 수
        val m3 : Int? = null, // 남성용 장애인용 대변기 수
        val m4 : Int? = null, // 남성용 장애인용 소변기 수
        val m5 : Int? = null, // 남성용 어린이용 대변기 수
        val m6 : Int? = null, // 남성용 어린이용 소변기 수
        val w1 : Int? = null, // 여성용 대변기 수
        val w2 : Int? = null, // 여성용 장애인용 대변기 수
        val w3 : Int? = null, // 여성용 어린이용 대변기 수
        @OneToMany(mappedBy = "toilet")
        val reviewList : MutableList<Review> = mutableListOf(),
        @OneToOne(mappedBy = "toilet")
        val statistics: Statistics? = null
) {

}