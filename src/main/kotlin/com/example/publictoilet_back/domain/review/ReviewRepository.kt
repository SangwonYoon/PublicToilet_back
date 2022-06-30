package com.example.publictoilet_back.domain.review

import com.example.publictoilet_back.domain.toilet.Toilet
import org.springframework.data.jpa.repository.JpaRepository

interface ReviewRepository : JpaRepository<Review, Long> {
    fun findByToiletId(toilet_id: Long) : MutableList<Review>
}