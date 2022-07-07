package com.example.publictoilet_back.repository

import com.example.publictoilet_back.entity.Toilet
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ToiletRepository : JpaRepository<Toilet, Long> {

//    @Query("SELECT ID, TOILETNAME, LATITUDE, LONGITUDE FROM (SELECT ID, TOILETNAME, LATITUDE, LONGITUDE, (6372800 * 2 * t.LATITUDE) AS DISTANCE FROM TOILET t) X WHERE DISTANCE <= :range", nativeQuery = true)
//    fun findNearToilet(user_latitude : Double, user_longitude : Double, range : Int) : Toilet
}