package com.example.demo.domain.store.service;

import com.example.demo.domain.store.dto.request.StoreRequestDto;
import com.example.demo.domain.store.entity.Store;
import com.example.demo.domain.store.exception.DuplicateStoreNameException;
import com.example.demo.domain.store.mapper.StoreMapper;
import com.example.demo.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreMapper storeMapper;
    private final StoreRepository storeRepository;

    @Transactional
    public void createStore(StoreRequestDto requestDto) {

        boolean storeExists = storeRepository.existsByNameAndRegion_Id(requestDto.name(), requestDto.regionId());
        if (storeExists){
            throw new DuplicateStoreNameException();
        }
        Store store = storeMapper.toStoreEntity(requestDto);
        storeRepository.save(store);


    }
}
