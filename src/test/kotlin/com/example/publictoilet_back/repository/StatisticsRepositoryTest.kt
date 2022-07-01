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
class StatisticsRepositoryTest {

    @Autowired
    val statisticsRepository : StatisticsRepository? = null

    @Autowired
    val toiletRepository : ToiletRepository? = null

    @After
    fun clean_up(){
        toiletRepository!!.deleteAll()
        statisticsRepository!!.deleteAll()
    }

    @Test
    fun 통계_저장_불러오기(){
        //given
        val savedToilet = toiletRepository!!.save(Toilet(id = 1, latitude = 10.23, longitude = 20.234))
        statisticsRepository!!.save(Statistics(toilet = savedToilet, score_avg = null))

        //when
        val statisticsList = (statisticsRepository!!.findAll())
        val statistics = statisticsList[0]

        //then
        assertThat(statisticsList.size).isEqualTo(1)
        assertThat(statistics.id).isEqualTo(savedToilet.id)
        assertThat(statistics.score_avg).isNull()
    }
}