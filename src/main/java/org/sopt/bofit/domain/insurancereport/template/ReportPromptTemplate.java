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
		추천 받은 보험 상품에 대한 이유(reasons)와 이를 핵심적으로 표현하는 키워드 칩(keywordChips)을 아래 형식대로 생성해줘.
		제시되는 모든 정보를 사용할 필요없이 상품 추천 설명에 필요한 정보만 사용해줘.
		각 reason은 반드시 한 문장으로만 작성하며 불필요한 미사어구를 제외하고 핵심을 제시해줘
		문장 길이는 짧고 명확하되, 의미가 부족하지 않도록 구성 (35 ~ 40 내외)
		각 문장은 반드시 사용자 정보 기반의 '이유 + 설계 방향'을 포함할 것
		말하듯 자연스럽고 부드럽게, 그러나 단정하고 신뢰감 있는 톤으로 작성할 것
		reasons의 각 문장은 총 3개 작성, keywordChips은 2개 작성
		나이는 언급하지 말아줘
		
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
