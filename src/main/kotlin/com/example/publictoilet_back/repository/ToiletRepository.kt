package com.example.publictoilet_back.repository

import com.example.publictoilet_back.dto.ToiletInfoDto
import com.example.publictoilet_back.dto.ToiletInfoProjection
import com.example.publictoilet_back.entity.Toilet
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ToiletRepository : JpaRepository<Toilet, Long> {

    @Query("SELECT id, longitude, latitude, toilet_name, tel, open_time, close_time, mw, m1, m2, m3, m4, m5, m6, w1, w2, w3, ST_Distance_Sphere(POINT(:longitude, :latitude), POINT(longitude, latitude)) AS distance From Toilet WHERE ST_Distance_Sphere(POINT(:longitude, :latitude), POINT(longitude, latitude)) <= :range ORDER BY distance", nativeQuery = true)
    fun findNearToilet(@Param("latitude") user_latitude : Double, @Param("longitude") user_longitude : Double, @Param("range") range : Int) : List<ToiletInfoProjection>?
}