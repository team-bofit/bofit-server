package org.sopt.bofit.domain.insurancereport.service.filter;

import java.util.List;
import java.util.Set;

import org.sopt.bofit.domain.insurance.entity.product.InsuranceProduct;
import org.sopt.bofit.domain.user.entity.UserInfo;
import org.sopt.bofit.domain.user.entity.constant.CoveragePreference;
import org.springframework.stereotype.Component;

@Component
public class CoveragePreferenceFilter {

	public List<InsuranceProduct> filtering(List<InsuranceProduct> products, UserInfo userInfo){
		return products.stream()
			.filter(product -> considerPreference(userInfo, product))
			.toList();
	}

	private boolean considerPreference(UserInfo userInfo, InsuranceProduct product) {
		Set<CoveragePreference> diseaseHistory = userInfo.getCoveragePreferences().keySet();

		return diseaseHistory.stream().allMatch(disease ->  isSelectedCovered(disease, product));
	}

	private boolean isSelectedCovered(CoveragePreference preference, InsuranceProduct product) {
		return switch (preference) {
			case MAJOR_DISEASE -> isCancerCovered(product);
			case SURGERY_COVERAGE -> isDiseaseAndInjuryType5Covered(product);
			case ACCIDENT_PREDICTION -> isInjuryType5Covered(product);
			case DEATH_BENEFIT -> isDeathCovered(product);
			default -> true;
		};
	}

	private boolean isCancerCovered(InsuranceProduct product) {
		var cancer = product.getMajorDisease().getCancer();
		return cancer.getGeneralSurgery() > 0 && cancer.getGeneralDiagnosis() > 0;
	}

	private boolean isDiseaseAndInjuryType5Covered(InsuranceProduct product){
		var diseaseType5 = product.getSurgery().getDiseaseSurgery().getType5();
		var injuryType5 = product.getSurgery().getInjurySurgery().getType5();

		return diseaseType5 > 0 && injuryType5 > 0;
	}

	private boolean isInjuryType5Covered(InsuranceProduct product) {
		var injuryType5 = product.getSurgery().getInjurySurgery().getType5();
		return injuryType5 > 0;
	}

	private boolean isDeathCovered(InsuranceProduct product) {
		var injuryDeath = product.getDeath().getInjury();
		var diseaseDeath = product.getDeath().getDisease();

		return injuryDeath > 0 && diseaseDeath > 0;
	}

}
