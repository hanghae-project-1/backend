package com.example.demo.domain.menu.repository;

import com.example.demo.domain.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MenuRepository extends JpaRepository<Menu, UUID> {
    Optional<Menu> findByIdAndStoreId(UUID menuId, UUID storeId);

    List<Menu> findByStoreId(UUID storeId);

    boolean existsByStoreIdAndName(UUID storeId, String name);

}
