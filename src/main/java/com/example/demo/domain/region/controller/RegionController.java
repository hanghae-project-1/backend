package com.example.demo.domain.region.controller;

import com.example.demo.common.model.response.Response;
import com.example.demo.domain.region.dto.request.RegionRequestDto;
import com.example.demo.domain.region.service.RegionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/region")
public class RegionController {

    private final RegionService regionService;

    @PostMapping("/create")
    public Response<Void> createRegion(@Valid @RequestBody RegionRequestDto requestDto){

        regionService.createRegion(requestDto);

        return Response.<Void>builder()
                .code(HttpStatus.CREATED.value())
                .message(HttpStatus.CREATED.getReasonPhrase())
                .build();
    }

}
