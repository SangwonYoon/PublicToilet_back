package com.example.publictoilet_back.service

import com.example.publictoilet_back.dto.ToiletInfoDto
import com.example.publictoilet_back.dto.ToiletSaveDto
import com.example.publictoilet_back.dto.ToiletUpdateDto
import com.example.publictoilet_back.entity.Statistics
import com.example.publictoilet_back.entity.Toilet
import com.example.publictoilet_back.repository.StatisticsRepository
import com.example.publictoilet_back.repository.ToiletRepository
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.time.LocalTime
import javax.transaction.Transactional
import kotlin.math.*

@Service
class ToiletService(val toiletRepository: ToiletRepository, val statisticsRepository: StatisticsRepository) {
    /*
    @Scheduled(cron = "5/10 * * * * *")
    fun schedulerTest(){
        println("What time is it now? : ${LocalTime.now()}")
    }

     */

    @Transactional
    fun createToiletData() : Boolean{
        for(idx in 1..11){
            try{
                val url = URL("https://openapi.gg.go.kr/Publtolt?KEY=e097ad965a254d3ea7f47e9649f8a4c2&Type=json&psize=1000&pIndex=$idx")
                val urlConnection = url.openConnection() as HttpURLConnection
                urlConnection.requestMethod = "GET"
                urlConnection.setRequestProperty("Content-type", "application/json")

                val bf = BufferedReader(InputStreamReader(url.openStream(), "UTF-8"))
                val jsonString = bf.readLine()

                val jsonParser = JSONParser()
                val jsonWholeObject = jsonParser.parse(jsonString.toString()) as JSONObject
                val jsonData = jsonWholeObject["Publtolt"] as JSONArray

                val jsonHeader = jsonData[0] as JSONObject
                val head = jsonHeader["head"] as JSONArray
                val result = head[1] as JSONObject
                val resultCode = (result["RESULT"] as JSONObject)["CODE"].toString()
                if(resultCode != "INFO-000"){
                    continue
                }

                val jsonObject = jsonData[1] as JSONObject
                val jsonArray = jsonObject["row"] as JSONArray

                for(i in 0 until jsonArray.size){
                    val obj = jsonArray[i] as JSONObject
                    val latitude = obj["REFINE_WGS84_LAT"]?.toString()?.toDouble()
                    val longitude = obj["REFINE_WGS84_LOGT"]?.toString()?.toDouble()
                    val toiletName = obj["PBCTLT_PLC_NM"].toString()
                    val tel = obj["MANAGE_INST_TELNO"]?.toString()
                    val openTime = obj["OPEN_TM_INFO"].toString()
                    val mw = obj["MALE_FEMALE_TOILET_YN"].toString().toBoolean()
                    val m1 = obj["MALE_WTRCLS_CNT"].toString().toInt()
                    val m2 = obj["MALE_UIL_CNT"].toString().toInt()
                    val m3 = obj["MALE_DSPSN_WTRCLS_CNT"].toString().toInt()
                    val m4 = obj["MALE_DSPSN_UIL_CNT"].toString().toInt()
                    val m5 = obj["MALE_CHILDUSE_WTRCLS_CNT"].toString().toInt()
                    val m6 = obj["MALE_CHILDUSE_UIL_CNT"].toString().toInt()
                    val w1 = obj["FEMALE_WTRCLS_CNT"].toString().toInt()
                    val w2 = obj["FEMALE_DSPSN_WTRCLS_CNT"].toString().toInt()
                    val w3 = obj["FEMALE_CHILDUSE_WTRCLS_CNT"].toString().toInt()

                    if(latitude == null || longitude == null){
                        continue
                    }

                    val toiletSaveDto = ToiletSaveDto(latitude = latitude, longitude = longitude, toiletName = toiletName, tel = tel, openTime = openTime, mw = mw, m1 = m1, m2 = m2, m3 = m3, m4 = m4, m5 = m5, m6 = m6, w1 = w1, w2 = w2, w3 = w3)
                    saveToilet(toiletSaveDto)
                }
            } catch (e : Exception){
                println(e)
                return false
            }
        }
        return true
    }

