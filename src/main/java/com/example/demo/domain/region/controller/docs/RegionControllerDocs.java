package com.example.demo.domain.region.controller.docs;

import com.example.demo.common.model.response.Response;
import com.example.demo.domain.region.dto.request.RegionRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Region", description = "지역 생성, 조회, 수정 등의 사용자 API")
public interface RegionControllerDocs {

    @Operation(summary = "지역 생성", description = "사용자의 ID 를 통해 지역을 생성하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "지역 생성 성공", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "400", description = "지역 생성 실패", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PostMapping("/api/v1/region/create")
    Response<Void> createRegion(@Valid @RequestBody RegionRequestDto requestDto);
}
