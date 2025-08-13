package org.sopt.bofit.domain.insurance.entity.benefit;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MajorDisease {

	@Embedded
	private Cancer cancer;

	@Embedded
	private Cerebrovascular cerebrovascular;

	@Embedded
	private Heart heart;
}
