package com.example.demo.domain.region.controller;

import com.example.demo.common.model.response.Response;
import com.example.demo.domain.region.dto.request.RegionRequestDto;
import com.example.demo.domain.region.dto.response.RegionResponseDto;
import com.example.demo.domain.region.service.RegionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @GetMapping
    public Response<List<RegionResponseDto>> getAllRegion(){
        return Response.<List<RegionResponseDto>>builder()
                .data(regionService.getAllRegion())
                .build();
    }

    @PatchMapping("/{regionId}")
    public Response<Void> modifyRegion(@PathVariable UUID regionId, @RequestBody RegionRequestDto requestDto){

        regionService.modifyRegion(regionId, requestDto);

        return Response.<Void>builder()
                .build();
    }
}
