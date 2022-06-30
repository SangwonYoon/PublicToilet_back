package com.example.publictoilet_back.repository

import com.example.publictoilet_back.entity.Review
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface ReviewRepository : JpaRepository<Review, Long> {
    fun findByToiletId(toilet_id: Long) : Optional<List<Review>>
}