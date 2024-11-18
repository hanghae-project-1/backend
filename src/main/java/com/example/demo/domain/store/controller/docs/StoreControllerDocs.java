package com.example.demo.domain.store.controller.docs;

import com.example.demo.common.model.response.Response;
import com.example.demo.domain.store.dto.request.StoreRequestDto;
import com.example.demo.domain.store.dto.response.StoreDetailResponseDto;
import com.example.demo.domain.store.dto.response.StoreResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Store", description = "음식점 등록, 조회, 수정, 삭제 관련 사용자 API")
public interface StoreControllerDocs {

    @Operation(summary = "음식점 생성", description = "사용자의 ID 를 통해 음식점을 생성하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "음식점 생성 성공", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "400", description = "음식점 생성 실패.", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PostMapping("/api/v1/store/create")
    Response<Void> createStore(@Valid @RequestBody StoreRequestDto request);

    @Operation(summary = "Owner 별 음식점 조회 및 키워드 검색", description = "Owner 별 음식점을 조회하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "음식점 조회 성공", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "음식점 조회 실패.", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping
    Response<List<StoreResponseDto>> ownerStores(
            @RequestParam String ownerName,
            @RequestParam(required = false) String keyWord
    );

    @Operation(summary = "음식점 검색", description = "음식점을 검색하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "음식점 검색 성공", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "음식점 검색 실패.", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping("/search")
    Response<List<StoreResponseDto>> searchStores(
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) UUID regionId
    );

    @Operation(summary = "음식점 단건 조회", description = "음식점 ID를 통해 단건 조회 하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "음식점 조회 성공", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "음식점 조회 실패.", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "음식점을 찾을 수 없습니다.", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping("/{storeId}")
    Response<StoreDetailResponseDto> getStoreDetail(@PathVariable UUID storeId);

    @Operation(summary = "음식점 수정", description = "음식점 ID 를 통해 음식점을 수정하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "음식점 수정 성공", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "400", description = "음식점을 취소할 수 없습니다.", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "음식점을 찾을 수 없습니다.", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PatchMapping("/api/v1/store/{storeId}")
    Response<Void> modifyStore(@PathVariable UUID storeId, @RequestBody StoreRequestDto requestDto);

    @Operation(summary = "음식점 삭제", description = "음식점 ID 를 통해 음식점을 삭제하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "음식점 삭제 성공", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "400", description = "음식점 삭제 실패", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "음식점을 찾을 수 없습니다.", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @DeleteMapping("/api/v1/store/{storeId}")
    Response<Void> deleteStore(@PathVariable UUID storeId);
}
