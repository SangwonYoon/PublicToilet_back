package com.example.publictoilet_back.web.controller

import com.example.publictoilet_back.domain.toilet.Toilet
import com.example.publictoilet_back.service.toilet.ToiletService
import com.example.publictoilet_back.web.dto.ToiletResponseDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ToiletApiController(val toiletService: ToiletService) {

    @GetMapping("/toilets/{id}")
    fun findById(@PathVariable id : Long) : ToiletResponseDto{
        return toiletService.findById(id)
    }

    @GetMapping("/toilets/search")
    fun findNearToilet(@RequestParam latitude : Double, @RequestParam longitude : Double, @RequestParam range : Int) : MutableList<ToiletResponseDto>{
        return toiletService.findNearToilet(latitude, longitude, range)
    }
}