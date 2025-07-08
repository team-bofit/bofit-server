package org.sopt.bofit.domain.insurance.repository;

import org.sopt.bofit.domain.insurance.entity.statistic.InsuranceStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceStatisticRepository extends JpaRepository<InsuranceStatistic, Long> {
}
