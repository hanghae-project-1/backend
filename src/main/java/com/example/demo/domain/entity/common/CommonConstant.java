package com.example.demo.domain.entity.common;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CommonConstant {

	@UtilityClass
	public static class RegExp {
		public static final String EMAIL = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";

		public static final String PHONE = "^(01[0-9]|02|[6-9])[-.]?\\d{3,4}[-.]?\\d{4}$";

		public static final String DISTRICT = "^서울특별시\\s+[가-힣]+구$";

	}

}
