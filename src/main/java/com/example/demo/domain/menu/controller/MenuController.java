package com.example.demo.domain.menu.controller;

import com.example.demo.common.model.response.Response;
import com.example.demo.domain.menu.controller.docs.MenuControllerDocs;
import com.example.demo.domain.menu.dto.request.MenuRequestDto;
import com.example.demo.domain.menu.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MenuController implements MenuControllerDocs {

    private final MenuService menuService;

    @PostMapping("/store/{storeId}/menu")
    public Response<Void> createMenu(@Valid @PathVariable UUID storeId, @RequestBody MenuRequestDto requestDto) {

        menuService.createMenu(storeId, requestDto);

        return Response.<Void>builder()
                .code(HttpStatus.CREATED.value())
                .message(HttpStatus.CREATED.getReasonPhrase())
                .build();

    }
}
