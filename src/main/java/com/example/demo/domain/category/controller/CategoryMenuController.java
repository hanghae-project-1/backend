package com.example.demo.domain.category.controller;

import com.example.demo.common.model.response.Response;
import com.example.demo.domain.category.controller.docs.CategoryMenuControllerDocs;
import com.example.demo.domain.category.dto.request.CategoryMenuRequestDto;
import com.example.demo.domain.category.dto.response.CategoryMenuResponseDto;
import com.example.demo.domain.category.service.CategoryMenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category-menu")
public class CategoryMenuController implements CategoryMenuControllerDocs {

	private final CategoryMenuService categoryMenuService;

	@PostMapping
	@PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_MASTER')")
	public Response<Void> createCategoryMenu(@Valid @RequestBody CategoryMenuRequestDto requestDto) {

		categoryMenuService.createCategoryMenu(requestDto);

		return Response.<Void>builder()
				.code(HttpStatus.CREATED.value())
				.message(HttpStatus.CREATED.getReasonPhrase())
				.build();

	}

	@GetMapping
	@PreAuthorize("hasAnyRole('ROLE_OWNER', 'ROLE_MANAGER', 'ROLE_MASTER')")
	public Response<List<CategoryMenuResponseDto>> getAllCategoryMenu() {
		return Response.<List<CategoryMenuResponseDto>>builder()
				.data(categoryMenuService.getAllCategoryMenu())
				.build();
	}

	@PatchMapping("/{categoryMenuId}")
	@PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_MASTER')")
	public Response<Void> modifyCategoryMenu(@PathVariable UUID categoryMenuId, @RequestBody CategoryMenuRequestDto requestDto) {

		categoryMenuService.modifyCategoryMenu(categoryMenuId, requestDto);

		return Response.<Void>builder()
				.build();
	}

	@DeleteMapping("/{categoryMenuId}")
	@PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_MASTER')")
	public Response<Void> deleteCategoryMenu(@PathVariable UUID categoryMenuId) {

		categoryMenuService.deleteCategoryMenu(categoryMenuId);

		return Response.<Void>builder()
				.build();
	}

}
