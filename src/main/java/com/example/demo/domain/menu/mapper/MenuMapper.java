package com.example.demo.domain.menu.mapper;

import com.example.demo.domain.menu.dto.request.MenuRequestDto;
import com.example.demo.domain.menu.dto.response.MenuResponseDto;
import com.example.demo.domain.menu.entity.Menu;
import com.example.demo.domain.store.entity.Store;
import org.springframework.stereotype.Component;

@Component
public class MenuMapper {

	public Menu toMenuEntity(MenuRequestDto requestDto, Store store) {

		return Menu.builder()
				.name(requestDto.name())
				.content(requestDto.content())
				.price(requestDto.price())
				.stockStatus(requestDto.stockStatus())
				.classification(requestDto.classification())
				.store(store)
				.build();

	}

	public MenuResponseDto toMenuResponseDto(Menu menu) {
		return new MenuResponseDto(
				menu.getName(),
				menu.getContent(),
				menu.getPrice(),
				menu.getStockStatus(),
				menu.getClassification()
		);
	}

}
