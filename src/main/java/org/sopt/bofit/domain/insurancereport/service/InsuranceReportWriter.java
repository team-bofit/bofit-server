package org.sopt.bofit.domain.insurancereport.service;

import static org.sopt.bofit.global.external.openai.constant.OpenAiRole.*;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.sopt.bofit.domain.insurance.entity.benefit.InsuranceBenefit;
import org.sopt.bofit.domain.insurance.entity.product.InsuranceProduct;
import org.sopt.bofit.domain.insurance.entity.statistic.InsuranceStatistic;
import org.sopt.bofit.domain.insurance.service.InsuranceProductReader;
import org.sopt.bofit.domain.insurancereport.entity.InsuranceReport;
import org.sopt.bofit.domain.insurancereport.entity.ReportRationale;
import org.sopt.bofit.domain.insurancereport.entity.constant.CoverageStatus;
import org.sopt.bofit.domain.insurancereport.repository.InsuranceReportRepository;
import org.sopt.bofit.domain.insurancereport.service.filter.CoveragePreferenceFilter;
import org.sopt.bofit.domain.insurancereport.service.filter.DiseaseHistoryFilter;
import org.sopt.bofit.domain.insurancereport.service.scoringrule.ScoringRuleCalculator;
import org.sopt.bofit.domain.user.entity.User;
import org.sopt.bofit.domain.user.entity.UserInfo;
import org.sopt.bofit.global.external.openai.client.OpenAiClient;
import org.sopt.bofit.global.external.openai.dto.request.ChatRequestMessage;
import org.sopt.bofit.global.external.openai.template.OpenAiPromptManager;
import org.sopt.bofit.global.oauth.util.JsonUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InsuranceReportWriter {

	private final ScoringRuleCalculator scoringRuleCalculator;
	private final InsuranceProductReader insuranceProductReader;

	private final InsuranceReportRepository insuranceReportRepository;

	private final DiseaseHistoryFilter diseaseHistoryFilter;
	private final CoveragePreferenceFilter coveragePreferenceFilter;

	private final OpenAiPromptManager openAiPromptManager;
	private final OpenAiClient openAiClient;

	public InsuranceProduct recommendBestInsurance(
		List<InsuranceProduct> products,
		User user,
		UserInfo userInfo,
		int age
	){
		products = diseaseHistoryFilter.filtering(products, userInfo);
		products = coveragePreferenceFilter.filtering(products, userInfo);

		Optional<InsuranceProduct> scoringRuledProduct = products.stream()
			.max(Comparator.comparingDouble(product -> scoringRuleCalculator.calculatorScoringRule(user, userInfo, product, age)));

		return scoringRuledProduct.orElseGet(insuranceProductReader::getRecommendedStatusProducts);
	}

	@Transactional
	public InsuranceReport writeReport(
		InsuranceStatistic average,
		InsuranceProduct product,
		User user,
		UserInfo userInfo,
		int age
	){
		CoverageStatus cancerStatus = cancerCoverageStatus(product, average);
		CoverageStatus cerebrovascularStatus = cerebrovascularCoverageStatus(product, average);
		CoverageStatus heartDiseaseStatus = heartDiseaseCoverageStatus(product, average);
		CoverageStatus majorDiseaseStatus = CoverageStatus.judge(
			List.of(cancerStatus, cerebrovascularStatus, heartDiseaseStatus));

		CoverageStatus diseaseSurgeryStatus = diseaseSurgeryCoverageStatus(product, average);
		CoverageStatus diseaseTypeSurgeryStatus = diseaseTypeSurgeryCoverageStatus(product, average);
		CoverageStatus injurySurgeryStatus = injurySurgeryCoverageStatus(product, average);
		CoverageStatus injuryTypeSurgeryStatus = injuryTypeSurgeryCoverageStatus(product, average);
		CoverageStatus surgeryStatus = CoverageStatus.judge(
			List.of(diseaseSurgeryStatus, diseaseTypeSurgeryStatus, injurySurgeryStatus, injuryTypeSurgeryStatus));

		CoverageStatus diseaseDailyHospitalizationStatus = diseaseDailyHospitalizationCoverageStatus(product, average);
		CoverageStatus injuryDailyHospitalizationStatus = injuryDailyHospitalizationCoverageStatus(product, average);
		CoverageStatus dailyHospitalizationStatus = CoverageStatus.judge(
			List.of(diseaseDailyHospitalizationStatus, injuryDailyHospitalizationStatus));

		CoverageStatus diseaseDisabilityStatus = diseaseDisabilityCoverageStatus(product, average);
		CoverageStatus injuryDisabilityStatus = injuryDisabilityCoverageStatus(product, average);
		CoverageStatus disabilityStatus = CoverageStatus.judge(
			List.of(diseaseDisabilityStatus, injuryDisabilityStatus));

		CoverageStatus diseaseDeathStatus = diseaseDeathCoverageStatus(product, average);
		CoverageStatus injuryDeathStatus = injuryDeathCoverageStatus(product, average);
		CoverageStatus deathStatus = CoverageStatus.judge(
			List.of(diseaseDeathStatus, injuryDeathStatus));

		InsuranceReport report = InsuranceReport.builder()
			.product(product)
			.statistic(average)

			.cancer(cancerStatus)
			.cerebrovascular(cerebrovascularStatus)
			.heartDisease(heartDiseaseStatus)
			.majorDisease(majorDiseaseStatus)

			.diseaseSurgery(diseaseSurgeryStatus)
			.diseaseTypeSurgery(diseaseTypeSurgeryStatus)
			.injurySurgery(injurySurgeryStatus)
			.injuryTypeSurgery(injuryTypeSurgeryStatus)
			.surgery(surgeryStatus)

			.diseaseDailyHospitalization(diseaseDailyHospitalizationStatus)
			.injuryDailyHospitalization(injuryDailyHospitalizationStatus)
			.dailyHospitalization(dailyHospitalizationStatus)

			.diseaseDisability(diseaseDisabilityStatus)
			.injuryDisability(injuryDisabilityStatus)
			.disability(disabilityStatus)

			.diseaseDeath(diseaseDeathStatus)
			.injuryDeath(injuryDeathStatus)
			.death(deathStatus)

			.build();

		report.updateRationale(generateRationale(user, userInfo, report, age));
		user.recommendedInsurance();
		return insuranceReportRepository.save(report);
	}

	private ReportRationale generateRationale(
		User user,
		UserInfo userInfo,
		InsuranceReport report,
		int age
	){
		String content = openAiClient.sendRequest(
			List.of(
				new ChatRequestMessage(SYSTEM.getValue(), openAiPromptManager.generateReportSystemMessage()),
				new ChatRequestMessage(USER.getValue(), openAiPromptManager.generateReportRationale(user, userInfo, report, age))
			));
		return JsonUtil.parseClass(ReportRationale.class, content);
	}

	private CoverageStatus diseaseDeathCoverageStatus (InsuranceProduct product, InsuranceBenefit average){
		List<Function<InsuranceBenefit, Integer>> functions = List.of(
			p -> p.getDeath().getDisease()
		);
		return CoverageStatus.judge(getCoverageAndAverage(product, average, functions));
	}

	private CoverageStatus injuryDeathCoverageStatus (InsuranceProduct product, InsuranceBenefit average){
		List<Function<InsuranceBenefit, Integer>> functions = List.of(
			p -> p.getDeath().getInjury()
		);
		return CoverageStatus.judge(getCoverageAndAverage(product, average, functions));
	}

	private CoverageStatus diseaseDisabilityCoverageStatus (InsuranceProduct product, InsuranceBenefit average){
		List<Function<InsuranceBenefit, Integer>> functions = List.of(
			p -> p.getDisability().getDiseaseGE3PCT()
		);
		return CoverageStatus.judge(getCoverageAndAverage(product, average, functions));
	}

	private CoverageStatus injuryDisabilityCoverageStatus (InsuranceProduct product, InsuranceBenefit average){
		List<Function<InsuranceBenefit, Integer>> functions = List.of(
			p -> p.getDisability().getInjuryGE3PCT()
		);
		return CoverageStatus.judge(getCoverageAndAverage(product, average, functions));
	}

	private CoverageStatus diseaseDailyHospitalizationCoverageStatus(InsuranceProduct product, InsuranceBenefit average){
		List<Function<InsuranceBenefit, Integer>> functions = List.of(
			p -> p.getDailyHospitalization().getDisease()
		);
		return CoverageStatus.judge(getCoverageAndAverage(product, average, functions));
	}

	private CoverageStatus injuryDailyHospitalizationCoverageStatus(InsuranceProduct product, InsuranceBenefit average){
		List<Function<InsuranceBenefit, Integer>> functions = List.of(
			p -> p.getDailyHospitalization().getInjury()
		);
		return CoverageStatus.judge(getCoverageAndAverage(product, average, functions));
	}

	private CoverageStatus injurySurgeryCoverageStatus(InsuranceProduct product, InsuranceBenefit average){
		List<Function<InsuranceBenefit, Integer>> functions = List.of(
			p -> p.getSurgery().getInjurySurgery().getGeneral()
		);
		return CoverageStatus.judge(getCoverageAndAverage(product, average, functions));
	}

	private CoverageStatus injuryTypeSurgeryCoverageStatus(InsuranceProduct product, InsuranceBenefit average){
		List<Function<InsuranceBenefit, Integer>> functions = List.of(
			p -> p.getSurgery().getInjurySurgery().getType1(),
			p -> p.getSurgery().getInjurySurgery().getType2(),
			p -> p.getSurgery().getInjurySurgery().getType3(),
			p -> p.getSurgery().getInjurySurgery().getType4(),
			p -> p.getSurgery().getInjurySurgery().getType5()
		);
		return CoverageStatus.judge(getCoverageAndAverage(product, average, functions));
	}

	private CoverageStatus diseaseSurgeryCoverageStatus(InsuranceProduct product, InsuranceBenefit average){
		List<Function<InsuranceBenefit, Integer>> functions = List.of(
			p -> p.getSurgery().getDiseaseSurgery().getGeneral()
		);
		return CoverageStatus.judge(getCoverageAndAverage(product, average, functions));
	}

	private CoverageStatus diseaseTypeSurgeryCoverageStatus(InsuranceProduct product, InsuranceBenefit average){
		List<Function<InsuranceBenefit, Integer>> functions = List.of(
			p -> p.getSurgery().getDiseaseSurgery().getType1(),
			p -> p.getSurgery().getDiseaseSurgery().getType2(),
			p -> p.getSurgery().getDiseaseSurgery().getType3(),
			p -> p.getSurgery().getDiseaseSurgery().getType4(),
			p -> p.getSurgery().getDiseaseSurgery().getType5()
		);
		return CoverageStatus.judge(getCoverageAndAverage(product, average, functions));
	}

	private CoverageStatus heartDiseaseCoverageStatus(InsuranceProduct product, InsuranceBenefit average){
		List<Function<InsuranceBenefit, Integer>> functions = List.of(
			p -> p.getMajorDisease().getHeart().getIschemicDiagnosis(),
			p -> p.getMajorDisease().getHeart().getIschemicSurgery(),
			p -> p.getMajorDisease().getHeart().getArrhythmiaDiagnosis(),
			p -> p.getMajorDisease().getHeart().getArrhythmiaSurgery(),
			p -> p.getMajorDisease().getHeart().getAcuteMyocardialInfarctionDiagnosis(),
			p -> p.getMajorDisease().getHeart().getAcuteMyocardialInfarctionSurgery(),
			p -> p.getMajorDisease().getHeart().getExtendedDiagnosis(),
			p -> p.getMajorDisease().getHeart().getExtendedSurgery()
		);
		return CoverageStatus.judge(getCoverageAndAverage(product, average, functions));
	}

	private CoverageStatus cerebrovascularCoverageStatus(InsuranceProduct product, InsuranceBenefit average){
		List<Function<InsuranceBenefit, Integer>> functions = List.of(
			p -> p.getMajorDisease().getCerebrovascular().getHemorrhageDiagnosis(),
			p -> p.getMajorDisease().getCerebrovascular().getHemorrhageSurgery(),
			p -> p.getMajorDisease().getCerebrovascular().getInfarctionDiagnosis(),
			p -> p.getMajorDisease().getCerebrovascular().getInfarctionSurgery(),
			p -> p.getMajorDisease().getCerebrovascular().getOtherDiagnosis(),
			p -> p.getMajorDisease().getCerebrovascular().getOtherSurgery()
		);
		return CoverageStatus.judge(getCoverageAndAverage(product, average, functions));
	}


	private CoverageStatus cancerCoverageStatus(InsuranceProduct product, InsuranceBenefit average){
		List<Function<InsuranceBenefit, Integer>> functions = List.of(
			p -> p.getMajorDisease().getCancer().getGeneralDiagnosis(),
			p -> p.getMajorDisease().getCancer().getGeneralSurgery(),
			p -> p.getMajorDisease().getCancer().getAtypicalDiagnosis(),
			p -> p.getMajorDisease().getCancer().getAtypicalSurgery()
		);
		return CoverageStatus.judge(getCoverageAndAverage(product, average, functions));
	}

	private Map<Integer, Integer> getCoverageAndAverage(
		InsuranceProduct product,
		InsuranceBenefit average,
		List<Function<InsuranceBenefit, Integer>> functions
	) {
		return functions.stream()
			.collect(Collectors.toMap(
				function -> function.apply(product),
				function -> function.apply(average),
				(v1, v2) -> v1
			));
	}

	private Map.Entry<Integer, Integer> getCoverageAndAverage (
		InsuranceBenefit product,
		InsuranceBenefit average,
		Function<InsuranceBenefit, Integer> function
	){
		return Map.entry(function.apply(product), function.apply(average));
	}

}
