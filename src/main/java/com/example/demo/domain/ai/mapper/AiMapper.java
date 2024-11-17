package com.example.demo.domain.ai.mapper;

import com.example.demo.domain.ai.dto.request.AiRequestDto;
import com.example.demo.domain.ai.dto.response.AiResponseDto;
import com.example.demo.domain.ai.entity.Ai;
import org.springframework.stereotype.Component;

@Component
public class AiMapper {

    public Ai toAiEntity(AiRequestDto requestDto, String responseText) {

        return Ai.builder()
                .ownerName(requestDto.ownerName())
                .requestText(requestDto.requestText())
                .responseText(responseText)
                .build();
    }


}
