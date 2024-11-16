package com.example.demo.domain.store.entity;

import com.example.demo.common.entity.BaseEntity;
import com.example.demo.domain.category.entity.CategoryMenu;
import com.example.demo.domain.entity.common.CommonConstant;
import com.example.demo.domain.region.entity.Region;
import com.example.demo.domain.store.dto.request.StoreRequestDto;
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
public class Store extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	UUID id;

	@NotNull
	String name;

	@NotNull
	String ownerName;

	@NotNull
	@Pattern(regexp = CommonConstant.RegExp.PHONE)
	String phone;

	@NotNull
	String address;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "p_category_id")
	CategoryMenu categoryMenu;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "p_region_id")
	Region region;


	public void updateStore(StoreRequestDto requestDto, Region region, CategoryMenu categoryMenu) {
		this.name = requestDto.name();
		this.phone = requestDto.phone();
		this.address = requestDto.address();
		this.categoryMenu = categoryMenu;
		this.region = region;
	}

}
