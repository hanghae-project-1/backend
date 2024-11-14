package com.example.demo.domain.region.controller.docs;

import com.example.demo.common.model.response.Response;
import com.example.demo.domain.region.dto.request.RegionRequestDto;
import com.example.demo.domain.region.dto.response.RegionResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Region", description = "지역 생성, 조회, 수정 등의 사용자 API")
public interface RegionControllerDocs {

    @Operation(summary = "지역 생성", description = "지역을 생성하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "지역 생성 성공", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "400", description = "지역 생성 실패", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PostMapping("/api/v1/region/create")
    Response<Void> createRegion(@Valid @RequestBody RegionRequestDto requestDto);

    @Operation(summary = "지역 전체 조회", description = "지역 전체를 조회하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "지역 전체 조회 성공", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping("/api/v1/region")
    Response<List<RegionResponseDto>> getAllRegion();

    @Operation(summary = "지역 수정", description = "지역을 수정하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "지역 수정 성공", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "400", description = "지역 수정 실패", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "지역을 찾을 수 없습니다.", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PatchMapping("/api/v1/region/{regionId}")
    Response<Void> modifyRegion(@PathVariable UUID regionId, @RequestBody RegionRequestDto requestDto);

    @Operation(summary = "지역 삭제", description = "지역을 삭제하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "지역 삭제 성공", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "400", description = "지역 삭제 실패", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "지역을 찾을 수 없습니다.", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @DeleteMapping("/api/v1/region/{regionId}")
    Response<Void> deleteRegion(@PathVariable UUID regionId);
}
