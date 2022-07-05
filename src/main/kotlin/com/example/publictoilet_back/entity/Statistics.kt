package com.example.publictoilet_back.entity

import javax.persistence.*

@Entity
class Statistics(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id : Long = 0,
        @OneToOne
        //@MapsId
        @JoinColumn(name = "toilet_id")
        val toilet : Toilet? = null,
        var score_avg : Float? = null
){
}