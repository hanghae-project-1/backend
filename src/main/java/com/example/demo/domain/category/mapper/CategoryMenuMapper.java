package com.example.demo.domain.category.mapper;

import com.example.demo.domain.category.dto.request.CategoryMenuRequestDto;
import com.example.demo.domain.category.entity.CategoryMenu;
import org.springframework.stereotype.Component;

@Component
public class CategoryMenuMapper {

    public CategoryMenu toCategoryMenuEntity(CategoryMenuRequestDto requestDto) {
        return CategoryMenu.builder()
                .name(requestDto.name())
                .build();
    }

}
