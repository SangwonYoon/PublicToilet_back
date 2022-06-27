package com.example.publictoilet_back.domain.info

import org.springframework.data.jpa.repository.JpaRepository

interface InfoRepository : JpaRepository<Info, Long> {
}