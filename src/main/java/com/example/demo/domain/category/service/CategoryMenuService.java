package com.example.demo.domain.category.service;

import com.example.demo.domain.category.dto.request.CategoryMenuRequestDto;
import com.example.demo.domain.category.dto.response.CategoryMenuResponseDto;
import com.example.demo.domain.category.entity.CategoryMenu;
import com.example.demo.domain.category.exception.DuplicateCategoryMenuException;
import com.example.demo.domain.category.exception.NotFoundCategoryMenuException;
import com.example.demo.domain.category.mapper.CategoryMenuMapper;
import com.example.demo.domain.category.repository.CategoryMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryMenuService {

    private final CategoryMenuMapper categoryMenuMapper;
    private final CategoryMenuRepository categoryMenuRepository;

    @Transactional
    public void createCategoryMenu(CategoryMenuRequestDto requestDto) {

        CategoryMenu categoryMenu = categoryMenuMapper.toCategoryMenuEntity(requestDto);

        checkForDuplicateName(requestDto);

        categoryMenuRepository.save(categoryMenu);

    }

    @Transactional(readOnly = true)
    public List<CategoryMenuResponseDto> getAllCategoryMenu() {

        List<CategoryMenu> categoryMenuList = categoryMenuRepository.findAll();

        return categoryMenuList.stream().map(categoryMenuMapper::toCategoryMenuResponseDto).toList();

    }

    @Transactional
    public void modifyCategoryMenu(UUID categoryMenuId, CategoryMenuRequestDto requestDto) {
        CategoryMenu categoryMenu = categoryMenuRepository.findById(categoryMenuId).orElseThrow(NotFoundCategoryMenuException::new);

        checkForDuplicateName(requestDto);

        categoryMenu.updateCategoryMenu(requestDto);
        categoryMenuRepository.save(categoryMenu);
    }

    private void checkForDuplicateName(CategoryMenuRequestDto requestDto) {
        boolean exists = categoryMenuRepository.existsByName(requestDto.name());
        if(exists){
            throw new DuplicateCategoryMenuException();
        }
    }
}
