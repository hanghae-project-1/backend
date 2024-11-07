package com.example.domain.entity.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {

	CUSTOMER("소비자"),
	OWNER("판매자"),
	MANAGER("관리자"),
	MASTER("마스터");

	private final String description;
}
