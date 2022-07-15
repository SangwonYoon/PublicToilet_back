package com.example.publictoilet_back.service

import com.example.publictoilet_back.dto.*
import com.example.publictoilet_back.repository.ReviewRepository
import org.apache.commons.logging.Log
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.springframework.cache.annotation.Cacheable
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import javax.transaction.Transactional

@Service
class AppService(val reviewService: ReviewService, val toiletService: ToiletService) {

    @Transactional
    fun createToiletData() : Boolean{
        for(idx in 1..11){
            //try{
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
                val head = jsonHeader.get("head") as JSONArray
                val result = head[1] as JSONObject
                val resultCode = (result.get("RESULT") as JSONObject).get("CODE").toString()
                if(resultCode != "INFO-000"){
                    continue
                }

                val jsonObject = jsonData[1] as JSONObject
                val jsonArray = jsonObject.get("row") as JSONArray

                for(i in 0..jsonArray.size-1){
                    val obj = jsonArray[i] as JSONObject
                    val latitude = obj.get("REFINE_WGS84_LAT")?.toString()?.toDouble()
                    val longitude = obj.get("REFINE_WGS84_LOGT")?.toString()?.toDouble()
                    val toiletName = obj.get("PBCTLT_PLC_NM").toString()
                    val tel = obj.get("MANAGE_INST_TELNO")?.toString()
                    val openTime = obj.get("OPEN_TM_INFO").toString()
                    val mw = obj.get("MALE_FEMALE_TOILET_YN").toString().toBoolean()
                    val m1 = obj.get("MALE_WTRCLS_CNT").toString().toInt()
                    val m2 = obj.get("MALE_UIL_CNT").toString().toInt()
                    val m3 = obj.get("MALE_DSPSN_WTRCLS_CNT").toString().toInt()
                    val m4 = obj.get("MALE_DSPSN_UIL_CNT").toString().toInt()
                    val m5 = obj.get("MALE_CHILDUSE_WTRCLS_CNT").toString().toInt()
                    val m6 = obj.get("MALE_CHILDUSE_UIL_CNT").toString().toInt()
                    val w1 = obj.get("FEMALE_WTRCLS_CNT").toString().toInt()
                    val w2 = obj.get("FEMALE_DSPSN_WTRCLS_CNT").toString().toInt()
                    val w3 = obj.get("FEMALE_CHILDUSE_WTRCLS_CNT").toString().toInt()

                    if(latitude == null || longitude == null){
                        continue
                    }

                    val toiletSaveDto = ToiletSaveDto(latitude = latitude, longitude = longitude, toiletName = toiletName, tel = tel, openTime = openTime, mw = mw, m1 = m1, m2 = m2, m3 = m3, m4 = m4, m5 = m5, m6 = m6, w1 = w1, w2 = w2, w3 = w3)
                    toiletService.saveToilet(toiletSaveDto)
                }
            //} catch (e : Exception){
            //    println(e)
            //    return false
            //}
        }
        return true
    }

    /*
    @Scheduled(cron = "* * * 15 * *") // 매월 15일에 동작
    fun updateDBData(){
        for(idx in 1..11){
            try{
                val url = URL("https://openapi.gg.go.kr/Publtolt?KEY=e097ad965a254d3ea7f47e9649f8a4c2&Type=json&psize=1000&pIndex=$idx")
                val urlConnection = url.openConnection() as HttpURLConnection
                urlConnection.requestMethod = "GET"
                urlConnection.setRequestProperty("Content-type", "application/json")

                val bf = BufferedReader(InputStreamReader(url.openStream(), "UTF-8"))
                val jsonString = bf.readLine()

                val jsonParser = JSONParser(jsonString)
                val jsonData = (jsonParser.parse() as JSONArray).array

                val jsonHeader = jsonData[0] as JSONObject
                val head = (jsonHeader.getFirst("head") as JSONArray).array
                val result = head[1] as JSONObject
                val resultCode = (result.getFirst("RESULT") as JSONObject).getFirst("CODE").toString()
                if(resultCode != "INFO-000"){
                    continue
                }
                if(idx == 1){

                }

                val jsonObject = jsonData[1] as JSONObject
                val jsonArray = (jsonObject.getFirst("row") as JSONArray).array

                for(i in 0..jsonArray.size){
                    val obj = jsonArray[i] as JSONObject
                    val latitude = obj.getFirst("REFINE_WGS84_LAT")?.toString()?.toDouble()
                    val longitude = obj.getFirst("REFINE_WGS84_LOGT")?.toString()?.toDouble()
                    val toiletName = obj.getFirst("PBCTLT_PLC_NM").toString()
                    val tel = obj.getFirst("MANAGE_INST_TELNO")?.toString()
                    val openTime = obj.getFirst("OPEN_TM_INFO").toString()
                    val mw = obj.getFirst("MALE_FEMALE_TOILET_YN").toString().toBoolean()
                    val m1 = obj.getFirst("MALE_WTRCLS_CNT").toString().toInt()
                    val m2 = obj.getFirst("MALE_UIL_CNT").toString().toInt()
                    val m3 = obj.getFirst("MALE_DSPSN_WTRCLS_CNT").toString().toInt()
                    val m4 = obj.getFirst("MALE_DSPSN_UIL_CNT").toString().toInt()
                    val m5 = obj.getFirst("MALE_CHILDUSE_WTRCLS_CNT").toString().toInt()
                    val m6 = obj.getFirst("MALE_CHILDUSE_UIL_CNT").toString().toInt()
                    val w1 = obj.getFirst("FEMALE_WTRCLS_CNT").toString().toInt()
                    val w2 = obj.getFirst("FEMALE_DSPSN_WTRCLS_CNT").toString().toInt()
                    val w3 = obj.getFirst("FEMALE_CHILDUSE_WTRCLS_CNT").toString().toInt()

                    if(latitude == null || longitude == null){
                        continue
                    }

                    val toiletSaveDto = ToiletSaveDto(latitude = latitude, longitude = longitude, toiletName = toiletName, tel = tel, openTime = openTime, mw = mw, m1 = m1, m2 = m2, m3 = m3, m4 = m4, m5 = m5, m6 = m6, w1 = w1, w2 = w2, w3 = w3)
                    toiletService.saveToilet(toiletSaveDto)
                }
            } catch (e : Exception){

            }
        }
    }

     */

    fun saveToilet(toiletSaveDto: ToiletSaveDto) : Long?{
        return toiletService.saveToilet(toiletSaveDto)
    }

    fun findToiletById(id : Long) : ToiletInfoDto{
        return toiletService.findById(id)
    }

    fun findToiletInfo(id : Long) : ToiletInfoDto {
        return toiletService.findInfo(id)
    }

    fun findNearToilet(latitude : Double, longitude : Double, range : Int) : MutableList<ToiletInfoDto>{
        return toiletService.findNearToilet(latitude, longitude, range)
    }

    fun findReviewByToiletId(toilet_id : Long) : MutableList<ReviewResponseDto>{
        return reviewService.findByToiletId(toilet_id)
    }

    @Transactional
    fun saveReview(toilet_id : Long, reviewRequestDto : ReviewRequestDto) : Long? {
        return reviewService.save(toilet_id, reviewRequestDto)
    }

    @Transactional
    fun updateToiletInfo(id : Long, toiletUpdateDto: ToiletUpdateDto) : Long?{
        return toiletService.updateToiletInfo(id, toiletUpdateDto)
    }
}