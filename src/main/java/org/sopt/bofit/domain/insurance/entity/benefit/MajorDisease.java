package org.sopt.bofit.domain.insurance.entity.benefit;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Getter;

@Getter
@Embeddable
public class MajorDisease {

	@Embedded
	private Cancer cancer;

	@Embedded
	private Cerebrovascular cerebrovascular;

	@Embedded
	private Heart heart;
}
