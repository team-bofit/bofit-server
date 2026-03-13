package org.sopt.bofit.domain.post.service.dto.request;

import java.util.List;

public record PostCreateCommand(
        String title,
        String content,
        String category,
        List<String> imageKeys
) {
}
