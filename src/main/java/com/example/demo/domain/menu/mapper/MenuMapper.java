package com.example.demo.domain.menu.mapper;

import com.example.demo.domain.menu.dto.request.MenuRequestDto;
import com.example.demo.domain.menu.entity.Menu;
import com.example.demo.domain.store.entity.Store;
import org.springframework.stereotype.Component;

@Component
public class MenuMapper {

    public Menu toMenuEntity(MenuRequestDto requestDto, Store store) {

        return Menu.builder()
                .name(requestDto.name())
                .content(requestDto.content())
                .price(requestDto.price())
                .stockStatus(requestDto.stockStatus())
                .imageUrl(requestDto.imageUrl())
                .classification(requestDto.classification())
                .store(store)
                .build();

    }

}
