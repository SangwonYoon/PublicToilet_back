package com.example.publictoilet_back.web.dto

import com.example.publictoilet_back.domain.toilet.Toilet

class ToiletResponseDto(var id : Long?, var latitude : Double?, var longitude : Double?) {
    constructor(entity : Toilet) : this(entity.id, entity.latitude, entity.longitude) {
    }
}