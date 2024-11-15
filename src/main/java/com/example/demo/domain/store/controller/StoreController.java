package com.example.demo.domain.store.controller;

import com.example.demo.common.model.response.Response;
import com.example.demo.domain.store.controller.docs.StoreControllerDocs;
import com.example.demo.domain.store.dto.request.StoreRequestDto;
import com.example.demo.domain.store.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/store")
public class StoreController implements StoreControllerDocs{

    private final StoreService storeService;

    @PostMapping("/create")
    public Response<Void> createStore(@Valid @RequestBody StoreRequestDto requestDto){

        storeService.createStore(requestDto);

        return Response.<Void>builder()
                .code(HttpStatus.CREATED.value())
                .message(HttpStatus.CREATED.getReasonPhrase())
                .build();

    }

}
