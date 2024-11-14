package com.example.demo.domain.region.entity;

import com.example.demo.domain.entity.common.CommonConstant;
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
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @NotNull
    @Column(unique = true)
    @Pattern(regexp = CommonConstant.RegExp.DISTRICT)
    String district;

}
