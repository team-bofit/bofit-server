package org.sopt.bofit.domain.insurance.entity.benefit;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Surgery {
	private DiseaseSurgery diseaseSurgery;
	private InjurySurgery injurySurgery;
}
