package org.sopt.bofit.domain.post.service.dto.request;

import org.sopt.bofit.global.file.dto.request.UpdateImageRequest;

import java.util.List;

public record PostUpdateCommand(
        String newTitle,
        String newContent,
        String newCategory,
        List<UpdateImageRequest> updatedImages,
        List<Long> deleteImageIds
) {
}
