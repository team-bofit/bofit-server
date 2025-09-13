package org.sopt.bofit.domain.comment.service.dto.request;

import java.util.List;
import java.util.Optional;
import org.sopt.bofit.global.file.dto.request.UpdateImageRequest;

public record CommentUpdateCommand(
    Optional<String> content,
    List<UpdateImageRequest> updatedImages,
    List<Long> deleteImageIds
) {

}
