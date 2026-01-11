package org.sopt.bofit.global.external.generativeai.openai.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ResponseFormat (
    String type,
    @JsonProperty("json_schema")
    JsonSchema jsonSchema
){

}
