package org.sopt.bofit.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.sopt.bofit.domain.user.entity.constant.CoveragePreference;
import org.sopt.bofit.domain.user.entity.constant.DiagnosedDisease;
import org.sopt.bofit.global.converter.CoveragePreferenceMapConverter;
import org.sopt.bofit.global.converter.DiseaseEnumListJsonConverter;
import org.sopt.bofit.global.entity.BaseEntity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    private int minPrice;

    private int maxPrice;

    @Convert(converter = DiseaseEnumListJsonConverter.class)
    @Column(name = "disease_history", columnDefinition = "JSON")
    private List<DiagnosedDisease> diseaseHistory = new ArrayList<>();

    @Convert(converter = DiseaseEnumListJsonConverter.class)
    @Column(name = "family_history", columnDefinition = "JSON")
    private List<DiagnosedDisease> familyHistory = new ArrayList<>();

    @Convert(converter = CoveragePreferenceMapConverter.class)
    @Column(name = "coverage_preference", columnDefinition = "JSON")
    private Map<CoveragePreference, Integer> coveragePreferences = new LinkedHashMap<>();
}
