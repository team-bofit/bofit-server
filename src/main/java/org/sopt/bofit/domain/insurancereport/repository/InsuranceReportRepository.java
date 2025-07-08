package org.sopt.bofit.domain.insurancereport.repository;

import java.util.UUID;

import org.sopt.bofit.domain.insurancereport.entity.InsuranceReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceReportRepository extends JpaRepository<InsuranceReport, UUID> {
}
