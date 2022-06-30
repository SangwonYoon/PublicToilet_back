package com.example.publictoilet_back.domain.toilet

import com.example.publictoilet_back.domain.info.Info
import com.example.publictoilet_back.domain.review.Review
import org.jetbrains.annotations.NotNull
import javax.persistence.*

@Entity
class Toilet(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id : Long? = null,
        @NotNull
        val longitude : Double = 0.0,
        @NotNull
        val latitude : Double = 0.0,
        @OneToOne(cascade = [CascadeType.ALL], mappedBy = "toilet")
        @PrimaryKeyJoinColumn
        val info : Info? = null,
        @OneToMany(cascade = [CascadeType.ALL], mappedBy = "toilet")
        @PrimaryKeyJoinColumn
        val review : MutableList<Review>? = null
) {

}