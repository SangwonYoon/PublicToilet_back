package com.example.publictoilet_back.domain.info

import com.example.publictoilet_back.domain.toilet.Toilet
import java.time.LocalDate.now
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.MapsId
import javax.persistence.OneToOne

@Entity
class Info (
        @Id
        val id : Long? = null,
        @OneToOne
        @MapsId // @Id와 매핑해준다.
        @JoinColumn(name = "toilet_id")
        val toilet : Toilet = Toilet(),
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
        val w3 : Int? = null // 여성용 어린이용 대변기 수
        ){
}