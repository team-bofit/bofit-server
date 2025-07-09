package org.sopt.bofit.domain.post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

public record PostRequest(
        @Schema(description = "글 제목", example = "아니")
        @NotBlank(message = "제목은 비어있을 수 없습니다.")
        @Length(max = 30, message = "제목은 30자 미만으로 입력해주세요.")
        String title,

        @Schema(description = "내용", example = "저희 대상타면 어떡하나요 ㅈㅉ로?")
        @NotBlank(message = "본문은 비어있을 수 없습니다.")
        @Length(max = 3000, message = "내용은 3000자 미만으로 작성해주세요.")
        String content
) {
}
