package com.example.demo.domain.menu.controller;

import com.example.demo.common.model.response.Response;
import com.example.demo.domain.menu.controller.docs.MenuControllerDocs;
import com.example.demo.domain.menu.dto.request.MenuRequestDto;
import com.example.demo.domain.menu.dto.response.MenuResponseDto;
import com.example.demo.domain.menu.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MenuController implements MenuControllerDocs {

	private final MenuService menuService;

	@PostMapping("/store/{storeId}/menu")
	@PreAuthorize("hasAnyRole('ROLE_OWNER', 'ROLE_MANAGER', 'ROLE_MASTER')")
	public Response<Void> createMenu(@Valid @PathVariable UUID storeId, @RequestBody MenuRequestDto requestDto) {

		menuService.createMenu(storeId, requestDto);

		return Response.<Void>builder()
				.code(HttpStatus.CREATED.value())
				.message(HttpStatus.CREATED.getReasonPhrase())
				.build();

	}

	@GetMapping("/store/{storeId}/menu")
	public Response<List<MenuResponseDto>> getAllMenu(@PathVariable UUID storeId) {
		return Response.<List<MenuResponseDto>>builder()
				.data(menuService.getAllMenu(storeId))
				.build();
	}


	@PatchMapping("/store/{storeId}/menu/{menuId}")
	@PreAuthorize("hasAnyRole('ROLE_OWNER', 'ROLE_MANAGER', 'ROLE_MASTER')")
	public Response<Void> modifyMenu(@PathVariable UUID storeId, @PathVariable UUID menuId, @RequestBody MenuRequestDto requestDto) {

		menuService.modifyMenu(storeId, menuId, requestDto);

		return Response.<Void>builder()
				.build();
	}

	@DeleteMapping("/store/{storeId}/menu/{menuId}")
	@PreAuthorize("hasAnyRole('ROLE_OWNER', 'ROLE_MANAGER', 'ROLE_MASTER')")
	public Response<Void> deleteMenu(@PathVariable UUID storeId, @PathVariable UUID menuId) {

		menuService.deleteMenu(storeId, menuId);

		return Response.<Void>builder()
				.build();
	}

}