    @Transactional
    @CacheEvict(value = ["toilet"], allEntries = true, cacheManager = "cacheManager") // 화장실 캐시 전부 삭제
    @Scheduled(cron = "* * * 15 * *") // 매달 15일에 동작
    fun updateToiletData(){
        toiletRepository.beforeUpdateToiletData() // 모든 화장실 데이터의 validate 값을 false로 update
        for(idx in 1..11){
            try{
                val url = URL("https://openapi.gg.go.kr/Publtolt?KEY=e097ad965a254d3ea7f47e9649f8a4c2&Type=json&psize=1000&pIndex=$idx")
                val urlConnection = url.openConnection() as HttpURLConnection
                urlConnection.requestMethod = "GET"
                urlConnection.setRequestProperty("Content-type", "application/json")

                val bf = BufferedReader(InputStreamReader(url.openStream(), "UTF-8"))
                val jsonString = bf.readLine()

                val jsonParser = JSONParser()
                val jsonWholeObject = jsonParser.parse(jsonString.toString()) as JSONObject
                val jsonData = jsonWholeObject["Publtolt"] as JSONArray

                val jsonHeader = jsonData[0] as JSONObject
                val head = jsonHeader["head"] as JSONArray
                val result = head[1] as JSONObject
                val resultCode = (result["RESULT"] as JSONObject)["CODE"].toString()
                if(resultCode != "INFO-000"){
                    continue
                }

                val jsonObject = jsonData[1] as JSONObject
                val jsonArray = jsonObject["row"] as JSONArray

                for(i in 0 until jsonArray.size){
                    val obj = jsonArray[i] as JSONObject
                    val latitude = obj["REFINE_WGS84_LAT"]?.toString()?.toDouble()
                    val longitude = obj["REFINE_WGS84_LOGT"]?.toString()?.toDouble()
                    val toiletName = obj["PBCTLT_PLC_NM"].toString()
                    val tel = obj["MANAGE_INST_TELNO"]?.toString()
                    val openTime = obj["OPEN_TM_INFO"].toString()
                    val mw = obj["MALE_FEMALE_TOILET_YN"].toString().toBoolean()
                    val m1 = obj["MALE_WTRCLS_CNT"].toString().toInt()
                    val m2 = obj["MALE_UIL_CNT"].toString().toInt()
                    val m3 = obj["MALE_DSPSN_WTRCLS_CNT"].toString().toInt()
                    val m4 = obj["MALE_DSPSN_UIL_CNT"].toString().toInt()
                    val m5 = obj["MALE_CHILDUSE_WTRCLS_CNT"].toString().toInt()
                    val m6 = obj["MALE_CHILDUSE_UIL_CNT"].toString().toInt()
                    val w1 = obj["FEMALE_WTRCLS_CNT"].toString().toInt()
                    val w2 = obj["FEMALE_DSPSN_WTRCLS_CNT"].toString().toInt()
                    val w3 = obj["FEMALE_CHILDUSE_WTRCLS_CNT"].toString().toInt()

                    if(latitude == null || longitude == null){
                        continue
                    }

                    val tempToilet  = toiletRepository.findFirst1ByLatitudeAndLongitude(latitude = latitude, longitude = longitude)
                    if(!tempToilet.isPresent){
                        val toiletSaveDto = ToiletSaveDto(latitude = latitude, longitude = longitude, toiletName = toiletName, tel = tel, openTime = openTime, mw = mw, m1 = m1, m2 = m2, m3 = m3, m4 = m4, m5 = m5, m6 = m6, w1 = w1, w2 = w2, w3 = w3)
                        saveToilet(toiletSaveDto)
                    }else{
                        tempToilet.get().update(ToiletUpdateDto(toiletName = toiletName, tel = tel, openTime = openTime, mw = mw, m1 = m1, m2 = m2, m3 = m3, m4 = m4, m5 = m5, m6 = m6, w1 = w1, w2 = w2, w3 = w3))
                    }
                }
            } catch (e : Exception){
                println(e)
            }
        }
    }


    @Transactional
    fun saveToilet(toiletSaveDto: ToiletSaveDto) : Long?{
        val savedToilet = toiletRepository.save(toiletSaveDto.toEntity())
        statisticsRepository.save(Statistics(toilet = savedToilet))
        return savedToilet.id
    }

    fun findById(id : Long) : ToiletInfoDto {
        val entity = toiletRepository.findById(id).orElseThrow{
            java.lang.IllegalArgumentException("해당 화장실은 존재하지 않습니다. id=$id")
        }

        return ToiletInfoDto(entity)
    }

    fun findInfo(id: Long) : ToiletInfoDto? {
        val entity = toiletRepository.findById(id).orElseThrow{
            IllegalArgumentException("해당 화장실은 존재하지 않습니다. id=$id")
        }

        if(!entity.validate){
            return null
        }

        val statistics = findOrCreateStatistics(entity)

        return ToiletInfoDto(statistics, entity)
    }

    fun findNearToilet(latitude : Double, longitude : Double, range : Int) : MutableList<ToiletInfoDto>{
        val result = mutableListOf<ToiletInfoDto>()
        val toiletInfoProjections = toiletRepository.findNearToilet(latitude, longitude, range) ?: return result
        for (toiletInfoProjection in toiletInfoProjections){
            val toiletInfoDto = ToiletInfoDto(toiletInfoProjection)
            result.add(toiletInfoDto)
        }

        return result
    }

    @Transactional
    fun updateToiletInfo(id : Long, toiletUpdateDto: ToiletUpdateDto) : Long?{
        val toilet = toiletRepository.findById(id).orElseThrow {
            IllegalArgumentException("해당 화장실은 존재하지 않습니다. id=$id")
        }
        toilet.update(toiletUpdateDto)
        return id
    }

    /*
    /**
     * 두 좌표의 거리를 계산한다.
     *
     * @param lat1 위도1
     * @param lon1 경도1
     * @param lat2 위도2
     * @param lon2 경도2
     * @return 두 좌표의 거리(m)
     */
    fun getDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Int {
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = sin(dLat / 2).pow(2.0) + sin(dLon / 2).pow(2.0) * cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2))
        val c = 2 * asin(sqrt(a))
        return (R * c).toInt()
    }
     */

    fun findOrCreateStatistics(toilet : Toilet) : Statistics{
        val findStatistics = statisticsRepository.findByToiletId(toilet.id)

        return if(findStatistics.isPresent){
            findStatistics.get()
        }else{
            statisticsRepository.save(Statistics(toilet = toilet))
        }
    }

    companion object{
        const val R =  6372.8 * 1000
    }
}