package org.ong.dryforest.service;

import lombok.Getter;
import java.util.List;

@Getter
public class PageResponse<T> {

    private final List<T> content;
    private final PageInfo page;

    public PageResponse(
            List<T> content,
            int size,
            int number,
            long totalElements,
            int totalPages
    ) {
        this.content = content;
        this.page = new PageInfo(
                size,
                number,
                totalElements,
                totalPages
        );
    }

    public record PageInfo(
            int size,
            int number,
            long totalElements,
            int totalPages
    ) {}
}