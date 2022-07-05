package com.example.publictoilet_back.service

import com.example.publictoilet_back.dto.ReviewRequestDto
import com.example.publictoilet_back.repository.ReviewRepository
import com.example.publictoilet_back.dto.ReviewResponseDto
import com.example.publictoilet_back.entity.Statistics
import com.example.publictoilet_back.entity.Toilet
import com.example.publictoilet_back.repository.StatisticsRepository
import com.example.publictoilet_back.repository.ToiletRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class ReviewService(val reviewRepository: ReviewRepository, val toiletRepository: ToiletRepository, val statisticsRepository: StatisticsRepository) {

    fun findByToiletId(toilet_id: Long) : MutableList<ReviewResponseDto>{
        val entities = reviewRepository.findByToiletId(toilet_id).orElseThrow {
            IllegalArgumentException("해당 화장실은 존재하지 않습니다. id=$toilet_id")
        }

        val result = mutableListOf<ReviewResponseDto>()

        for(entity in entities){
            result.add(ReviewResponseDto(entity))
        }

        return result
    }

    @Transactional
    fun save(reviewRequestDto : ReviewRequestDto) : Long?{
        val cnt = reviewRepository.findByToiletId(reviewRequestDto.toilet_id).orElseThrow {
            IllegalArgumentException("해당 화장실이 존재하지 않습니다. id=${reviewRequestDto.toilet_id}")
        }.size // 해당 화장실의 리뷰 개수
        val statistics = findOrCreateStatistics(toiletRepository.findById(reviewRequestDto.toilet_id).get()) // 해당 화장실의 통계 객체
        if(statistics.score_avg == null) statistics.score_avg = reviewRequestDto.score
        else statistics.score_avg = (statistics.score_avg!! * cnt + reviewRequestDto.score) / (cnt+1) // 통계 객체의 평점 평균 재계산
        statisticsRepository.save(statistics)

        val toilet = toiletRepository.findById(reviewRequestDto.toilet_id).orElseThrow {
            IllegalArgumentException("해당 화장실이 존재하지 않습니다. id=${reviewRequestDto.toilet_id}")
        }
        return reviewRepository.save(reviewRequestDto.toEntity(toilet)).id
    }

    fun findOrCreateStatistics(toilet : Toilet) : Statistics {
        val findStatistics = statisticsRepository.findById(toilet.id)

        return if(findStatistics.isPresent){
            findStatistics.get()
        }else{
            statisticsRepository.save(Statistics(toilet = toilet))
        }
    }
}