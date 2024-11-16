package com.example.demo.domain.menu.controller.docs;

import com.example.demo.common.model.response.Response;
import com.example.demo.domain.menu.dto.request.MenuRequestDto;
import com.example.demo.domain.menu.dto.response.MenuResponseDto;
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

@Tag(name = "Menu", description = "메뉴 등록, 조회, 수정, 삭제 관련 사용자 API")
public interface MenuControllerDocs {

    @Operation(summary = "메뉴 생성", description = "음식점 ID 를 통해 메뉴를 생성하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "메뉴 생성 성공", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "400", description = "메뉴 생성 실패.", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PostMapping("/api/v1/store/{storeId}/menu")
    Response<Void> createMenu(@Valid @PathVariable UUID storeId, @RequestBody MenuRequestDto requestDto);

    @Operation(summary = "메뉴 전체 조회", description = "메뉴 전체를 조회하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "메뉴 전체 조회 성공", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping("/api/v1/store/{storeId}/menu")
    Response<List<MenuResponseDto>> getAllMenu(@PathVariable UUID storeId);

    @Operation(summary = "메뉴 수정", description = "메뉴 ID 를 통해 메뉴를 수정하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "메뉴 수정 성공", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "400", description = "메뉴를 취소할 수 없습니다.", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "메뉴를 찾을 수 없습니다.", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PatchMapping("/api/v1/store/{storeId}/menu/{menuId}")
    Response<Void> modifyMenu(@PathVariable UUID storeId, @PathVariable UUID menuId, @RequestBody MenuRequestDto requestDto);

    @Operation(summary = "메뉴 삭제", description = "메뉴를 삭제하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "메뉴 삭제 성공", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "400", description = "메뉴 삭제 실패", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "메뉴를 찾을 수 없습니다.", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @DeleteMapping("/api/v1/store/{storeId}/menu/{menuId}")
    Response<Void> deleteMenu(@PathVariable UUID storeId, @PathVariable UUID menuId);
}
