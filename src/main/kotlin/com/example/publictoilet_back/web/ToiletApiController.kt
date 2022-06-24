package com.example.publictoilet_back.web

import com.example.publictoilet_back.domain.toilet.Toilet
import com.example.publictoilet_back.service.toilet.ToiletService
import com.example.publictoilet_back.web.dto.ToiletResponseDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ToiletApiController(val toiletService: ToiletService) {

    @GetMapping("/api/v1/toilet/{id}")
    fun findById(@PathVariable id : Long) : ToiletResponseDto{
        return toiletService.findById(id)
    }

    @GetMapping("api/v1/toilet/{temp_latitude}/{temp_longitude}/{range}")
    fun findNearToilet(@PathVariable(value = "temp_latitude") latitude : Double, @PathVariable(value = "temp_longitude") longitude : Double, @PathVariable(value = "range") range : Int) : MutableList<Toilet>{
        return toiletService.findNearToilet(latitude, longitude, range)
    }
}