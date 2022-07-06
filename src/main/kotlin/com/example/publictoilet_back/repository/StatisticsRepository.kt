package com.example.publictoilet_back.repository

import com.example.publictoilet_back.entity.Review
import com.example.publictoilet_back.entity.Statistics
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface StatisticsRepository : JpaRepository<Statistics, Long> {

    fun findByToiletId(toilet_id: Long) : Optional<Statistics>
}