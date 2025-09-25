package org.sopt.bofit.domain.insurance.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.sopt.bofit.domain.insurance.entity.product.InsuranceProduct;
import org.sopt.bofit.domain.insurance.entity.product.QInsuranceProduct;
import org.sopt.bofit.domain.insurancereport.service.dto.InsuranceOptionCommand;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class InsuranceProductCustomRepositoryImpl implements InsuranceProductCustomRepository{

    private final JPAQueryFactory queryFactory;

    public List<InsuranceProduct> findAllByAgeAndPremiumAndOptions(
        int age,
        int minPremium,
        int maxPremium,
        InsuranceOptionCommand  optionCommand
    ){
        QInsuranceProduct insuranceProduct = QInsuranceProduct.insuranceProduct;

        BooleanBuilder whereBuilder = new BooleanBuilder();
        whereBuilder.and(ageCondition(age, insuranceProduct));
        whereBuilder.and(premiumCondition(minPremium, maxPremium, insuranceProduct));
        whereBuilder.and(optionCondition(optionCommand, insuranceProduct));

        return queryFactory.selectFrom(insuranceProduct)
            .where(whereBuilder)
            .fetch();
    }

    public BooleanExpression ageCondition(int age, QInsuranceProduct insuranceProduct){
        return insuranceProduct.basicInformation.maxEnrollmentAge.goe(age).and(
            insuranceProduct.basicInformation.minEnrollmentAge.loe(age));
    }

    public BooleanExpression premiumCondition(int minPremium, int maxPremium, QInsuranceProduct insuranceProduct){
        return insuranceProduct.basicInformation.premium.between(minPremium, maxPremium);
    }

    public Predicate optionCondition(InsuranceOptionCommand optionCommand, QInsuranceProduct insuranceProduct){
        BooleanBuilder optionCondition = new BooleanBuilder();

        optionCommand.renewableType().ifPresent(renewableType ->
            optionCondition.and(insuranceProduct.basicInformation.renewableType.eq(renewableType)));

        optionCommand.refundType().ifPresent(refundType ->
            optionCondition.and(insuranceProduct.basicInformation.refundType.eq(refundType)));

        optionCommand.maturityAge().ifPresent(maturityAge ->
            optionCondition.and(insuranceProduct.basicInformation.maturityAge.eq(maturityAge)));

        optionCommand.paymentPeriod().ifPresent(period ->
            optionCondition.and(insuranceProduct.basicInformation.paymentPeriod.eq(period)));

        return optionCondition;
    }
}
