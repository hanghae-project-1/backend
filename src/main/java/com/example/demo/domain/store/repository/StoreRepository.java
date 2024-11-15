package com.example.demo.domain.store.repository;

import com.example.demo.domain.store.entity.Store;
import com.example.demo.domain.store.repository.custom.StoreCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StoreRepository extends JpaRepository<Store, UUID>, StoreCustomRepository {
    boolean existsByNameAndRegion_Id(String name, UUID regionId);
}
