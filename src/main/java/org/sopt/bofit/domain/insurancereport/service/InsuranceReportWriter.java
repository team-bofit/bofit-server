package org.sopt.bofit.domain.insurancereport.service;

import static org.sopt.bofit.global.constant.CacheConstant.INSURANCE_REPORT_CACHE_NAME;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.bofit.domain.insurance.entity.benefit.InsuranceBenefit;
import org.sopt.bofit.domain.insurance.entity.product.InsuranceProduct;
import org.sopt.bofit.domain.insurance.entity.statistic.InsuranceStatistic;
import org.sopt.bofit.domain.insurance.service.InsuranceProductReader;
import org.sopt.bofit.domain.insurancereport.dto.response.CompareCoverage;
import org.sopt.bofit.domain.insurancereport.entity.InsuranceReport;
import org.sopt.bofit.domain.insurancereport.entity.ReportRationale;
import org.sopt.bofit.domain.insurancereport.entity.constant.CoverageStatus;
import org.sopt.bofit.domain.insurancereport.repository.InsuranceReportRepository;
import org.sopt.bofit.domain.insurancereport.service.filter.CoveragePreferenceFilter;
import org.sopt.bofit.domain.insurancereport.service.filter.DiseaseHistoryFilter;
import org.sopt.bofit.domain.insurancereport.service.scoringrule.ScoringRuleCalculator;
import org.sopt.bofit.domain.user.entity.PersonalInfo;
import org.sopt.bofit.domain.user.entity.User;
import org.sopt.bofit.domain.user.entity.UserInfo;
import org.sopt.bofit.domain.user.service.UserInfoWriter;
import org.sopt.bofit.domain.user.service.UserReader;
import org.sopt.bofit.domain.user.service.UserWriter;
import org.sopt.bofit.global.external.generativeai.GenerativeAiClient;
import org.sopt.bofit.global.external.generativeai.reportrelational.GenerateReportRationaleRequest;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class InsuranceReportWriter {

	private final ScoringRuleCalculator scoringRuleCalculator;
	private final InsuranceProductReader insuranceProductReader;

	private final UserInfoWriter userInfoWriter;

    private final UserReader userReader;
    private final UserWriter userWriter;

	private final InsuranceReportRepository insuranceReportRepository;

	private final DiseaseHistoryFilter diseaseHistoryFilter;
	private final CoveragePreferenceFilter coveragePreferenceFilter;

    private final GenerativeAiClient generativeAiClient;

	@Transactional(readOnly = true)
	public InsuranceProduct recommendBestInsurance(
		List<InsuranceProduct> products,
        PersonalInfo personalInfo,
		UserInfo userInfo,
		int age
	){
		// products = diseaseHistoryFilter.filtering(products, userInfo);
		// products = coveragePreferenceFilter.filtering(products, userInfo);

		Optional<InsuranceProduct> scoringRuledProduct = products.stream()
			.max(Comparator.comparingDouble(product -> scoringRuleCalculator.calculatorScoringRule(personalInfo, userInfo, product, age)));

		return scoringRuledProduct.orElseGet(insuranceProductReader::getRecommendedStatusProducts);
	}

    public InsuranceReport createReport(
        InsuranceStatistic average,
        InsuranceProduct product,
        User user,
        UserInfo userInfo,
        PersonalInfo personalInfo,
        int age
    ){
        CoverageStatus cancerStatus = cancerCoverageStatus(product, average);
        CoverageStatus cerebrovascularStatus = cerebrovascularCoverageStatus(product, average);
        CoverageStatus heartDiseaseStatus = heartDiseaseCoverageStatus(product, average);
        CoverageStatus majorDiseaseStatus = CoverageStatus.judgeFromCoverageStatuses(
            List.of(cancerStatus, cerebrovascularStatus, heartDiseaseStatus));

        CoverageStatus diseaseSurgeryStatus = diseaseSurgeryCoverageStatus(product, average);
        CoverageStatus diseaseTypeSurgeryStatus = diseaseTypeSurgeryCoverageStatus(product, average);
        CoverageStatus injurySurgeryStatus = injurySurgeryCoverageStatus(product, average);
        CoverageStatus injuryTypeSurgeryStatus = injuryTypeSurgeryCoverageStatus(product, average);
        CoverageStatus surgeryStatus = CoverageStatus.judgeFromCoverageStatuses(
            List.of(diseaseSurgeryStatus, diseaseTypeSurgeryStatus, injurySurgeryStatus, injuryTypeSurgeryStatus));

        CoverageStatus diseaseDailyHospitalizationStatus = diseaseDailyHospitalizationCoverageStatus(product, average);
        CoverageStatus injuryDailyHospitalizationStatus = injuryDailyHospitalizationCoverageStatus(product, average);
        CoverageStatus dailyHospitalizationStatus = CoverageStatus.judgeFromCoverageStatuses(
            List.of(diseaseDailyHospitalizationStatus, injuryDailyHospitalizationStatus));

        CoverageStatus diseaseDisabilityStatus = diseaseDisabilityCoverageStatus(product, average);
        CoverageStatus injuryDisabilityStatus = injuryDisabilityCoverageStatus(product, average);
        CoverageStatus disabilityStatus = CoverageStatus.judgeFromCoverageStatuses(
            List.of(diseaseDisabilityStatus, injuryDisabilityStatus));

        CoverageStatus diseaseDeathStatus = diseaseDeathCoverageStatus(product, average);
        CoverageStatus injuryDeathStatus = injuryDeathCoverageStatus(product, average);
        CoverageStatus deathStatus = CoverageStatus.judgeFromCoverageStatuses(
            List.of(diseaseDeathStatus, injuryDeathStatus));

        InsuranceReport report = InsuranceReport.builder()
            .user(user)
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

        return report;
    }

    @Transactional
	@CachePut(cacheNames = INSURANCE_REPORT_CACHE_NAME, key = "#result.id", unless = "#result==null")
	public InsuranceReport saveReport(
		InsuranceReport report,
		User requestUser,
		UserInfo userInfo,
        PersonalInfo personalInfo
	){
        User user = userReader.getActiveById(requestUser.getId());
		InsuranceReport savedInsuranceReport = insuranceReportRepository.save(report);
		userInfoWriter.save(userInfo.updateReport(savedInsuranceReport));

        userWriter.updateUser(user, personalInfo);

		return savedInsuranceReport;
	}

    @CachePut(cacheNames = INSURANCE_REPORT_CACHE_NAME, key = "#result.id", unless = "#result==null")
	public InsuranceReport generateAndApplyRationale(
        PersonalInfo personalInfo,
		UserInfo userInfo,
		InsuranceReport report,
		int age
	){
        GenerateReportRationaleRequest request = GenerateReportRationaleRequest.create(
            personalInfo, userInfo, report, age);
        ReportRationale reportRationale = generativeAiClient.generateReportRationaleForApi(request);
        report.updateRationale(reportRationale);
        return insuranceReportRepository.save(report);
	}

    @CachePut(cacheNames = INSURANCE_REPORT_CACHE_NAME, key = "#result.id", unless = "#result==null")
    public InsuranceReport generateAndApplyRationaleForMessage(
        PersonalInfo personalInfo,
        UserInfo userInfo,
        InsuranceReport report,
        int age
    ){
        GenerateReportRationaleRequest request = GenerateReportRationaleRequest.create(
            personalInfo, userInfo, report, age);
        ReportRationale reportRationale = generativeAiClient.generateReportRationaleForMessage(request);
        report.updateRationale(reportRationale);
        return insuranceReportRepository.save(report);
    }

	private CoverageStatus diseaseDeathCoverageStatus (InsuranceProduct product, InsuranceBenefit average){
		List<Function<InsuranceBenefit, Integer>> functions = List.of(
			p -> p.getDeath().getDisease()
		);
		return CoverageStatus.judgeFromCompareCoverages(getCompareCoverages(product, average, functions));
	}

	private CoverageStatus injuryDeathCoverageStatus (InsuranceProduct product, InsuranceBenefit average){
		List<Function<InsuranceBenefit, Integer>> functions = List.of(
			p -> p.getDeath().getInjury()
		);
		return CoverageStatus.judgeFromCompareCoverages(getCompareCoverages(product, average, functions));
	}

	private CoverageStatus diseaseDisabilityCoverageStatus (InsuranceProduct product, InsuranceBenefit average){
		List<Function<InsuranceBenefit, Integer>> functions = List.of(
			p -> p.getDisability().getDiseaseGE3PCT()
		);
		return CoverageStatus.judgeFromCompareCoverages(getCompareCoverages(product, average, functions));
	}

	private CoverageStatus injuryDisabilityCoverageStatus (InsuranceProduct product, InsuranceBenefit average){
		List<Function<InsuranceBenefit, Integer>> functions = List.of(
			p -> p.getDisability().getInjuryGE3PCT()
		);
		return CoverageStatus.judgeFromCompareCoverages(getCompareCoverages(product, average, functions));
	}

	private CoverageStatus diseaseDailyHospitalizationCoverageStatus(InsuranceProduct product, InsuranceBenefit average){
		List<Function<InsuranceBenefit, Integer>> functions = List.of(
			p -> p.getDailyHospitalization().getDisease()
		);
		return CoverageStatus.judgeFromCompareCoverages(getCompareCoverages(product, average, functions));
	}

	private CoverageStatus injuryDailyHospitalizationCoverageStatus(InsuranceProduct product, InsuranceBenefit average){
		List<Function<InsuranceBenefit, Integer>> functions = List.of(
			p -> p.getDailyHospitalization().getInjury()
		);
		return CoverageStatus.judgeFromCompareCoverages(getCompareCoverages(product, average, functions));
	}

	private CoverageStatus injurySurgeryCoverageStatus(InsuranceProduct product, InsuranceBenefit average){
		List<Function<InsuranceBenefit, Integer>> functions = List.of(
			p -> p.getSurgery().getInjurySurgery().getGeneral()
		);
		return CoverageStatus.judgeFromCompareCoverages(getCompareCoverages(product, average, functions));
	}

	private CoverageStatus injuryTypeSurgeryCoverageStatus(InsuranceProduct product, InsuranceBenefit average){
		List<Function<InsuranceBenefit, Integer>> functions = List.of(
			p -> p.getSurgery().getInjurySurgery().getType1(),
			p -> p.getSurgery().getInjurySurgery().getType2(),
			p -> p.getSurgery().getInjurySurgery().getType3(),
			p -> p.getSurgery().getInjurySurgery().getType4(),
			p -> p.getSurgery().getInjurySurgery().getType5()
		);
		return CoverageStatus.judgeFromCompareCoverages(getCompareCoverages(product, average, functions));
	}

	private CoverageStatus diseaseSurgeryCoverageStatus(InsuranceProduct product, InsuranceBenefit average){
		List<Function<InsuranceBenefit, Integer>> functions = List.of(
			p -> p.getSurgery().getDiseaseSurgery().getGeneral()
		);
		return CoverageStatus.judgeFromCompareCoverages(getCompareCoverages(product, average, functions));
	}

	private CoverageStatus diseaseTypeSurgeryCoverageStatus(InsuranceProduct product, InsuranceBenefit average){
		List<Function<InsuranceBenefit, Integer>> functions = List.of(
			p -> p.getSurgery().getDiseaseSurgery().getType1(),
			p -> p.getSurgery().getDiseaseSurgery().getType2(),
			p -> p.getSurgery().getDiseaseSurgery().getType3(),
			p -> p.getSurgery().getDiseaseSurgery().getType4(),
			p -> p.getSurgery().getDiseaseSurgery().getType5()
		);
		return CoverageStatus.judgeFromCompareCoverages(getCompareCoverages(product, average, functions));
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
		return CoverageStatus.judgeFromCompareCoverages(getCompareCoverages(product, average, functions));
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
		return CoverageStatus.judgeFromCompareCoverages(getCompareCoverages(product, average, functions));
	}


	private CoverageStatus cancerCoverageStatus(InsuranceProduct product, InsuranceBenefit average){
		List<Function<InsuranceBenefit, Integer>> functions = List.of(
			p -> p.getMajorDisease().getCancer().getGeneralDiagnosis(),
			p -> p.getMajorDisease().getCancer().getGeneralSurgery(),
			p -> p.getMajorDisease().getCancer().getAtypicalDiagnosis(),
			p -> p.getMajorDisease().getCancer().getAtypicalSurgery()
		);
		return CoverageStatus.judgeFromCompareCoverages(getCompareCoverages(product, average, functions));
	}

	private List<CompareCoverage> getCompareCoverages(
		InsuranceProduct product,
		InsuranceBenefit average,
		List<Function<InsuranceBenefit, Integer>> functions
	) {
		return functions.stream()
			.map(function ->  new CompareCoverage(
				function.apply(product),
				function.apply(average)
			))
			.toList();
	}

}
