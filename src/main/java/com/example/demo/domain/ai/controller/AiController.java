package com.example.demo.domain.ai.controller;

import com.example.demo.common.model.response.Response;
import com.example.demo.domain.ai.dto.request.AiRequestDto;
import com.example.demo.domain.ai.service.AiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ai")
public class AiController {

    private final AiService aiService;

    @PostMapping("/create")
    public Response<String> createAi(@Valid @RequestBody AiRequestDto requestDto) throws JSONException {

        String responseText = aiService.createAi(requestDto);

        return Response.<String>builder()
                .data(responseText)
                .build();

    }


}
