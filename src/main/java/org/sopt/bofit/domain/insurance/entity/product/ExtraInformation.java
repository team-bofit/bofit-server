package org.sopt.bofit.domain.insurance.entity.product;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class ExtraInformation {

	@Column(name = "remarks", length = 1000)
	private String remarks;

	@Column(name = "external_uri")
	private String externalUri;

	@Column(name = "informaion_source")
	private String source;
}
