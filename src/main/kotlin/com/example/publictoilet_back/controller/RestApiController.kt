package com.example.publictoilet_back.controller

import com.example.publictoilet_back.dto.*
import com.example.publictoilet_back.service.AppService
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore

@RestController
class RestApiController(val appService: AppService) {

    @ApiOperation(value = "화장실 데이터 생성", notes = "공공 API로부터 화장실 데이터를 받아와 데이터베이스에 저장한다.")
    @PostMapping("/toilets/create")
    fun createToiletData() : Boolean{
        return appService.createToiletData()
    }

    @ApiOperation(value = "화장실 정보 저장", notes = "화장실 상세 정보를 저장한다.")
    @PostMapping("/toilets")
    fun saveToilet(@RequestBody toiletSaveDto: ToiletSaveDto) : Long?{
        return appService.saveToilet(toiletSaveDto)
    }

    @ApiOperation(value = "사용자 주변 화장실 조회", notes = "사용자가 지정한 범위 내의 화장실 목록을 조회한다.")
    @ApiImplicitParams(
        ApiImplicitParam(name = "latitude", value = "사용자 위치의 위도", required = true),
        ApiImplicitParam(name = "longitude", value = "사용자 위치의 경도", required = true),
        ApiImplicitParam(name = "range", value = "탐색 범위", required = true)
    )
    @GetMapping("/toilets/search")
    fun findNearToilet(@RequestParam latitude : Double, @RequestParam longitude : Double, @RequestParam range : Int) : MutableList<ToiletInfoDto>{
        return appService.findNearToilet(latitude, longitude, range)
    }

    @ApiOperation(value = "화장실 정보 조회", notes = "화장실 상세 정보를 조회한다.\n validate = false인 화장실을 조회한 경우 null 반환")
    @ApiImplicitParam(name = "toilet_id", value = "화장실 id", required = true)
    @Cacheable(value = ["toilet"], key = "#toilet_id", cacheManager = "cacheManager")
    @GetMapping("/toilets/{toilet_id}/info")
    fun findInfo(@PathVariable toilet_id : Long) : ToiletInfoDto? {
        return appService.findToiletInfo(toilet_id)
    }

    @ApiOperation(value = "화장실 리뷰 조회", notes = "조회하고자 하는 화장실의 리뷰를 조회한다.")
    @ApiImplicitParam(name = "toilet_id", value = "화장실 id", required = true)
    @Cacheable(value = ["review"], key = "#toilet_id", cacheManager = "cacheManager")
    @GetMapping("toilets/{toilet_id}/reviews")
    fun findReviewsByToiletId(@PathVariable toilet_id : Long) : MutableList<ReviewResponseDto>{
        return appService.findReviewByToiletId(toilet_id)
    }
    @ApiOperation(value = "리뷰 저장", notes = "화장실 리뷰를 저장한다.")
    @ApiImplicitParam(name = "toilet_id", value = "화장실 id", required = true)
    @CacheEvict(value = ["review"], key = "#toilet_id", cacheManager = "cacheManager")
    @PostMapping("/reviews/{toilet_id}")
    fun saveReview(@PathVariable toilet_id : Long, @RequestBody reviewRequestDto : ReviewRequestDto) : Long?{
        return appService.saveReview(toilet_id, reviewRequestDto)
    }

    @ApiOperation(value = "화장실 정보 수정", notes = "화장실 상세 정보를 수정한다.")
    @ApiImplicitParam(name = "toilet_id", value = "화장실 id", required = true)
    @CacheEvict(value = ["toilet"], key = "#toilet_id", cacheManager = "cacheManager")
    @PutMapping("/toilets/{toilet_id}")
    fun updateToiletInfo(@PathVariable toilet_id : Long, @RequestBody toiletUpdateDto : ToiletUpdateDto) : Long?{
        return appService.updateToiletInfo(toilet_id, toiletUpdateDto)
    }

//    @ApiIgnore
//    @Cacheable(value = ["toilet"], key = "#id", cacheManager = "cacheManager")
//    @GetMapping("/toilets/{id}")
//    fun findById(@PathVariable id : Long) : ToiletInfoDto {
//        return appService.findToiletById(id)
//    }
}