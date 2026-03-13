package org.sopt.bofit.domain.comment.service.dto.request;

import java.util.List;

public record CommentCreateCommand(
    String content,
    List<String> imageKeys
) {
}
