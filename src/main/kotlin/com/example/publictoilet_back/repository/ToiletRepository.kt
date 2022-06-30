package com.example.publictoilet_back.repository

import com.example.publictoilet_back.entity.Toilet
import org.springframework.data.jpa.repository.JpaRepository

interface ToiletRepository : JpaRepository<Toilet, Long> {
}