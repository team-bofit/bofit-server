package org.sopt.bofit.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Slice;

import java.util.List;

public record SliceResponse<T>(
        @Schema(description = "데이터 목록") List<T> content,
        @Schema(description = "정렬 정보") SortResponse sort,
        @Schema(description = "현재 페이지 번호") int currentPage,
        @Schema(description = "페이지당 데이터 수") int size,
        @Schema(description = "첫 페이지 여부") boolean first,
        @Schema(description = "마지막 페이지 여부") boolean last
) {

    public static <T> SliceResponse<T> of(Slice<T> slice) {
        return new SliceResponse<>(
                slice.getContent(),
                SortResponse.of(slice.getSort()),
                slice.getNumber(),
                slice.getSize(),
                slice.isFirst(),
                slice.isLast()
        );
    }
}
