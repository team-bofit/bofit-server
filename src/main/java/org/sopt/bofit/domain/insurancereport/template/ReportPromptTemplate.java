package org.sopt.bofit.domain.insurancereport.template;

import java.util.stream.Collectors;

import org.sopt.bofit.domain.insurance.entity.product.InsuranceProduct;
import org.sopt.bofit.domain.insurancereport.entity.InsuranceReport;
import org.sopt.bofit.domain.user.entity.User;
import org.sopt.bofit.domain.user.entity.UserInfo;
import org.sopt.bofit.domain.user.entity.constant.CoveragePreference;
import org.sopt.bofit.domain.user.entity.constant.DiagnosedDisease;
import org.sopt.bofit.global.external.openai.template.OpenAiPromptTemplate;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReportPromptTemplate {
	private final static String DELIMITER = ", ";

	private final static String SYSTEM_MESSAGE = "너는 보험 리포트를 작성하는 전문가야. 아래 사용자 정보를 바탕으로, 요구사항에 맞춰 보험 리포트 설명을 작성해줘.";
	public static String recommendReasonAndKeywordChip(
		User user,
		UserInfo userInfo,
		InsuranceReport report,
		int age
	){
		return OpenAiPromptTemplate.createDefaultMessage(
    """
    reasons max size < 5, keywordChips max size < 3
    reasons example: "운전이 잦아 상해 위험 대비가 필요해요", "미혼인 점을 참고해 실제 치료비 중심으로 구성했어요", "근골격계 이력으로 병원비 보장을 강화했어요"
    keywordChips example: "중대 질환 든든 보장", "합리적인 보험료"
    """ +
			userDetailTemplate(user, userInfo, age)
			+ reportInfoTemplate(report, report.getProduct()),
    """
     {
    	"reasons": [
    		"<보험 상품 추천 이유>"
    	],
    	"keywordChips": [
    		"<보험 상품 핵심 키워드>"
    	]
    }
    """
			);
	}

	public static String userDetailTemplate(User user, UserInfo userInfo, int age){
		return """
   			### 사용자 정보
			- 나이: %d
			- 성별: %s
			- 직업: %s
			- 결혼 여부: %s
			- 자녀 유무: %s
			- 운전 여부: %s
			- 병력: %s
			- 가족력: %s
			- 보장 니즈: %s
			- 희망 보험료: %d ~ %d
			
				"""
			.formatted(
				age,
				user.getGender().getDisplayName(),
				user.getJob().getDisplayName(),
				user.isMarried(),
				user.isHasChild(),
				user.isDriver(),
				userInfo.getDiseaseHistory().stream()
					.map(DiagnosedDisease::getDiseaseName)
					.collect(Collectors.joining(DELIMITER)),
				userInfo.getFamilyHistory().stream()
					.map(DiagnosedDisease::getDiseaseName)
					.collect(Collectors.joining(DELIMITER)),
				userInfo.getCoveragePreferences().keySet().stream()
					.map(CoveragePreference::getDescription)
					.collect(Collectors.joining(DELIMITER)),
				userInfo.getMinPrice(), userInfo.getMaxPrice()
		);
	}

	public static String reportInfoTemplate(InsuranceReport report, InsuranceProduct product){
		return
   			"""
			### 추천 받은 상품 summary
			%s
			
			추천된 보험 상품 비고: %s
			"""
			.formatted(
				report.toReportSummaryString(),
				product.getExtraInformation().getRemarks());
	}


	public static String systemMessage(){
		return SYSTEM_MESSAGE;
	}
}
