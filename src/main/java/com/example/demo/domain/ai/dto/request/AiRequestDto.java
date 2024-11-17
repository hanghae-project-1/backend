package com.example.demo.domain.ai.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AiRequestDto(

        @NotBlank
        String ownerName,

        @NotBlank
        String requestText

) {
}
