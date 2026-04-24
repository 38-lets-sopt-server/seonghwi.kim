package org.sopt.dto.response;

import org.sopt.domain.BoardType;
import org.sopt.domain.Post;

public record PostResponse(
        Long postId,
        String title,
        String content,
        String authorName,
        boolean isAnonymous,
        BoardType boardType,
        String createdAt,
        String updatedAt
) {

    public static PostResponse from(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getDisplayAuthorName(),
                post.isAnonymous(),
                post.getBoardType(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }
}
