package com.example.demo.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Error Convention
 * ?*** : 해당 서비스의 종류
 * *?** : 조회(GET), 추가(POST), 수정(PATCH,PUT), 삭제(DELETE) HTTP Method에 해당하는 오류
 * **?? : 순서대로 기입
 * ------------------------------------
 * 1 : User Service
 * 2 : Menu Service
 * 3 : Store Service
 * 4 : AI Service
 * 5 : Order Service
 * 6 : Order Detail Service
 * 7 : Review Service
 * 8 : Payment Service
 * 9 : CategoryMenu Service
 * 10 : Region Service
 * ... (추가)
 */

@Getter
@AllArgsConstructor
public enum Error {

	DUPLICATE_STORE_NAME(3000, "같은 지역 내에 이미 존재하는 음식점 이름입니다."),

	NOT_FOUND_ORDER(5000, "해당 주문을 찾을 수 없습니다."),
	RETURN_PERIOD_PASSED(5001, "주문을 취소할 수 없습니다."),
	ORDER_BEEN_CANCELED(5002, "주문이 취소되었습니다."),
	IS_NOT_YOUR_ORDER(5003, "사용자의 주문이 아닙니다."),
	INCORRECT_TOTAL_PRICE(5100, "총 금액이 맞지않습니다."),

	NOT_FOUND_REVIEW(7000, "해당 리뷰를 찾을 수 없습니다."),
	PURCHASE_IS_NOT_CONFIRMED(7100, "리뷰는 구매 확정 후 작성할 수 있습니다."),
	IS_NOT_YOUR_REVIEW(7101, "본인의 주문에만 리뷰를 작성할 수 있습니다."),
	
	DUPLICATE_CATEGORYMENU_NAME(9000, "중복된 카테고리 이름입니다."),
	NOT_FOUND_CATEGORYMENU(9001, "해당 카테고리를 찾을 수 없습니다."),

	NOT_FOUND_REGION(10001, "해당 지역을 찾을 수 없습니다."),

	INTERNAL_SERVER_ERROR(9999, "서버 오류입니다.");

	final Integer code;

	final String message;


}
