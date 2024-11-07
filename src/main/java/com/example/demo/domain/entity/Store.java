package com.example.demo.domain.entity;

import com.example.demo.domain.entity.common.CommonConstant;
import com.example.demo.domain.entity.common.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "p_store")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Store {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	UUID id;

	@NotNull
	String name;

	@NotNull
	@Pattern(regexp = CommonConstant.RegExp.PHONE)
	String phone;

	@NotNull
	String address;

	@NotNull
	@Enumerated(EnumType.STRING)
	Status.Category category;

}
