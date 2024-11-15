package com.example.demo.domain.entity;

import com.example.demo.common.entity.BaseEntity;
import com.example.demo.domain.entity.common.Status;
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
@Table(name = "p_menu")
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
