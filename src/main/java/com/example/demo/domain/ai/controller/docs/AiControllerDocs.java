package com.example.demo.domain.ai.controller.docs;

import com.example.demo.common.model.response.Response;
import com.example.demo.domain.ai.dto.request.AiRequestDto;
import com.example.demo.domain.ai.dto.response.AiResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Tag(name = "Ai", description = "Ai 생성, 조회, 삭제 사용자 API")
public interface AiControllerDocs {

    @Operation(summary = "ai 생성", description = "ai를 생성하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "ai 생성 성공", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "400", description = "ai 생성 실패", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PostMapping("/api/v1/ai/create")
    Response<String> createAi(@Valid @RequestBody AiRequestDto requestDto);

    @Operation(summary = "ai 전체 조회", description = "ai 전체를 조회하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ai 전체 조회 성공", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping("/api/v1/ai")
    Response<List<AiResponseDto>> getAllAi();

}
