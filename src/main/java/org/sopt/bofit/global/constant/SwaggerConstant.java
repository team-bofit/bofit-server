package org.sopt.bofit.global.constant;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SwaggerConstant {


	public final static String TAG_NAME_COMMUNITY = "Community";
	public final static String TAG_DESCRIPTION_COMMUNITY = "커뮤니티 관련 API";

	public final static String TAG_NAME_USER_INFO = "UserInfo";
	public final static String TAG_DESCRIPTION_USER_INFO = "유저 정보 관련 API";

	public final static String TAG_NAME_INSURANCE = "Insurance";
	public final static String TAG_DESCRIPTION_INSURANCE = "보험 관련 API";

	public final static String TAG_NAME_KAKAO_LOGIN = "Login";
	public final static String TAG_DESCRIPTION_KAKAO_LOGIN = "카카오 로그인 관련 API";

}
