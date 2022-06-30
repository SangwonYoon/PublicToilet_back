package com.example.publictoilet_back.entity

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.MapsId
import javax.persistence.OneToOne

@Entity
class Statistics(
        @Id
        val id : Long? = 0,
        @OneToOne
        @MapsId
        @JoinColumn(name = "toilet_id")
        val toilet : Toilet = Toilet(),
        val score_avg : Float? = null
){
}