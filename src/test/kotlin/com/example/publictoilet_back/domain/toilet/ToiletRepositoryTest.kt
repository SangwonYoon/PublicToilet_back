package com.example.publictoilet_back.domain.toilet

import com.example.publictoilet_back.entity.Toilet
import com.example.publictoilet_back.repository.ToiletRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class ToiletRepositoryTest {

    @Autowired
    val toiletRepository: ToiletRepository? = null

    @After
    fun cleanup(){
        toiletRepository?.deleteAll()
    }

    @Test
    fun 화장실위치_불러오기(){
        //given
        val latitude = 20.5
        val longitude = 30.5

        toiletRepository?.save(Toilet(latitude = latitude, longitude = longitude))

        //when
        val toiletList = toiletRepository?.findAll()

        //then
        val toilet = toiletList!![0]
        assertThat(toilet.latitude).isEqualTo(20.5)
        assertThat(toilet.longitude).isEqualTo(30.5)
    }
}