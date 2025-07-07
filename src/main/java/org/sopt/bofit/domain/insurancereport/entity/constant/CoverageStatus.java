package org.sopt.bofit.domain.insurancereport.entity.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CoverageStatus {
	POWERFUL("강력"),
	ENOUGH("충분"),
	WEAKNESS("약함")
	;
	private final String description;
}
