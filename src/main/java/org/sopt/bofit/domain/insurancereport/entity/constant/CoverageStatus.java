package org.sopt.bofit.domain.insurancereport.entity.constant;

import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;

import org.sopt.bofit.domain.insurancereport.dto.response.CompareCoverage;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CoverageStatus {
	POWERFUL("강력", 3),
	ENOUGH("충분", 2),
	WEAKNESS("부족", 1)
	;
	private final String description;
	private final int point;

	public static CoverageStatus judgeFromCompareCoverages(int coverageValue, int average) {
		return switch (Integer.compare(coverageValue, average)){
			case 1 -> POWERFUL;
			case 0 -> ENOUGH;
			case -1 -> WEAKNESS;
			default -> throw new IllegalStateException("Unexpected value: " + Integer.compare(coverageValue, average));
		};
	}

	public static CoverageStatus judgeFromCoverageStatuses(List<CoverageStatus> coverageStatuses){
		OptionalDouble coverageValue = coverageStatuses.stream()
			.mapToInt(CoverageStatus::getPoint)
			.average();

		if(coverageValue.isPresent()){
			return judgeFromPoint(coverageValue.getAsDouble());
		}

		return WEAKNESS;
	}

	public static CoverageStatus judgeFromPoint(double point){
		if(point > ENOUGH.point)
			return POWERFUL;
		if(point > WEAKNESS.point)
			return ENOUGH;
		return WEAKNESS;
	}

	public static CoverageStatus judgeFromCompareCoverage(CompareCoverage compareCoverage){
		return switch (Integer.compare(compareCoverage.productCoverage(), compareCoverage.averageCoverage())){
			case 1 -> POWERFUL;
			case 0 -> ENOUGH;
			case -1 -> WEAKNESS;
			default -> throw new IllegalStateException("Unexpected value: " +
				Integer.compare(compareCoverage.productCoverage(), compareCoverage.averageCoverage()));
		};
	}

	public static CoverageStatus judgeFromCompareCoverages(List<CompareCoverage> compareCoverages){
		OptionalDouble coverageValue = compareCoverages.stream()
			.map(CoverageStatus::judgeFromCompareCoverage)
			.mapToInt(CoverageStatus::getPoint)
			.average();

		if(coverageValue.isPresent()){
			return judgeFromPoint(coverageValue.getAsDouble());
		}

		return WEAKNESS;
	}

}
