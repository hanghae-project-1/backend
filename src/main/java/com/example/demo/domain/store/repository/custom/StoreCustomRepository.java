package com.example.demo.domain.store.repository.custom;

import com.example.demo.domain.store.entity.Store;

import java.util.List;
import java.util.UUID;

public interface StoreCustomRepository {

    List<Store> searchByFilters(UUID categoryId, UUID regionId);
}
