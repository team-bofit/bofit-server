package org.sopt.bofit.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Sort;

import java.util.List;

@Schema(description = "정렬 정보 DTO")
public record SortResponse(
        @Schema(description = "정렬 기준 목록") List<Order> orders
) {
    public static SortResponse of(Sort sort) {
        return new SortResponse(
                sort.stream()
                        .map(order -> new Order(order.getProperty(), order.getDirection().name()))
                        .toList()
        );
    }

    @Schema(description = "정렬 기준 객체")
    public record Order(
            @Schema(description = "정렬 필드명") String property,
            @Schema(description = "정렬 방향 (ASC 또는 DESC)") String direction
    ) {
        public static Order of(Sort.Order order) {
            return new Order(order.getProperty(), order.getDirection().name());
        }
    }
}