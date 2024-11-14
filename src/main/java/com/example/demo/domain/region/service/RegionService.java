package com.example.demo.domain.region.service;

import com.example.demo.domain.region.dto.request.RegionRequestDto;
import com.example.demo.domain.region.dto.response.RegionResponseDto;
import com.example.demo.domain.region.entity.Region;
import com.example.demo.domain.region.exception.NotFoundRegionException;
import com.example.demo.domain.region.mapper.RegionMapper;
import com.example.demo.domain.region.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegionService {

    private final RegionMapper regionMapper;
    private final RegionRepository regionRepository;

    @Transactional
    public void createRegion(RegionRequestDto requestDto) {
        Region region = regionMapper.toRegionEntity(requestDto);

        regionRepository.save(region);

    }

    @Transactional(readOnly = true)
    public List<RegionResponseDto> getAllRegion() {
        List<Region> regionList = regionRepository.findAll();

        return regionList.stream().map(regionMapper::toRegionResponseDto).toList();
    }

    public void modifyRegion(UUID regionId, RegionRequestDto requestDto) {

        Region region = regionRepository.findById(regionId).orElseThrow(NotFoundRegionException::new);

        region.updateRegion(requestDto);
        regionRepository.save(region);
    }
}
