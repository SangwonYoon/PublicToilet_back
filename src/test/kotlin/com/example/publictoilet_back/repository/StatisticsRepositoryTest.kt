package com.example.publictoilet_back.repository

import com.example.publictoilet_back.entity.Statistics
import com.example.publictoilet_back.entity.Toilet
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class
StatisticsRepositoryTest {

    @Autowired
    val statisticsRepository : StatisticsRepository? = null

    @Autowired
    val toiletRepository : ToiletRepository? = null

    private val tempToilet = Toilet(latitude = 10.23, longitude = 20.234)

    @After
    fun clean_up(){
        statisticsRepository!!.deleteAll()
        toiletRepository!!.deleteAll()
    }

    @Test
    fun 통계_저장_불러오기(){
        //given
        val savedToilet = toiletRepository!!.save(tempToilet)
        statisticsRepository!!.save(Statistics(toilet = savedToilet))

        //when
        val statistics = (statisticsRepository!!.findByToiletId(savedToilet.id)).get()

        val toilet = toiletRepository!!.findAll()[0]

        //then
        assertThat(toilet.latitude).isEqualTo(10.23)
        assertThat(statistics.toilet!!.id).isEqualTo(savedToilet.id)
        assertThat(statistics.score_avg).isNull()
    }
}
