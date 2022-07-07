package com.example.publictoilet_back.dto

import com.example.publictoilet_back.entity.Review
import com.example.publictoilet_back.entity.Toilet
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import io.swagger.annotations.ApiParam

@ApiModel(value = "ReviewRequestDto (리뷰 저장 정보)", description = "화장실 리뷰를 저장할 때 사용하는 model")
class ReviewRequestDto(@ApiModelProperty(value = "화장실 id", required = true) val toilet_id : Long, @ApiModelProperty(value = "리뷰 코멘트", required = true) val comment : String, @ApiModelProperty(value = "리뷰 평점", required = true) val score : Float) {

    fun toEntity(toilet : Toilet) : Review {
        return Review(toilet = toilet, comment = comment, score = score) //TODO
    }
}