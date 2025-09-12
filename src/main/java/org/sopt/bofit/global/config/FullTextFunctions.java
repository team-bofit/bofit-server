package org.sopt.bofit.global.config;

import org.hibernate.boot.model.FunctionContributions;
import org.hibernate.boot.model.FunctionContributor;
import org.hibernate.query.sqm.function.SqmFunctionRegistry;
import org.hibernate.type.StandardBasicTypes;


public class FullTextFunctions implements FunctionContributor {

    @Override
    public void contributeFunctions(FunctionContributions contributions) {
        SqmFunctionRegistry registry = contributions.getFunctionRegistry();

        registry.registerPattern(
                "match_language_mode",
                "match (?1, ?2, ?3) against (?4 in natural language mode)",
                contributions.getTypeConfiguration()
                        .getBasicTypeRegistry()
                        .resolve(StandardBasicTypes.DOUBLE)

        );

    }
}
