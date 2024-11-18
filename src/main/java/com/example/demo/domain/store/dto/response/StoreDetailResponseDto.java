package com.example.demo.domain.store.dto.response;

import com.example.demo.domain.menu.dto.response.MenuResponseDto;

import java.util.List;
import java.util.UUID;

public record StoreDetailResponseDto (

        UUID id,

        String name,

        String phone,

        String address,

        String categoryMenuName,

        String district,

        List<MenuResponseDto> menuList

){
}
