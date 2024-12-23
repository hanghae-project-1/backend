package com.example.demo.domain.category.entity;

import com.example.demo.common.entity.BaseEntity;
import com.example.demo.domain.category.dto.request.CategoryMenuRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "p_category")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryMenu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @NotNull
    @Column(unique = true)
    String name;

    public void updateCategoryMenu(CategoryMenuRequestDto requestDto) {
        this.name = requestDto.name();
    }
}
