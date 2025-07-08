package org.sopt.bofit.domain.insurance.repository;

import org.sopt.bofit.domain.insurance.entity.product.InsuranceProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceProductRepository extends JpaRepository<InsuranceProduct, Long> {
}
