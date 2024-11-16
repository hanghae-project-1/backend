package com.example.demo.domain.store.service;

import com.example.demo.domain.category.entity.CategoryMenu;
import com.example.demo.domain.category.exception.NotFoundCategoryMenuException;
import com.example.demo.domain.category.repository.CategoryMenuRepository;
import com.example.demo.domain.region.entity.Region;
import com.example.demo.domain.region.exception.NotFoundRegionException;
import com.example.demo.domain.region.repository.RegionRepository;
import com.example.demo.domain.store.dto.request.StoreRequestDto;
import com.example.demo.domain.store.dto.response.StoreResponseDto;
import com.example.demo.domain.store.entity.Store;
import com.example.demo.domain.store.exception.DuplicateStoreNameException;
import com.example.demo.domain.store.exception.NotFoundStoreException;
import com.example.demo.domain.store.mapper.StoreMapper;
import com.example.demo.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreMapper storeMapper;
    private final StoreRepository storeRepository;
    private final CategoryMenuRepository categoryMenuRepository;
    private final RegionRepository regionRepository;

    @Transactional
    public void createStore(StoreRequestDto requestDto) {

        checkDuplicateStoreName(requestDto.name(), requestDto.regionId());
        Store store = storeMapper.toStoreEntity(requestDto);
        storeRepository.save(store);


    }


    public List<StoreResponseDto> searchStores(UUID categoryId, UUID regionId) {

        List<Store> storeList = storeRepository.searchByFilters(categoryId, regionId);

        return storeList.stream().map(storeMapper::toStoreResponseDto).toList();

    }

    @Transactional
    public void modifyStore(UUID storeId, StoreRequestDto requestDto) {
        
        Store store = getStore(storeId);

        Region region = store.getRegion();
        if (!region.getId().equals(requestDto.regionId())) {
            region = regionRepository.findById(requestDto.regionId()).orElseThrow(NotFoundRegionException::new);
        }

        CategoryMenu categoryMenu = store.getCategoryMenu();
        if (!categoryMenu.getId().equals(requestDto.categoryMenuId())) {
            categoryMenu = categoryMenuRepository.findById(requestDto.categoryMenuId()).orElseThrow(NotFoundCategoryMenuException::new);
        }

        checkDuplicateStoreName(requestDto.name(), requestDto.regionId());
        store.updateStore(requestDto, region, categoryMenu);
        storeRepository.save(store);
    }

    @Transactional
    public void deleteStore(UUID storeId) {

        Store store = getStore(storeId);
        store.markAsDelete();

    }

    private Store getStore(UUID storeId) {
        return storeRepository.findById(storeId).orElseThrow(NotFoundStoreException::new);
    }

    private void checkDuplicateStoreName(String name, UUID regionId) {

        boolean storeExists = storeRepository.existsByNameAndRegion_Id(name, regionId);
        if (storeExists){
            throw new DuplicateStoreNameException();
        }
    }
}
