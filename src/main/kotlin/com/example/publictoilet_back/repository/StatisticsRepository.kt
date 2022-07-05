package com.example.publictoilet_back.repository

import com.example.publictoilet_back.entity.Statistics
import org.springframework.data.jpa.repository.JpaRepository

interface StatisticsRepository : JpaRepository<Statistics, Long> {
}