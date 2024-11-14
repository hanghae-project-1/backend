package com.example.demo.domain.region.mapper;

import com.example.demo.domain.region.dto.request.RegionRequestDto;
import com.example.demo.domain.region.entity.Region;
import org.springframework.stereotype.Component;

@Component
public class RegionMapper {

    public Region toRegionEntity(RegionRequestDto requestDto) {
        return Region.builder()
                .district(requestDto.district())
                .build();
    }

}
