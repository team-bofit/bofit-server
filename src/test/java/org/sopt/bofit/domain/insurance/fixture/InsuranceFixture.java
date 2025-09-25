package org.sopt.bofit.domain.insurance.fixture;


import java.util.Optional;
import org.sopt.bofit.domain.insurancereport.service.dto.InsuranceOptionCommand;

public class InsuranceFixture {

    public static InsuranceOptionCommand getEmptyInsuranceOptionCommand() {
        return new InsuranceOptionCommand(
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty());
    }
}
