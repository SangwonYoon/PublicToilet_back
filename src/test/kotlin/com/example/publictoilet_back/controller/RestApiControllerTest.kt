package com.example.publictoilet_back.controller

import com.example.publictoilet_back.dto.*
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
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.util.LinkedMultiValueMap
import java.time.LocalTime

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

    private val tempToilet = Toilet(longitude = 10.34, latitude = 30.54, toiletName = "솔샘 화장실", tel = "02-547-2323", openTime = LocalTime.of(7, 30, 0), closeTime = LocalTime.of(22,40,0), mw = false, m1 = 1, m2 = 2, m3 = 3, m4 = 4, m5 = 5, m6 = 6, w1 = 7, w2 = 8, w3 = 9)

    @After
    @Throws(Exception::class)
    fun tearDown(){
        reviewRepository!!.deleteAll()
        statisticsRepository!!.deleteAll()
        toiletRepository!!.deleteAll()
    }

    @Test
    fun saveToiletTest(){
        //given
        val request = ToiletSaveDto(longitude = 10.34, latitude = 40.54, toiletName = "정릉 화장실", tel = "02-547-2323", openTime = "05:30:00", closeTime = "23:00:00", mw = false, m1 = 1, m2 = 2, m3 = 3, m4 = 4, m5 = 5, m6 = 6, w1 = 7, w2 = 8, w3 = 9)

        val url = "http://localhost:$port/toilets"

        //when
        val responseEntity = restTemplate!!.postForEntity(url, request, Long::class.java)

        //then
        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(responseEntity.body).isGreaterThan(0L)

        val savedToilet = toiletRepository!!.findById(responseEntity.body!!).get()
        assertThat(savedToilet.latitude).isEqualTo(40.54)
        assertThat(savedToilet.toiletName).isEqualTo("정릉 화장실")
        assertThat(savedToilet.openTime).isEqualTo(LocalTime.of(5, 30, 0))

        val statistics = statisticsRepository!!.findByToiletId(responseEntity.body!!).get()
        assertThat(statistics).isNotNull
        assertThat(statistics.score_avg).isNull()
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
        val toilet4 = toiletRepository!!.save(Toilet(longitude = 10.34, latitude = 75.54, toiletName = "솔샘 화장실", tel = "02-547-2323", openTime = null, closeTime = null, mw = false, m1 = 1, m2 = 2, m3 = 3, m4 = 4, m5 = 5, m6 = 6, w1 = 7, w2 = 8, w3 = 9))
        val toilet2 = toiletRepository!!.save(Toilet(longitude = 10.34, latitude = 50.54, toiletName = "솔샘 화장실", tel = "02-547-2323", openTime = null, closeTime = null, mw = false, m1 = 1, m2 = 2, m3 = 3, m4 = 4, m5 = 5, m6 = 6, w1 = 7, w2 = 8, w3 = 9))
        val toilet3 = toiletRepository!!.save(Toilet(longitude = 10.34, latitude = 70.54, toiletName = "솔샘 화장실", tel = "02-547-2323", openTime = null, closeTime = null, mw = false, m1 = 1, m2 = 2, m3 = 3, m4 = 4, m5 = 5, m6 = 6, w1 = 7, w2 = 8, w3 = 9))
        val toilet5 = toiletRepository!!.save(Toilet(longitude = 10.34, latitude = 80.54, toiletName = "솔샘 화장실", tel = "02-547-2323", openTime = null, closeTime = null, mw = false, m1 = 1, m2 = 2, m3 = 3, m4 = 4, m5 = 5, m6 = 6, w1 = 7, w2 = 8, w3 = 9))

        val userLatitude = "30.54"
        val userLongitude = "10.34"
        val range = "10000000"

        val url = "http://localhost:$port/toilets/search?latitude=$userLatitude&longitude=$userLongitude&range=$range"

        //when
        val params = LinkedMultiValueMap<String, String>()
        params.add("latitude", userLatitude)
        params.add("longitude", userLongitude)
        params.add("range", range)

        val responseEntity = restTemplate!!.getForEntity(url, Array<ToiletInfoDto>::class.java)

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
        val request1 = ReviewRequestDto(comment = "hello", score = 5.0F)
        val request2 = ReviewRequestDto(comment = "hello", score = 4.0F)
        val request3 = ReviewRequestDto(comment = "hello", score = 3.0F)

        val url = "http://localhost:$port/reviews/${savedToilet.id}"

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

    @Test
    fun updateToiletInfoTest(){
        //given
        val savedToilet = toiletRepository!!.save(tempToilet)
        val request = ToiletUpdateDto(toiletName = "길음 화장실", tel = "02-266-8323", openTime = LocalTime.of(6,0,0), closeTime = LocalTime.of(22,10,0), mw = true, m1 = 10, m2 = 9, m3 = 8, m4 = 7, m5 = 6, m6 = 5, w1 = 4, w2 = 3, w3 = 2)

        val url = "http://localhost:$port/toilets/${savedToilet.id}"

        //when
        restTemplate!!.put(url, request)

        //then

        val changedToilet = toiletRepository!!.findById(savedToilet.id).get()
        assertThat(changedToilet.toiletName).isEqualTo("길음 화장실")
        assertThat(changedToilet.tel).isEqualTo("02-266-8323")
        assertThat(changedToilet.openTime).isEqualTo(LocalTime.of(6,0,0))
        assertThat(changedToilet.closeTime).isEqualTo(LocalTime.of(22,10,0))
        assertThat(changedToilet.mw).isEqualTo(true)
        assertThat(changedToilet.m1).isEqualTo(10)
        assertThat(changedToilet.m2).isEqualTo(9)
        assertThat(changedToilet.m3).isEqualTo(8)
        assertThat(changedToilet.m4).isEqualTo(7)
        assertThat(changedToilet.m5).isEqualTo(6)
        assertThat(changedToilet.m6).isEqualTo(5)
        assertThat(changedToilet.w1).isEqualTo(4)
        assertThat(changedToilet.w2).isEqualTo(3)
        assertThat(changedToilet.w3).isEqualTo(2)
    }
}