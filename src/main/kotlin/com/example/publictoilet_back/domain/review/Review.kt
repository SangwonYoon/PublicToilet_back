package com.example.publictoilet_back.domain.review

import com.example.publictoilet_back.domain.toilet.Toilet
import org.jetbrains.annotations.NotNull
import javax.persistence.*

@Entity
class Review(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id : Long = 0,
        @ManyToOne
        @JoinColumn(name = "toilet_id")
        val toilet : Toilet = Toilet(), // Toilet의 PK를 갖는 column
        @NotNull
        val score : Double = 5.0,
        val comment : String? = null
) {
}