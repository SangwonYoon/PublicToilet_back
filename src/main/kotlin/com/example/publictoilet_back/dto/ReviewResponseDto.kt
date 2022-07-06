package com.example.publictoilet_back.dto

import com.example.publictoilet_back.entity.Review
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel(value = "ReviewResponseDto (리뷰 조회 정보)", description = "리뷰를 조회할 때 사용하는 model")
class ReviewResponseDto(@ApiModelProperty(value = "평점", required = true) val score : Float,
                        @ApiModelProperty(value = "리뷰 코멘트", required = true) val comment : String?) {
    constructor(entity : Review) : this(entity.score, entity.comment)
}