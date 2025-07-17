package org.sopt.bofit.domain.insurancereport.dto.response.surgery;

import static org.sopt.bofit.domain.insurancereport.entity.Disease.*;
import static org.sopt.bofit.domain.insurancereport.errorcode.InsuranceReportErrorCode.*;

import org.sopt.bofit.domain.insurance.entity.benefit.DiseaseSurgery;
import org.sopt.bofit.domain.insurance.entity.benefit.InjurySurgery;
import org.sopt.bofit.domain.insurance.entity.benefit.SurgeryType;
import org.sopt.bofit.domain.insurance.entity.product.InsuranceProduct;
import org.sopt.bofit.domain.insurance.entity.statistic.InsuranceStatistic;
import org.sopt.bofit.domain.insurancereport.dto.response.CompareCoverage;
import org.sopt.bofit.domain.insurancereport.entity.Disease;
import org.sopt.bofit.domain.insurancereport.entity.InsuranceReport;
import org.sopt.bofit.global.exception.customexception.BadRequestException;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record SurgerySection(
	String displayName,
	String hyphenCase,
	CompareCoverage surgery,
	SurgeryTypeSection surgeryType
) {

	public static SurgerySection getSurgerySection(String hyphenCase, InsuranceReport report){
		InsuranceProduct product = report.getProduct();
		InsuranceStatistic statistic = report.getStatistic();

		if (DISEASE_SURGERY.getHyphenCase().equalsIgnoreCase(hyphenCase))
			return createDiseaseSection(product, statistic);
		if (INJURY_SURGERY.getHyphenCase().equalsIgnoreCase(hyphenCase))
			return createInjurySection(product, statistic);
		if (DISEASE_TYPE_SURGERY.getHyphenCase().equalsIgnoreCase(hyphenCase))
			return createDiseaseTypeSection(product, statistic);
		if (INJURY_TYPE_SURGERY.getHyphenCase().equalsIgnoreCase(hyphenCase))
			return createInjuryTypeSection(product, statistic);

		throw new BadRequestException(INVALID_REPORT_SECTION);
	}

	public static SurgerySection createDiseaseSection(
		InsuranceProduct product,
		InsuranceStatistic average
	){
		DiseaseSurgery productSurgery = product.getSurgery().getDiseaseSurgery();
		DiseaseSurgery averageSurgery = average.getSurgery().getDiseaseSurgery();
		return new SurgerySection(
			Disease.DISEASE_SURGERY.getDisplayName(),
			Disease.DISEASE_SURGERY.getHyphenCase(),
			CompareCoverage.of(productSurgery.getGeneral(), averageSurgery.getGeneral()),
			null
			);
	}

	public static SurgerySection createInjurySection(
		InsuranceProduct product,
		InsuranceStatistic average
	){
		InjurySurgery productSurgery = product.getSurgery().getInjurySurgery();
		InjurySurgery averageSurgery = average.getSurgery().getInjurySurgery();
		return new SurgerySection(
			Disease.INJURY_SURGERY.getDisplayName(),
			Disease.INJURY_SURGERY.getHyphenCase(),
			CompareCoverage.of(productSurgery.getGeneral(), averageSurgery.getGeneral()),
			null
		);
	}

	public static SurgerySection createDiseaseTypeSection(
		InsuranceProduct product,
		InsuranceStatistic average
	){
		SurgeryType productSurgery = product.getSurgery().getDiseaseSurgery();
		SurgeryType averageSurgery = average.getSurgery().getDiseaseSurgery();
		return new SurgerySection(
			DISEASE_TYPE_SURGERY.getDisplayName(),
			DISEASE_TYPE_SURGERY.getHyphenCase(),
			null,
			SurgeryTypeSection.of( productSurgery, averageSurgery)
		);
	}

	public static SurgerySection createInjuryTypeSection(
		InsuranceProduct product,
		InsuranceStatistic average
	){
		SurgeryType productSurgery = product.getSurgery().getInjurySurgery();
		SurgeryType averageSurgery = average.getSurgery().getInjurySurgery();
		return new SurgerySection(
			INJURY_TYPE_SURGERY.getDisplayName(),
			INJURY_TYPE_SURGERY.getHyphenCase(),
			null,
			SurgeryTypeSection.of(productSurgery, averageSurgery)
		);
	}

}
