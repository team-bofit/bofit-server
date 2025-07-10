package org.sopt.bofit.domain.insurance.repository;

import java.util.Optional;

import org.sopt.bofit.domain.insurance.entity.statistic.InsuranceStatistic;
import org.sopt.bofit.domain.insurance.entity.statistic.StatisticRange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceStatisticRepository extends JpaRepository<InsuranceStatistic, Long> {

	Optional<InsuranceStatistic> findByStatisticRange(StatisticRange statisticRange);
}
