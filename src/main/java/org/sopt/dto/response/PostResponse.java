package org.sopt.dto.response;

import org.sopt.domain.BoardType;
import org.sopt.domain.Post;

import java.time.LocalDateTime;

public record PostResponse(
        Long postId,
        String title,
        String content,
        String authorName,
        boolean isAnonymous,
        BoardType boardType,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static PostResponse from(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                getAuthorName(post),
                post.isAnonymous(),
                post.getBoardType(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }

    private static String getAuthorName(Post post) {
        if (post.isAnonymous()) {
            return "익명";
        }

        return post.getUser().getNickname();
    }
}
