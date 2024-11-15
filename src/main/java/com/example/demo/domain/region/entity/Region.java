package com.example.demo.domain.region.entity;

import com.example.demo.common.entity.BaseEntity;
import com.example.demo.domain.entity.common.CommonConstant;
import com.example.demo.domain.region.dto.request.RegionRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "p_region")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Region extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @NotNull
    @Column(unique = true)
    @Pattern(regexp = CommonConstant.RegExp.DISTRICT)
    String district;

    public void updateRegion(RegionRequestDto requestDto) {
        this.district = requestDto.district();
    }
}
