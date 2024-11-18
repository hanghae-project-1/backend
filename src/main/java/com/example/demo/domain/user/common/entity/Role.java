package com.example.demo.domain.user.common.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
	CUSTOMER("ROLE_CUSTOMER","소비자"),
	OWNER("ROLE_OWNER","판매자"),
	MANAGER("ROLE_MANAGER","관리자"),
	MASTER("ROLE_MASTER","마스터");

	private final String key;
	private final String description;

}
