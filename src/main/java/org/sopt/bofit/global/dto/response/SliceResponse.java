package org.sopt.bofit.global.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import org.springframework.data.domain.Slice;

public record SliceResponse<T, C>(
        @Schema(description = "데이터 목록") List<T> content,
        @Schema(description = "다음 커서") C nextCursor,
        @Schema(description = "마지막 페이지 여부") boolean isLast
) {
    public static <T extends CursorProvider<C>, C> SliceResponse<T, C> from(Slice<T> slice) {
        List<T> content = slice.getContent();
        C nextCursor = !slice.isLast() && !content.isEmpty()
                ? content.get(content.size() - 1).nextCursor()
                : null;

        return new SliceResponse<>(content, nextCursor, slice.isLast());
    }
}
