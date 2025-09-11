package org.sopt.bofit.domain.commentreply.service.dto.request;

import java.util.List;

public record CommentReplyCreateCommand(
    String content,
    List<String> imageUrls
) {

}
