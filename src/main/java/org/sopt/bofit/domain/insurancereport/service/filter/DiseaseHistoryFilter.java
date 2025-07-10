package org.sopt.bofit.domain.insurancereport.service.filter;

import java.util.List;

import org.sopt.bofit.domain.insurance.entity.product.InsuranceProduct;
import org.sopt.bofit.domain.user.entity.UserInfo;
import org.sopt.bofit.domain.user.entity.constant.DiagnosedDisease;
import org.springframework.stereotype.Component;

@Component
public class DiseaseHistoryFilter {
	public List<InsuranceProduct> filtering(List<InsuranceProduct> products, UserInfo userInfo){
		return products.stream()
			.filter(product -> isCoveredDiseaseHistory(userInfo, product))
			.toList();
	}

	private boolean isCoveredDiseaseHistory(UserInfo userInfo, InsuranceProduct product) {
		List<DiagnosedDisease> diseaseHistory = userInfo.getDiseaseHistory();

		return diseaseHistory.stream().allMatch(disease -> isDiseaseCovered(disease, product));
	}

	private boolean isDiseaseCovered(DiagnosedDisease disease, InsuranceProduct product) {
		return switch (disease) {
			case CANCER -> isCancerCovered(product);
			case CEREBROVASCULAR -> isCerebrovascularCovered(product);
			case HEART -> isHeartCovered(product);
			case RESPIRATORY, RIVER, KIDNEY -> isSurgeryCovered(product);
			default -> true;
		};
	}

	private boolean isCancerCovered(InsuranceProduct product) {
		var cancer = product.getMajorDisease().getCancer();
		return cancer.getGeneralSurgery() > 0 && cancer.getGeneralDiagnosis() > 0;
	}

	private boolean isCerebrovascularCovered(InsuranceProduct product) {
		var cerebrovascular = product.getMajorDisease().getCerebrovascular();
		return cerebrovascular.getHemorrhageDiagnosis() > 0 && cerebrovascular.getHemorrhageSurgery() > 0;
	}

	private boolean isHeartCovered(InsuranceProduct product) {
		var heart = product.getMajorDisease().getHeart();
		return heart.getIschemicDiagnosis() > 0 && heart.getIschemicSurgery() > 0;
	}

	private boolean isSurgeryCovered(InsuranceProduct product) {
		var surgery = product.getSurgery();
		return surgery.getDiseaseSurgery().getGeneral() > 0 || surgery.getDiseaseSurgery().getType5() > 0;
	}

}
