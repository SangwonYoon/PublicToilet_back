package com.example.publictoilet_back.entity

import com.example.publictoilet_back.entity.Toilet
import org.jetbrains.annotations.NotNull
import javax.persistence.*

@Entity
class Review(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id : Long = 0,
        @ManyToOne
        @JoinColumn(name = "toilet_id")
        var toilet : Toilet? = null, // Toilet의 PK를 갖는 column
        @NotNull
        var score : Float = 5.0F,
        @NotNull
        val comment : String? = null
) : BaseTimeEntity() {
}