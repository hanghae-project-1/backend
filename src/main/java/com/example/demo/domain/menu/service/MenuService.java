package com.example.demo.domain.menu.service;

import com.example.demo.domain.menu.dto.request.MenuRequestDto;
import com.example.demo.domain.menu.dto.response.MenuResponseDto;
import com.example.demo.domain.menu.entity.Menu;
import com.example.demo.domain.menu.exception.DuplicateMenuException;
import com.example.demo.domain.menu.exception.NotFoundMenuAndStoreException;
import com.example.demo.domain.menu.exception.NotFoundMenuException;
import com.example.demo.domain.menu.mapper.MenuMapper;
import com.example.demo.domain.menu.repository.MenuRepository;
import com.example.demo.domain.store.entity.Store;
import com.example.demo.domain.store.exception.NotFoundStoreException;
import com.example.demo.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuMapper menuMapper;
    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public void createMenu(UUID storeId, MenuRequestDto requestDto) {

        Store store = getStore(storeId);
        checkDuplicateMenu(requestDto.name());
        Menu menu = menuMapper.toMenuEntity(requestDto, store);
        menuRepository.save(menu);

    }

    @Transactional(readOnly = true)
    public List<MenuResponseDto> getAllMenu(UUID storeId) {
        List<Menu> menuList = menuRepository.findByStoreId(storeId);

        return menuList.stream().map(menuMapper::toMenuResponseDto).toList();

    }

    @Transactional
    public void modifyMenu(UUID storeId, UUID menuId, MenuRequestDto requestDto) {

        Store store = getStore(storeId);
        Menu menu = menuRepository.findById(menuId).orElseThrow(NotFoundMenuException::new);
        checkDuplicateMenu(requestDto.name());
        menu.updateMenu(requestDto, store);
    }

    @Transactional
    public void deleteMenu(UUID storeId, UUID menuId) {

        Menu menu = menuRepository.findByIdAndStoreId(menuId, storeId).orElseThrow(NotFoundMenuAndStoreException::new);
        menu.markAsDelete();

    }

    private Store getStore(UUID storeId) {
        return storeRepository.findById(storeId).orElseThrow(NotFoundStoreException::new);
    }

    private void checkDuplicateMenu(String name) {
        boolean menuExists = menuRepository.existsByName(name);
        if(menuExists){
            throw new DuplicateMenuException();
        }
    }

}
