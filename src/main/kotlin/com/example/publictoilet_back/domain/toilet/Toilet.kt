package com.example.publictoilet_back.domain.toilet

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Toilet(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id : Long? = null,
        val longitude : Double? = null,
        val latitude : Double? = null
) {

}