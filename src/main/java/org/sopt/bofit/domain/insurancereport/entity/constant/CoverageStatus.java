package org.sopt.bofit.domain.insurancereport.entity.constant;

import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;

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

	public static CoverageStatus judge(int coverageValue, int average) {
		return switch (Integer.compare(coverageValue, average)){
			case 1 -> POWERFUL;
			case 0 -> ENOUGH;
			case -1 -> WEAKNESS;
			default -> throw new IllegalStateException("Unexpected value: " + Integer.compare(coverageValue, average));
		};
	}

	public static CoverageStatus judge(List<CoverageStatus> coverageStatuses){
		OptionalDouble coverageValue = coverageStatuses.stream()
			.mapToInt(CoverageStatus::getPoint)
			.average();

		if(coverageValue.isPresent()){
			return judgeFromPoint(coverageValue.getAsDouble());
		}

		return WEAKNESS;
	}

	public static CoverageStatus judge(Map.Entry<Integer, Integer> coverageValueAndAverages) {
		return switch (Integer.compare(coverageValueAndAverages.getKey(), coverageValueAndAverages.getValue())){
			case 1 -> POWERFUL;
			case 0 -> ENOUGH;
			case -1 -> WEAKNESS;
			default -> throw new IllegalStateException("Unexpected value: " +
				Integer.compare(coverageValueAndAverages.getKey(), coverageValueAndAverages.getValue()));
		};
	}

	public static CoverageStatus judgeFromPoint(double point){
		if(point > ENOUGH.point)
			return POWERFUL;
		if(point > WEAKNESS.point)
			return ENOUGH;
		return WEAKNESS;
	}

	public static CoverageStatus judge(Map<Integer, Integer> coverageValueAndAverages){
		OptionalDouble coverageValue = coverageValueAndAverages.entrySet()
			.stream()
			.map(CoverageStatus::judge)
			.mapToInt(CoverageStatus::getPoint)
			.average();

		if(coverageValue.isPresent()){
			return judgeFromPoint(coverageValue.getAsDouble());
		}

		return WEAKNESS;
	}
}
