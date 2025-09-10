package org.sopt.bofit.domain.comment.service.dto.request;

import java.util.Optional;

public record CommentCreateCommand(
    String content,
    Optional<String> imageUrl
) {

}
