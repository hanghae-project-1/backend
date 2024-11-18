package com.example.demo.domain.store.mapper;

import com.example.demo.domain.category.entity.CategoryMenu;
import com.example.demo.domain.menu.dto.response.MenuResponseDto;
import com.example.demo.domain.region.entity.Region;
import com.example.demo.domain.store.dto.request.StoreRequestDto;
import com.example.demo.domain.store.dto.response.StoreDetailResponseDto;
import com.example.demo.domain.store.dto.response.StoreResponseDto;
import com.example.demo.domain.store.entity.Store;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StoreMapper {

    public Store toStoreEntity(StoreRequestDto requestDto) {
        return Store.builder()
                .name(requestDto.name())
                .ownerName(requestDto.ownerName())
                .phone(requestDto.phone())
                .address(requestDto.address())
                .categoryMenu(CategoryMenu.builder().id(requestDto.categoryMenuId()).build())
                .region(Region.builder().id(requestDto.regionId()).build())
                .build();

    }

    public StoreResponseDto toStoreResponseDto(Store store){
        return new StoreResponseDto(
                store.getId(),
                store.getName(),
                store.getPhone(),
                store.getAddress(),
                store.getCategoryMenu().getName(),
                store.getRegion().getDistrict()
        );
    }

    public StoreDetailResponseDto toStoreDetailResponseDto(Store store, List<MenuResponseDto> menuList) {
        return new StoreDetailResponseDto(
                store.getId(),
                store.getName(),
                store.getPhone(),
                store.getAddress(),
                store.getCategoryMenu().getName(),
                store.getRegion().getDistrict(),
                menuList
        );

    }
}
