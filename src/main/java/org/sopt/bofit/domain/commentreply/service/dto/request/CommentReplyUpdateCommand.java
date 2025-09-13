package org.sopt.bofit.domain.commentreply.service.dto.request;

import java.util.List;
import java.util.Optional;
import org.sopt.bofit.global.file.dto.request.UpdateImageRequest;

public record CommentReplyUpdateCommand (
    Optional<String> content,
    List<UpdateImageRequest> updatedImages,
    List<Long> deleteImageIds
) {

}
