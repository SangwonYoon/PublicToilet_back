package com.example.publictoilet_back.controller

import com.example.publictoilet_back.dto.ReviewRequestDto
import com.example.publictoilet_back.dto.ReviewResponseDto
import com.example.publictoilet_back.dto.ToiletLocationDto
import com.example.publictoilet_back.dto.ToiletInfoDto
import com.example.publictoilet_back.service.AppService
import org.springframework.web.bind.annotation.*

@RestController
class RestApiController(val appService: AppService) {

    @GetMapping("/toilets/{id}")
    fun findById(@PathVariable id : Long) : ToiletLocationDto {
        return appService.findToiletById(id)
    }

    @GetMapping("/toilets/search")
    fun findNearToilet(@RequestParam latitude : Double, @RequestParam longitude : Double, @RequestParam range : Int) : MutableList<ToiletLocationDto>{
        return appService.findNearToilet(latitude, longitude, range)
    }

    @GetMapping("/toilets/{id}/info")
    fun findInfo(@PathVariable id : Long) : ToiletInfoDto {
        return appService.findToiletInfo(id)
    }

    @GetMapping("toilets/{toilet_id}/reviews")
    fun findByToiletId(@PathVariable toilet_id : Long) : MutableList<ReviewResponseDto>{
        return appService.findReviewByToiletId(toilet_id)
    }

    @PostMapping("/reviews")
    fun save(@RequestBody reviewRequestDto : ReviewRequestDto) : Long?{
        return appService.saveReview(reviewRequestDto)
    }

}