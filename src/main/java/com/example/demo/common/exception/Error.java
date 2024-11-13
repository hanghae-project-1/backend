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

	NOT_FOUND_ORDER(5000, "해당 주문을 찾을 수 없습니다."),
	RETURN_PERIOD_PASSED(5001, "주문을 취소할 수 없습니다."),
	ORDER_BEEN_CANCELED(5002, "주문이 취소되었습니다."),
	IS_NOT_YOUR_ORDER(5003, "사용자의 주문이 아닙니다."),
	INCORRECT_TOTAL_PRICE(5100, "총 금액이 맞지않습니다."),

	DUPLICATE_CATEGORYMENU_NAME(9000, "중복된 카테고리 이름입니다."),

	INTERNAL_SERVER_ERROR(9999, "서버 오류입니다.");

	final Integer code;

	final String message;


}
