package com.example.publictoilet_back.domain.toilet

import org.springframework.data.jpa.repository.JpaRepository

interface ToiletRepository : JpaRepository<Toilet, Long> {
}