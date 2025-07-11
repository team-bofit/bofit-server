package org.sopt.bofit.domain.user.dto.response;

import java.util.List;
import java.util.stream.Stream;

import org.sopt.bofit.domain.user.entity.constant.Job;

public record JobResponses(List<JobResponse> jobs) {

	public static JobResponses create(Job[] jobs){
		return new JobResponses(
			Stream.of(jobs)
				.map(JobResponse::create)
				.toList()
		);
	}

	private record JobResponse(
		Job job,
		String displayName
	){
		private static JobResponse create(Job job){
			return new JobResponse(job, job.getDisplayName());
		}
	}
}
