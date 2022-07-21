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

    private val tempToilet = Toilet(longitude = 10.34, latitude = 30.54, toiletName = "솔샘 화장실", tel = "02-547-2323", openTime = "24시간", mw = false, m1 = 1, m2 = 2, m3 = 3, m4 = 4, m5 = 5, m6 = 6, w1 = 7, w2 = 8, w3 = 9)

    @Test
    fun findFirst1ByLatitudeAndLongitudeTest(){
        //given
        toiletRepository!!.save(tempToilet)
        toiletRepository!!.save(Toilet(id = 3, longitude = 10.34, latitude = 30.54, toiletName = "솔샘 화장실", tel = "02-547-2323", openTime = "24시간", mw = false, m1 = 1, m2 = 2, m3 = 3, m4 = 4, m5 = 5, m6 = 6, w1 = 7, w2 = 8, w3 = 9))

        //when
        val toilet = toiletRepository!!.findFirst1ByLatitudeAndLongitude(longitude = 10.34, latitude = 30.54).get()

        //then
        assertThat(toilet.toiletName).isEqualTo("솔샘 화장실")
        assertThat(toilet.openTime).isEqualTo("24시간")
        assertThat(toilet.tel).isEqualTo("02-547-2323")
    }

    @Test
    fun beforeUpdateToiletDataTest(){
        //given
        val toilet1 = toiletRepository!!.save(Toilet(longitude = 10.34, latitude = 30.54, toiletName = "솔샘 화장실", tel = "02-547-2323", openTime = null, mw = false, m1 = 1, m2 = 2, m3 = 3, m4 = 4, m5 = 5, m6 = 6, w1 = 7, w2 = 8, w3 = 9))
        val toilet4 = toiletRepository!!.save(Toilet(longitude = 10.34, latitude = 75.54, toiletName = "솔샘 화장실", tel = "02-547-2323", openTime = null, mw = false, m1 = 1, m2 = 2, m3 = 3, m4 = 4, m5 = 5, m6 = 6, w1 = 7, w2 = 8, w3 = 9))
        val toilet2 = toiletRepository!!.save(Toilet(longitude = 10.34, latitude = 50.54, toiletName = "솔샘 화장실", tel = "02-547-2323", openTime = null, mw = false, m1 = 1, m2 = 2, m3 = 3, m4 = 4, m5 = 5, m6 = 6, w1 = 7, w2 = 8, w3 = 9))
        val toilet3 = toiletRepository!!.save(Toilet(longitude = 10.34, latitude = 70.54, toiletName = "솔샘 화장실", tel = "02-547-2323", openTime = null, mw = false, m1 = 1, m2 = 2, m3 = 3, m4 = 4, m5 = 5, m6 = 6, w1 = 7, w2 = 8, w3 = 9))
        val toilet5 = toiletRepository!!.save(Toilet(longitude = 10.34, latitude = 80.54, toiletName = "솔샘 화장실", tel = "02-547-2323", openTime = null, mw = false, m1 = 1, m2 = 2, m3 = 3, m4 = 4, m5 = 5, m6 = 6, w1 = 7, w2 = 8, w3 = 9))


        //when
        toiletRepository!!.beforeUpdateToiletData()

        //then
        val toilets = toiletRepository!!.findAll()
        assertThat(toilets[0].validate).isEqualTo(false)
        assertThat(toilets[1].validate).isEqualTo(false)
        assertThat(toilets[2].validate).isEqualTo(false)
        assertThat(toilets[3].validate).isEqualTo(false)
        assertThat(toilets[4].validate).isEqualTo(false)
    }

    @Test
    fun 화장실정보_저장_불러오기(){
        //given
        val latitude = 20.5
        val longitude = 30.5
        val toiletName = "솔샘 화장실"
        val tel = "02-547-2323"
        val openTime = "24시간"
        //val closeTime = null
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

        toiletRepository?.save(Toilet(latitude = latitude, longitude = longitude, toiletName = toiletName, tel = tel, openTime = openTime, mw = mw, m1 = m1, m2 = m2, m3 = m3, m4 = m4, m5 = m5, m6 = m6, w1 = w1, w2 = w2, w3 = w3))

        //when
        val toiletList = toiletRepository?.findAll()

        //then
        val toilet = toiletList!![0]
        assertThat(toilet.latitude).isEqualTo(latitude)
        assertThat(toilet.longitude).isEqualTo(longitude)
        assertThat(toilet.toiletName).isEqualTo(toiletName)
        assertThat(toilet.tel).isEqualTo(tel)
        assertThat(toilet.openTime).isEqualTo(openTime)
        //assertThat(toilet.closeTime).isNull()
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

