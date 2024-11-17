package com.example.demo.domain.ai.controller;

import com.example.demo.common.model.response.Response;
import com.example.demo.domain.ai.dto.request.AiRequestDto;
import com.example.demo.domain.ai.dto.response.AiResponseDto;
import com.example.demo.domain.ai.service.AiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public Response<List<AiResponseDto>> getAllAi(@RequestParam("ownerName") String ownerName) {
        return Response.<List<AiResponseDto>>builder()
                .data(aiService.getAllAi(ownerName))
                .build();
    }


}
