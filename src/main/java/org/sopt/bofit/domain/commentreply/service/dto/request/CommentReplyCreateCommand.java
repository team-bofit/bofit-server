package org.sopt.bofit.domain.commentreply.service.dto.request;

import java.util.Optional;

public record CommentReplyCreateCommand(
    String content,
    Optional<String> imageUrl
) {

}
