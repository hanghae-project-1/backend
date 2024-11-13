package com.example.demo.domain.category.repository;


import com.example.demo.domain.category.entity.CategoryMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryMenuRepository extends JpaRepository<CategoryMenu, UUID> {
    boolean existsByName(String name);
}
