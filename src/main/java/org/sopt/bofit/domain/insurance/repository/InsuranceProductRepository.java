package org.sopt.bofit.domain.insurance.repository;

import java.util.List;
import java.util.Optional;

import org.sopt.bofit.domain.insurance.entity.product.InsuranceProduct;
import org.sopt.bofit.domain.insurance.entity.product.constant.InsuranceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceProductRepository extends JpaRepository<InsuranceProduct, Long> {

	Optional<InsuranceProduct> findFirstByStatus(InsuranceStatus insuranceStatus);

}
