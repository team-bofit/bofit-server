package org.sopt.bofit.domain.insurancereport.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Type;
import org.sopt.bofit.global.converter.JsonStringListConverter;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReportRationale {

	@Type(JsonType.class)
	@Column(name = "reasons", columnDefinition = "JSON")
	@Convert(converter = JsonStringListConverter.class)
	private List<String> reasons = new ArrayList<>();

	@Type(JsonType.class)
	@Column(name = "keyword_chips", columnDefinition = "JSON")
	@Convert(converter = JsonStringListConverter.class)
	private List<String> keywordChips = new ArrayList<>();
}
