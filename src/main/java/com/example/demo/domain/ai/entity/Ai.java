package com.example.demo.domain.ai.entity;

import com.example.demo.common.entity.BaseEntity;
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
@Table(name = "p_ai")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Ai extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @NotNull
    String ownerName;

    @NotNull
    String requestText;

    @NotNull
    String responseText;

}
