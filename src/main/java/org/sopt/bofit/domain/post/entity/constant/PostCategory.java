package org.sopt.bofit.domain.post.entity.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum PostCategory {
    QNA("보험 QnA"),
    INFORMATION("정보 공유"),
    CONVERSATION("사담")
    ;

    private String description;
}
