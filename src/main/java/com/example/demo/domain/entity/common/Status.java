package com.example.demo.domain.entity.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class Status {

	@Getter
	@AllArgsConstructor
	public enum Order {

		ORDER_PROGRESS("주문 진행중"),
		ORDER_CANCELED("취소"),
		SHIPPING("배달중"),
		SHIPPING_COMPLETED("배달 완료"),
		ORDER_COMPLETED("주문 완료");

		private final String description;
	}

	@Getter
	@AllArgsConstructor
	public enum Payment {

		PAYMENT_COMPLETED("결제 완료"),
		PAYMENT_FAILED("결제 실패"),
		ORDER_FINALIZED("구매 확정");

		private final String description;
	}

	@Getter
	@AllArgsConstructor
	public enum Classification {

		MAIN("메인"),
		SIDE("사이드"),
		DRINK("음료"),
		DESSERT("디저트");

		private final String description;
	}

}
