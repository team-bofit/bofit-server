package org.sopt.bofit.domain.user.dto.response;

import java.util.List;
import java.util.stream.Stream;

import org.sopt.bofit.domain.user.entity.constant.DiagnosedDisease;

public record DiagnosedDiseaseNameResponse (
	List<String> diagnosedDiseaseNames
){

	public static DiagnosedDiseaseNameResponse create(DiagnosedDisease[] diagnosedDiseases){
		return new DiagnosedDiseaseNameResponse(
			Stream.of(diagnosedDiseases)
				.map(DiagnosedDisease::getDiseaseName)
				.toList()
		);
	}
}
