package org.sopt.bofit.domain.insurancereport.dto.response.majordisease;

import static org.sopt.bofit.domain.insurancereport.entity.Disease.*;
import static org.sopt.bofit.domain.insurancereport.errorcode.InsuranceReportErrorCode.*;

import java.util.List;

import org.sopt.bofit.domain.insurance.entity.benefit.Cancer;
import org.sopt.bofit.domain.insurance.entity.benefit.Cerebrovascular;
import org.sopt.bofit.domain.insurance.entity.benefit.Heart;
import org.sopt.bofit.domain.insurance.entity.product.InsuranceProduct;
import org.sopt.bofit.domain.insurance.entity.statistic.InsuranceStatistic;
import org.sopt.bofit.domain.insurancereport.constant.AdditionalInfo;
import org.sopt.bofit.domain.insurancereport.entity.Disease;
import org.sopt.bofit.domain.insurancereport.entity.InsuranceReport;
import org.sopt.bofit.global.exception.customexception.BadRequestException;

public record MajorDiseaseSection(
	String displayName,
	String hyphenCase,
	String additionalInfo,
	List<MajorDiseaseSubSection> sections
) {

	public static MajorDiseaseSection getMajorDiseaseSection(String hyphenCase, InsuranceReport report){
		InsuranceProduct product = report.getProduct();
		InsuranceStatistic statistic = report.getStatistic();

		if(CANCER.getHyphenCase().equalsIgnoreCase(hyphenCase))
			return createCancerSection(product, statistic);
		if (CEREBROVASCULAR.getHyphenCase().equalsIgnoreCase(hyphenCase))
			return createCerebrovascularSection(product, statistic);
		if (HEART.getHyphenCase().equalsIgnoreCase(hyphenCase))
			return createHeartSection(product, statistic);

		throw new BadRequestException(INVALID_REPORT_SECTION);
	}

	public static MajorDiseaseSection createCancerSection (
		InsuranceProduct product, InsuranceStatistic average){
		Cancer productCancer = product.getMajorDisease().getCancer();
		Cancer averageCancer = average.getMajorDisease().getCancer();

		return new MajorDiseaseSection(
			CANCER.getDisplayName(),
			CANCER.getHyphenCase(),
			AdditionalInfo.CANCER.getInformation(),
			List.of(
				MajorDiseaseSubSection.of(
					Disease.GENERAL_CANCER,
					productCancer.getGeneralDiagnosis(), averageCancer.getGeneralDiagnosis(),
					productCancer.getGeneralSurgery(), averageCancer.getGeneralSurgery()),

				MajorDiseaseSubSection.of(
					Disease.ATYPICAL_CANCER,
					productCancer.getAtypicalDiagnosis(), averageCancer.getAtypicalDiagnosis(),
					productCancer.getAtypicalSurgery(), averageCancer.getAtypicalSurgery())
			)
		);
	}

	public static MajorDiseaseSection createCerebrovascularSection (
		InsuranceProduct product, InsuranceStatistic average){
		Cerebrovascular productCerebrovascular = product.getMajorDisease().getCerebrovascular();
		Cerebrovascular averageCerebrovascular = average.getMajorDisease().getCerebrovascular();

		return new MajorDiseaseSection(
			Disease.CEREBROVASCULAR.getDisplayName(),
			Disease.CEREBROVASCULAR.getHyphenCase(),
			AdditionalInfo.CEREBROVASCULAR.getInformation(),
			List.of(
				MajorDiseaseSubSection.of(
					Disease.CEREBRAL_HEMORRHAGE,
					productCerebrovascular.getHemorrhageDiagnosis(), averageCerebrovascular.getHemorrhageDiagnosis(),
					productCerebrovascular.getHemorrhageSurgery(), averageCerebrovascular.getHemorrhageSurgery()),
				MajorDiseaseSubSection.of(
					Disease.CEREBRAL_INFARCTION,
					productCerebrovascular.getInfarctionDiagnosis(), averageCerebrovascular.getInfarctionDiagnosis(),
					productCerebrovascular.getInfarctionSurgery(), averageCerebrovascular.getInfarctionSurgery()),
				MajorDiseaseSubSection.of(
					Disease.OTHER_CEREBROVASCULAR,
					productCerebrovascular.getOtherDiagnosis(), averageCerebrovascular.getOtherDiagnosis(),
				productCerebrovascular.getOtherSurgery(), averageCerebrovascular.getOtherSurgery())
			)
		);
	}

	public static MajorDiseaseSection createHeartSection (
		InsuranceProduct product, InsuranceStatistic average){
		Heart productHeart = product.getMajorDisease().getHeart();
		Heart averageHeart = average.getMajorDisease().getHeart();

		return new MajorDiseaseSection(
			Disease.HEART.getDisplayName(),
			Disease.HEART.getHyphenCase(),
			AdditionalInfo.HEART_DISEASE.getInformation(),
			List.of(
					MajorDiseaseSubSection.of(
						Disease.ACUTE_MYOCARDIAL_INFARCTION,
						productHeart.getAcuteMyocardialInfarctionDiagnosis(), averageHeart.getAcuteMyocardialInfarctionDiagnosis(),
						productHeart.getAcuteMyocardialInfarctionSurgery(), averageHeart.getAcuteMyocardialInfarctionSurgery()),
					MajorDiseaseSubSection.of(
						Disease.ISCHEMIC_HEART,
						productHeart.getIschemicDiagnosis(), averageHeart.getIschemicDiagnosis(),
						productHeart.getIschemicSurgery(), averageHeart.getIschemicSurgery()),
					MajorDiseaseSubSection.of(
						Disease.EXTENDED_HEART,
						productHeart.getExtendedDiagnosis(), averageHeart.getExtendedDiagnosis(),
						productHeart.getExtendedSurgery(), averageHeart.getExtendedSurgery()),
					MajorDiseaseSubSection.of(
						Disease.ARRHYTHMIA_HEART_FAILURE,
						productHeart.getArrhythmiaDiagnosis(), averageHeart.getArrhythmiaDiagnosis(),
						productHeart.getArrhythmiaSurgery(), averageHeart.getArrhythmiaSurgery())
			)
		);
	}
}
