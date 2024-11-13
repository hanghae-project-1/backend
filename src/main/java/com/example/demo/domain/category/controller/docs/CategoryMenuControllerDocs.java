package com.example.demo.domain.category.controller.docs;

import com.example.demo.common.model.response.Response;
import com.example.demo.domain.category.dto.request.CategoryMenuRequestDto;
import com.example.demo.domain.category.dto.response.CategoryMenuResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.UUID;

@Tag(name = "CategoryMenu", description = "카테고리 생성, 조회, 수정 등의 사용자 API")
public interface CategoryMenuControllerDocs {

    @Operation(summary = "카테고리 생성", description = "사용자의 ID 를 통해 카테고리를 생성하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "카테고리 생성 성공", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "400", description = "카테고리 생성 실패", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PostMapping("/api/v1/category-menu")
    Response<Void> createCategoryMenu(@Valid @RequestBody CategoryMenuRequestDto requestDto);

    @Operation(summary = "카테고리 전체 조회", description = "카테고리 전체를 조회하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "카테고리 전체 조회 성공", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping("/api/v1/category-menu")
    Response<List<CategoryMenuResponseDto>> getAllCategoryMenu();

    @Operation(summary = "카테고리 수정", description = "카테고리 ID 를 통해 카테고리를 수정하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "카테고리 수정 성공", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "400", description = "카테고리 수정 실패", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "카테고리를 찾을 수 없습니다.", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PatchMapping("/api/v1/category-menu/{categoryMenuId}")
    Response<Void> modifyCategoryMenu(@PathVariable UUID categoryMenuId, @RequestBody CategoryMenuRequestDto requestDto);
}
