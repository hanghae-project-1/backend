package com.example.domain.entity;

import com.example.domain.entity.common.BaseEntity;
import com.example.domain.entity.common.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "P_Menu")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Menu extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	UUID id;

	@NotNull
	String name;

	@NotNull
	String content;

	@NotNull
	Integer price;

	@NotNull
	Boolean stockStatus;

	@NotNull
	String imageUrl;

	@NotNull
	@Enumerated(EnumType.STRING)
	Status.Classification classification;

}
