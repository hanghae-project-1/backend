package com.example.demo.domain.menu.repository;

import com.example.demo.domain.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MenuRepository extends JpaRepository<Menu, UUID> {
    boolean existsByName(String name);

    Optional<Menu> findByIdAndStoreId(UUID menuId, UUID storeId);
}
