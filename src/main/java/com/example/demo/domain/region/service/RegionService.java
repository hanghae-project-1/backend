package com.example.demo.domain.region.service;

import com.example.demo.domain.region.dto.request.RegionRequestDto;
import com.example.demo.domain.region.entity.Region;
import com.example.demo.domain.region.mapper.RegionMapper;
import com.example.demo.domain.region.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegionService {

    private final RegionMapper regionMapper;
    private final RegionRepository regionRepository;

    @Transactional
    public void createRegion(RegionRequestDto requestDto) {
        Region region = regionMapper.toRegionEntity(requestDto);

        System.out.println("eeeeeeeeeeeeee");
        regionRepository.save(region);

    }
}
