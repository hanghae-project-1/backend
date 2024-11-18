package com.example.demo.domain.ai.dto.response;

import jakarta.validation.constraints.NotBlank;

public record AiResponseDto(

        @NotBlank
        String requestText,

        @NotBlank
        String responseText

) {
}
