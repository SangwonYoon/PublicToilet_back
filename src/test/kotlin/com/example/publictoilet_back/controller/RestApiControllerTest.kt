package com.example.publictoilet_back.controller

import com.example.publictoilet_back.dto.ToiletInfoDto
import com.example.publictoilet_back.entity.Statistics
import com.example.publictoilet_back.entity.Toilet
import com.example.publictoilet_back.repository.ReviewRepository
import com.example.publictoilet_back.repository.StatisticsRepository
import com.example.publictoilet_back.repository.ToiletRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RestApiControllerTest {

    @LocalServerPort
    val port : Int = 0

    @Autowired
    val restTemplate : TestRestTemplate? = null

    @Autowired
    val toiletRepository : ToiletRepository? = null

    @Autowired
    val reviewRepository : ReviewRepository? = null

    @Autowired
    val statisticsRepository : StatisticsRepository? = null

    @After
    @Throws(Exception::class)
    fun tearDown(){
        toiletRepository!!.deleteAll()
        reviewRepository!!.deleteAll()
        statisticsRepository!!.deleteAll()
    }

    @Test
    @Throws(Exception::class)
    fun findInfoTest(){
        //given
        val savedToilet = toiletRepository!!.save(Toilet(id = 1, longitude = 10.34, latitude = 30.54, toiletName = "솔샘 화장실", tel = "02-547-2323", openTime = null, closeTime = null, mw = false, m1 = 1, m2 = 2, m3 = 3, m4 = 4, m5 = 5, m6 = 6, w1 = 7, w2 = 8, w3 = 9))
        statisticsRepository!!.save(Statistics(toilet = savedToilet, score_avg = null))

        val url = "http://localhost:$port/toilets/${savedToilet.id}/info"

        val savedToiletInfoDto = ToiletInfoDto(savedToilet)

        //when
        val responseEntity : ResponseEntity<ToiletInfoDto> = restTemplate!!.getForEntity(url, ToiletInfoDto::class.java)

        //then
        assertThat(responseEntity.body).isEqualTo(savedToiletInfoDto)
        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
    }
}