package org.sopt.bofit.domain.post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.sopt.bofit.domain.post.entity.constant.PostInfoConstant;
import org.sopt.bofit.domain.post.service.dto.request.PostUpdateCommand;
import org.sopt.bofit.global.annotation.ValidPostCategory;
import org.sopt.bofit.global.file.dto.request.UpdateImageRequest;

import java.util.List;

public record PostUpdateRequest(
        @Schema(description = "제목", example = "ㅇㅇ")
        @NotBlank(message = "제목은 비어있을 수 없습니다.")
        @Length(max = PostInfoConstant.MAX_TITLE_LENGTH, message = "제목은 {max}자 미만으로 작성해주세요.")
        String newTitle,

        @Schema(description = "내용", example = "ㅇㅇㅇ")
        @NotBlank(message = "본문은 비어있을 수 없습니다.")
        @Length(max = PostInfoConstant.MAX_CONTENT_LENGTH, message = "내용은 {max}자 미만으로 작성해주세요.")
        String newContent,

        @Schema(description = "카테고리", example = "INFORMATION")
        @NotBlank(message = "카테고리는 비어있을 수 없습니다.")
        @ValidPostCategory
        String newCategory,

        @Schema(description = "수정된 이미지 목록")
        @NotNull(message = "수정된 url 목록은  null 일 수 없습니다.")
        @Valid
        List<UpdateImageRequest> updatedImages,

        @Schema(description = "삭제할 이미지 ID 목록")
        @NotNull(message = "삭제된 url 목록은  null 일 수 없습니다.")
        @Valid
        List<@NotNull Long> deleteImageIds

) {
   public PostUpdateCommand toCommand(){
        return new PostUpdateCommand(this.newTitle, this.newContent, this.newCategory, this.updatedImages, this.deleteImageIds);
   }
}
