package org.sopt.bofit.domain.insurancereport.repository.scoringrule;

import java.util.List;

import org.sopt.bofit.domain.insurancereport.entity.scoringrule.userinfo.UserInfoRuleType;
import org.sopt.bofit.domain.insurancereport.entity.scoringrule.userinfo.UserInfoScoringRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoScoringRuleRepository extends JpaRepository<UserInfoScoringRule, Long> {
	List<UserInfoScoringRule> findAllByUserInfoRuleType(UserInfoRuleType userInfoRuleType);
}
