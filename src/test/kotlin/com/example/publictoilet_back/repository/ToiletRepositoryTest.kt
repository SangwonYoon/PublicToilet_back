package com.example.publictoilet_back.repository

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
        toiletRepository!!.deleteAll()
    }

    @Test
    fun 화장실정보_저장_불러오기(){
        //given
        val latitude = 20.5
        val longitude = 30.5
        val toiletName = "솔샘 화장실"
        val tel = "02-547-2323"
        val openTime = null
        val closeTime = null
        val mw = false
        val m1 = 1
        val m2 = 2
        val m3 = 3
        val m4 = 4
        val m5 = 5
        val m6 = 6
        val w1 = 7
        val w2 = 8
        val w3 = 9

        toiletRepository?.save(Toilet(latitude = latitude, longitude = longitude, toiletName = toiletName, tel = tel, openTime = openTime, closeTime = closeTime, mw = mw, m1 = m1, m2 = m2, m3 = m3, m4 = m4, m5 = m5, m6 = m6, w1 = w1, w2 = w2, w3 = w3))

        //when
        val toiletList = toiletRepository?.findAll()

        //then
        val toilet = toiletList!![0]
        assertThat(toilet.latitude).isEqualTo(latitude)
        assertThat(toilet.longitude).isEqualTo(longitude)
        assertThat(toilet.toiletName).isEqualTo(toiletName)
        assertThat(toilet.tel).isEqualTo(tel)
        assertThat(toilet.openTime).isNull()
        assertThat(toilet.closeTime).isNull()
        assertThat(toilet.mw).isEqualTo(mw)
        assertThat(toilet.m1).isEqualTo(m1)
        assertThat(toilet.m2).isEqualTo(m2)
        assertThat(toilet.m3).isEqualTo(m3)
        assertThat(toilet.m4).isEqualTo(m4)
        assertThat(toilet.m5).isEqualTo(m5)
        assertThat(toilet.m6).isEqualTo(m6)
        assertThat(toilet.w1).isEqualTo(w1)
        assertThat(toilet.w2).isEqualTo(w2)
        assertThat(toilet.w3).isEqualTo(w3)
    }
}

