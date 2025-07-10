package org.sopt.bofit.domain.user.dto.response;

import java.util.List;
import java.util.stream.Stream;

import org.sopt.bofit.domain.user.entity.constant.Job;

public record JobNameResponse(List<String> jobs) {
	public static JobNameResponse create(Job[] jobs){
		return new JobNameResponse(
			Stream.of(jobs)
				.map(Job::getDisplayName)
				.toList()
		);
	}
}
