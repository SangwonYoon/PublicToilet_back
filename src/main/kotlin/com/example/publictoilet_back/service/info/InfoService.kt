package com.example.publictoilet_back.service.info

import com.example.publictoilet_back.domain.info.InfoRepository
import com.example.publictoilet_back.web.dto.InfoResponseDto
import org.springframework.stereotype.Service

@Service
class InfoService(val infoRepository: InfoRepository) {

    fun findById(id : Long) : InfoResponseDto{
        // TODO 정상적으로 toilet의 id로 info가 조회되는지 확인해야 함.
        val entity = infoRepository.findById(id).orElseThrow{
            IllegalArgumentException("해당 화장실은 존재하지 않습니다. id=$id")
        }

        return InfoResponseDto(entity)
    }
}