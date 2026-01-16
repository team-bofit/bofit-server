package org.sopt.bofit.global.external.generativeai.openai.dto.request;

import java.util.Map;

/**
 * strict 모드에서는 모든 필드가 required 여야 함
 * strict 모드에서는 추가 속성을 허용하지 않아야 합니
 * @param name: 스키마 이름
 * @param strict: 구조 강제 모드 여부
 * @param schema: 스키마 데이터 타입
 */
public record JsonSchema (
    String name,
    boolean strict,
    Map<String, Object> schema
){

}
