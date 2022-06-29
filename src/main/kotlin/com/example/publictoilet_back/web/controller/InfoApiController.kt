package com.example.publictoilet_back.web.controller

import com.example.publictoilet_back.service.info.InfoService
import com.example.publictoilet_back.web.dto.InfoResponseDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class InfoApiController(val infoService: InfoService) {

    @GetMapping("Info/{id}")
    fun findById(@PathVariable id : Long) : InfoResponseDto{
        return infoService.findById(id)
    }
}