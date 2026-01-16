package org.sopt.bofit.global.external.generativeai.openai.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

public record ResponseFormat (
    String type,
    @JsonProperty("json_schema")
    JsonSchema jsonSchema
){

    public static ResponseFormat createReportRationaleSchema(){
        Map<String, Object> reasonsProperty = Map.of(
            "type", "array",
            "description", """ 
                보험 상품 추천 이유 (3개). 각 이유는 '이유 + 설계 방향'을 포함하며 '~해요'체로 끝나는 한 문장이어야 함.
                예시: "간질환과 뇌혈관·심장 질환 병력이 있어 주요 질환 및 수술 보장 범위를 충분하게 설계했어요.", 
                    "운전자로서 예기치 않은 사고 위험을 고려해 상해 관련 수술과 장애 보장을 강하게 반영했어요.", 
                    "수술 발생 시 보장을 중점에 두고 통원·입원비보다 수술비와 중대질환 위주로 설계했어요."
                """,
            "items", Map.of("type", "string")
        );

        Map<String, Object> keywordChipsProperty = Map.of(
            "type", "array",
            "description", """
                상품을 표현하는 핵심 키워드 (2개).
                예시: "중대 질환·수술 든든 설계", "합리적인 보험료"
                """,
            "items", Map.of("type", "string")
        );

        Map<String, Object> schemaDefinition = Map.of(
            "type", "object",
            "properties", Map.of(
                "reasons", reasonsProperty,
                "keywordChips", keywordChipsProperty
            ),
            "required", List.of("reasons", "keywordChips"),
            "additionalProperties", false
        );

        return new ResponseFormat(
            "json_schema",
            new JsonSchema(
                "insurance_report_rationale",
                true,
                schemaDefinition
            )
        );
    }

}
