package org.sopt.bofit.domain.user.entity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.annotations.Type;
import org.sopt.bofit.domain.insurancereport.entity.InsuranceReport;
import org.sopt.bofit.domain.user.entity.constant.CoveragePreference;
import org.sopt.bofit.domain.user.entity.constant.DiagnosedDisease;
import org.sopt.bofit.global.converter.CoveragePreferenceMapConverter;
import org.sopt.bofit.global.converter.DiseaseEnumListJsonConverter;
import org.sopt.bofit.global.entity.BaseEntity;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "user_info")
public class UserInfo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "insurance_report_id")
    private InsuranceReport insuranceReport;

    private int minPrice;

    private int maxPrice;

    @Type(JsonType.class)
    @Convert(converter = DiseaseEnumListJsonConverter.class)
    @Column(name = "disease_history", columnDefinition = "JSON")
    private List<DiagnosedDisease> diseaseHistory = new ArrayList<>();

    @Type(JsonType.class)
    @Convert(converter = DiseaseEnumListJsonConverter.class)
    @Column(name = "family_history", columnDefinition = "JSON")
    private List<DiagnosedDisease> familyHistory = new ArrayList<>();

    @Type(JsonType.class)
    @Convert(converter = CoveragePreferenceMapConverter.class)
    @Column(name = "coverage_preference", columnDefinition = "JSON")
    private Map<CoveragePreference, Integer> coveragePreferences = new LinkedHashMap<>();

    public UserInfo updateReport(InsuranceReport report){
        this.insuranceReport = report;
        return this;
    }
}
