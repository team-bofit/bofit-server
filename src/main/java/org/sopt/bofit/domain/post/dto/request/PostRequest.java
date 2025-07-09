package org.sopt.bofit.domain.post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;
import org.sopt.bofit.domain.post.entity.constant.PostInfoConstant;

public record PostRequest(
        @Schema(description = "글 제목", example = "아니")
        @NotBlank(message = "제목은 비어있을 수 없습니다.")
        @Length(max = PostInfoConstant.MAX_TITLE_LENGTH)
        String title,

        @Schema(description = "내용", example = "저희 대상타면 어떡하나요 ㅈㅉ로?")
        @NotBlank(message = "본문은 비어있을 수 없습니다.")
        @Length(max = PostInfoConstant.MAX_CONTENT_LENGTH, message = "내용은 {max}자 미만으로 작성해주세요.")
        String content
) {
}
