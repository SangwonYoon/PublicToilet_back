package com.example.publictoilet_back.controller

import com.example.publictoilet_back.dto.ReviewRequestDto
import com.example.publictoilet_back.dto.ReviewResponseDto
import com.example.publictoilet_back.dto.ToiletInfoDto
import com.example.publictoilet_back.dto.ToiletLocationDto
import com.example.publictoilet_back.entity.Review
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
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.util.LinkedMultiValueMap

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

    private val tempToilet = Toilet(longitude = 10.34, latitude = 30.54, toiletName = "솔샘 화장실", tel = "02-547-2323", openTime = null, closeTime = null, mw = false, m1 = 1, m2 = 2, m3 = 3, m4 = 4, m5 = 5, m6 = 6, w1 = 7, w2 = 8, w3 = 9)

    @After
    @Throws(Exception::class)
    fun tearDown(){
        reviewRepository!!.deleteAll()
        statisticsRepository!!.deleteAll()
        toiletRepository!!.deleteAll()
    }

    @Test
    @Throws(Exception::class)
    fun findInfoTest(){
        //given
        val savedToilet = toiletRepository!!.save(tempToilet)
        statisticsRepository!!.save(Statistics(toilet = savedToilet, score_avg = null))

        val url = "http://localhost:$port/toilets/${savedToilet.id}/info"

        val savedToiletInfoDto = ToiletInfoDto(savedToilet)

        //when
        val responseEntity : ResponseEntity<ToiletInfoDto> = restTemplate!!.getForEntity(url, ToiletInfoDto::class.java)

        //then
        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(responseEntity.body?.score_avg).isEqualTo(savedToiletInfoDto.score_avg)
        assertThat(responseEntity.body?.toiletName).isEqualTo(savedToiletInfoDto.toiletName)
        assertThat(responseEntity.body?.mw).isEqualTo(savedToiletInfoDto.mw)
        assertThat(responseEntity.body?.w1).isEqualTo(savedToiletInfoDto.w1)

        val statistics = statisticsRepository!!.findByToiletId(savedToilet.id).get()
        assertThat(statistics).isNotEqualTo(null)
        assertThat(statistics.score_avg).isEqualTo(null)
    }

    @Test
    fun findNearToiletTest(){
        //given
        val toilet1 = toiletRepository!!.save(Toilet(longitude = 10.34, latitude = 30.54, toiletName = "솔샘 화장실", tel = "02-547-2323", openTime = null, closeTime = null, mw = false, m1 = 1, m2 = 2, m3 = 3, m4 = 4, m5 = 5, m6 = 6, w1 = 7, w2 = 8, w3 = 9))
        val toilet4 = toiletRepository!!.save(Toilet(longitude = 10.34, latitude = 90.54, toiletName = "솔샘 화장실", tel = "02-547-2323", openTime = null, closeTime = null, mw = false, m1 = 1, m2 = 2, m3 = 3, m4 = 4, m5 = 5, m6 = 6, w1 = 7, w2 = 8, w3 = 9))
        val toilet2 = toiletRepository!!.save(Toilet(longitude = 10.34, latitude = 50.54, toiletName = "솔샘 화장실", tel = "02-547-2323", openTime = null, closeTime = null, mw = false, m1 = 1, m2 = 2, m3 = 3, m4 = 4, m5 = 5, m6 = 6, w1 = 7, w2 = 8, w3 = 9))
        val toilet3 = toiletRepository!!.save(Toilet(longitude = 10.34, latitude = 70.54, toiletName = "솔샘 화장실", tel = "02-547-2323", openTime = null, closeTime = null, mw = false, m1 = 1, m2 = 2, m3 = 3, m4 = 4, m5 = 5, m6 = 6, w1 = 7, w2 = 8, w3 = 9))
        val toilet5 = toiletRepository!!.save(Toilet(longitude = 10.34, latitude = 110.54, toiletName = "솔샘 화장실", tel = "02-547-2323", openTime = null, closeTime = null, mw = false, m1 = 1, m2 = 2, m3 = 3, m4 = 4, m5 = 5, m6 = 6, w1 = 7, w2 = 8, w3 = 9))

        val userLatitude = "30.54"
        val userLongitude = "10.34"
        val range = "10000"

        val url = "http://localhost:$port/toilets/search?latitude=$userLatitude&longitude=$userLongitude&range=$range"

        //when
        val params = LinkedMultiValueMap<String, String>()
        params.add("latitude", userLatitude)
        params.add("longitude", userLongitude)
        params.add("range", range)

        val responseEntity = restTemplate!!.getForEntity(url, Array<ToiletLocationDto>::class.java)

        //then
        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(responseEntity.body!![0].latitude).isEqualTo(toilet1.latitude)
        assertThat(responseEntity.body!![1].latitude).isEqualTo(toilet2.latitude)
        assertThat(responseEntity.body!![2].latitude).isEqualTo(toilet3.latitude)
        assertThat(responseEntity.body!![3].latitude).isEqualTo(toilet4.latitude)
        assertThat(responseEntity.body!![4].latitude).isEqualTo(toilet5.latitude)
        assertThat(responseEntity.body).hasSize(5)
    }

    @Test
    fun saveReviewTest(){
        //given
        val savedToilet = toiletRepository!!.save(tempToilet)
        statisticsRepository!!.save(Statistics(toilet = savedToilet))
        val request1 = ReviewRequestDto(toilet_id = savedToilet.id, comment = "hello", score = 5.0F)
        val request2 = ReviewRequestDto(toilet_id = savedToilet.id, comment = "hello", score = 4.0F)
        val request3 = ReviewRequestDto(toilet_id = savedToilet.id, comment = "hello", score = 3.0F)

        val url = "http://localhost:$port/reviews"

        //when
        val responseEntity = restTemplate!!.postForEntity(url, request1, Long::class.java)
        restTemplate!!.postForEntity(url, request2, Long::class.java)
        restTemplate!!.postForEntity(url, request3, Long::class.java)

        //then
        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(responseEntity.body!!).isGreaterThan(0L)

        val reviews = reviewRepository!!.findAll()
        assertThat(reviews[0].toilet!!.id).isEqualTo(savedToilet.id)
        assertThat(reviews[0].comment).isEqualTo("hello")
        assertThat(reviews[0].score).isEqualTo(5.0F)

        val statistics = statisticsRepository!!.findByToiletId(savedToilet.id).get()
        assertThat(statistics.score_avg).isEqualTo(4.0F)
    }

    @Test
    fun findReviewsByToiletIdTest(){
        //given
        val savedToilet = toiletRepository!!.save(tempToilet)
        reviewRepository!!.save(Review(toilet = savedToilet, comment = "test1", score = 1.0F))
        reviewRepository!!.save(Review(toilet = savedToilet, comment = "test2", score = 2.0F))
        reviewRepository!!.save(Review(toilet = savedToilet, comment = "test3", score = 3.0F))

        val url = "http://localhost:$port/toilets/${savedToilet.id}/reviews"

        //when
        val responseEntity = restTemplate!!.getForEntity(url, Array<ReviewResponseDto>::class.java)

        //then
        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(responseEntity.body!![0].comment).isEqualTo("test1")
        assertThat(responseEntity.body!![0].score).isEqualTo(1.0F)
        assertThat(responseEntity.body!![1].comment).isEqualTo("test2")
        assertThat(responseEntity.body!![1].score).isEqualTo(2.0F)
        assertThat(responseEntity.body!![2].comment).isEqualTo("test3")
        assertThat(responseEntity.body!![2].score).isEqualTo(3.0F)
    }
}