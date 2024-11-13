package com.example.demo.domain.category.service;

import com.example.demo.domain.category.dto.request.CategoryMenuRequestDto;
import com.example.demo.domain.category.dto.response.CategoryMenuResponseDto;
import com.example.demo.domain.category.entity.CategoryMenu;
import com.example.demo.domain.category.exception.DuplicateCategoryMenuException;
import com.example.demo.domain.category.mapper.CategoryMenuMapper;
import com.example.demo.domain.category.repository.CategoryMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryMenuService {

    private final CategoryMenuMapper categoryMenuMapper;
    private final CategoryMenuRepository categoryMenuRepository;

    @Transactional
    public void createCategoryMenu(CategoryMenuRequestDto requestDto) {

        CategoryMenu categoryMenu = categoryMenuMapper.toCategoryMenuEntity(requestDto);

        boolean exists = categoryMenuRepository.existsByName(requestDto.name());
        if(exists){
            throw new DuplicateCategoryMenuException();
        }

        categoryMenuRepository.save(categoryMenu);

    }

    @Transactional(readOnly = true)
    public List<CategoryMenuResponseDto> getAllCategoryMenu() {

        List<CategoryMenu> categoryMenuList = categoryMenuRepository.findAll();

        return categoryMenuList.stream().map(categoryMenuMapper::toCategoryMenuResponseDto).toList();

    }
}
