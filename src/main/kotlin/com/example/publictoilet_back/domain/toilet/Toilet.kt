package com.example.publictoilet_back.domain.toilet

import com.example.publictoilet_back.domain.info.Info
import javax.persistence.*

@Entity
class Toilet(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id : Long? = null,
        val longitude : Double? = null,
        val latitude : Double? = null,
        @OneToOne(cascade = [CascadeType.ALL], mappedBy = "toilet")
        @PrimaryKeyJoinColumn
        val info : Info? = null
) {

}