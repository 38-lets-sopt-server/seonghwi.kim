package org.sopt.dto.request;

import org.sopt.domain.BoardType;

public record CreatePostRequest(
        String title,
        String content,
        Long userId,
        Boolean isAnonymous,
        BoardType boardType
) {
}
