package org.sopt.bofit.domain.insurance.repository;

import java.util.List;
import java.util.Optional;

import org.sopt.bofit.domain.insurance.entity.product.InsuranceProduct;
import org.sopt.bofit.domain.insurance.entity.product.constant.InsuranceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceProductRepository extends JpaRepository<InsuranceProduct, Long> {

	Optional<InsuranceProduct> findFirstByStatus(InsuranceStatus insuranceStatus);

	@Query("""
		select ip
		from InsuranceProduct as ip
		where ip.basicInformation.minEnrollmentAge <= :age and ip.basicInformation.maxEnrollmentAge >= :age and
			ip.basicInformation.premium > :minPremium and ip.basicInformation.premium < :maxPremium
	""")
	List<InsuranceProduct> findAllByAgeAndPremium(
		@Param("age") int age,
		@Param("minPremium") int minPremium,
		@Param("maxPremium") int maxPremium);

}
