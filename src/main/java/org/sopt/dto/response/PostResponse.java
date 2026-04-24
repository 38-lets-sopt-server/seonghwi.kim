package org.sopt.dto.response;

import org.sopt.domain.Post;

public record PostResponse(
        Long postId,
        String title,
        String content,
        String authorName,
        boolean isAnonymous,
        String createdAt
) {

    public static PostResponse from(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getDisplayAuthorName(),
                post.isAnonymous(),
                post.getCreatedAt()
        );
    }
}
