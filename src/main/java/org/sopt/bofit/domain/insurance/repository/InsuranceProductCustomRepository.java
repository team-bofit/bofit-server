package org.sopt.bofit.domain.insurance.repository;

import java.util.List;
import org.sopt.bofit.domain.insurance.entity.product.InsuranceProduct;
import org.sopt.bofit.domain.insurancereport.service.dto.InsuranceOptionCommand;

public interface InsuranceProductCustomRepository {

    List<InsuranceProduct> findAllByAgeAndPremiumAndOptions(
        int age,
        int minPremium, int maxPremium,
        InsuranceOptionCommand optionCommand
    );
}
