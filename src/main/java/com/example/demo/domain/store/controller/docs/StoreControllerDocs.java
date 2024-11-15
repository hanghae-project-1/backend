package com.example.demo.domain.store.controller.docs;

import com.example.demo.common.model.response.Response;
import com.example.demo.domain.store.dto.request.StoreRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@Tag(name = "Store", description = "음식점 등록, 조회, 수정, 삭제 관련 사용자 API")
public interface StoreControllerDocs {

    @Operation(summary = "음식점 생성", description = "사용자의 ID 를 통해 음식점을 생성하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "음식점 생성 성공", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "400", description = "음식점 생성 실패.", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PostMapping("/api/v1/store/create/q")
    Response<Void> createStore(@Valid @RequestBody StoreRequestDto request);


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
