package org.sopt.bofit.domain.user.dto.response;

import java.util.List;
import java.util.stream.Stream;

import org.sopt.bofit.domain.user.entity.constant.DiagnosedDisease;


public record DiagnosedDiseaseResponses(
	List<DiagnosedDiseaseResponse> diagnosedDiseases
){
	public static DiagnosedDiseaseResponses from(DiagnosedDisease[] diagnosedDiseases){
		return new DiagnosedDiseaseResponses(
			Stream.of(diagnosedDiseases)
				.map(DiagnosedDiseaseResponse::from)
				.toList()
		);
	}

	private record DiagnosedDiseaseResponse(
		DiagnosedDisease diagnosedDisease,
		String displayName,
		String description
	 ){
		private static DiagnosedDiseaseResponse from(DiagnosedDisease diagnosedDisease){
			return new DiagnosedDiseaseResponse(
				diagnosedDisease,
				diagnosedDisease.getDiseaseName(),
				diagnosedDisease.getDescription());
		}
	}
}
