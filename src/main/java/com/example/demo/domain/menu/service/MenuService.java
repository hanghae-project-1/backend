package com.example.demo.domain.menu.service;

import com.example.demo.domain.menu.dto.request.MenuRequestDto;
import com.example.demo.domain.menu.entity.Menu;
import com.example.demo.domain.menu.exception.DuplicateMenuException;
import com.example.demo.domain.menu.exception.NotFoundMenuException;
import com.example.demo.domain.menu.mapper.MenuMapper;
import com.example.demo.domain.menu.repository.MenuRepository;
import com.example.demo.domain.store.entity.Store;
import com.example.demo.domain.store.exception.NotFoundStoreException;
import com.example.demo.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuMapper menuMapper;
    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public void createMenu(UUID storeId, MenuRequestDto requestDto) {

        Store store = storeRepository.findById(storeId).orElseThrow(NotFoundStoreException::new);
        checkDuplicateMenu(requestDto.name());
        Menu menu = menuMapper.toMenuEntity(requestDto, store);
        menuRepository.save(menu);

    }

    @Transactional
    public void modifyMenu(UUID storeId, UUID menuId, MenuRequestDto requestDto) {

        Store store = storeRepository.findById(storeId).orElseThrow(NotFoundStoreException::new);
        Menu menu = menuRepository.findById(menuId).orElseThrow(NotFoundMenuException::new);
        checkDuplicateMenu(requestDto.name());
        menu.updateMenu(requestDto, store);
    }

    private void checkDuplicateMenu(String name) {
        boolean menuExists = menuRepository.existsByName(name);
        if(menuExists){
            throw new DuplicateMenuException();
        }
    }
}
