package org.sopt.bofit.domain.user.dto.response;

import java.util.List;
import java.util.stream.Stream;

import org.sopt.bofit.domain.user.entity.constant.CoveragePreference;

public record CoveragePreferenceResponses (
	List<CoveragePreferenceResponse> coveragePreferenceResponses
){

	public static CoveragePreferenceResponses from(CoveragePreference[] coveragePreferences){
		return new CoveragePreferenceResponses(Stream.of(coveragePreferences)
			.map(CoveragePreferenceResponse::create)
			.toList());
	}

	private record CoveragePreferenceResponse(
		CoveragePreference coveragePreference,
		String description
	){
		private static CoveragePreferenceResponse create(CoveragePreference coveragePreference){
			return new CoveragePreferenceResponse(coveragePreference, coveragePreference.getDescription());
		}
	}
}
