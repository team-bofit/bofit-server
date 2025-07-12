package org.sopt.bofit.domain.insurancereport.repository;

import java.util.Optional;
import java.util.UUID;

import org.sopt.bofit.domain.insurancereport.entity.InsuranceReport;
import org.sopt.bofit.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceReportRepository extends JpaRepository<InsuranceReport, UUID> {

	@Query("select ir "
		+ "from InsuranceReport ir "
		+ "left join fetch ir.product ip "
		+ "left join fetch ir.statistic is "
		+ "where ir.id = :id")
	Optional<InsuranceReport> findByIdWithProductAndStatistic(@Param("id") UUID id);

	Optional<InsuranceReport> findFirstByUserOrderByCreatedAtDesc(User user);

}
