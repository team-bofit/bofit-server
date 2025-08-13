package org.sopt.bofit.domain.insurance.entity.product;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExtraInformation {

	@Column(name = "remarks", length = 1000)
	private String remarks;

	@Column(name = "external_uri")
	private String externalUri;

	@Column(name = "information_source")
	private String source;

	@Builder
	private ExtraInformation(String remarks, String externalUri, String source) {
		this.remarks = remarks;
		this.externalUri = externalUri;
		this.source = source;
	}
}
