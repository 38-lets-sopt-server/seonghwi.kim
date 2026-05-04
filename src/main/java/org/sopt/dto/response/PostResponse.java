package org.sopt.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.sopt.domain.BoardType;
import org.sopt.domain.Post;

import java.time.LocalDateTime;

@Schema(description = "게시글 응답")
public record PostResponse(

        @Schema(description = "게시글 ID", example = "1")
        Long postId,

        @Schema(description = "게시글 제목", example = "오늘 학식 뭐임")
        String title,

        @Schema(description = "게시글 내용", example = "돈까스래")
        String content,

        @Schema(description = "작성자 이름. 익명 게시글이면 익명으로 반환됩니다.", example = "익명")
        String authorName,

        @Schema(description = "익명 여부", example = "true")
        boolean isAnonymous,

        @Schema(description = "게시판 종류", example = "FREE")
        BoardType boardType,

        @Schema(description = "좋아요 수", example = "3")
        long likeCount,

        @Schema(description = "게시글 생성 시각", example = "2026-05-01T14:30:00")
        LocalDateTime createdAt,

        @Schema(description = "게시글 수정 시각", example = "2026-05-01T14:35:00")
        LocalDateTime updatedAt
) {

    public static PostResponse from(Post post, long likeCount) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                getAuthorName(post),
                post.isAnonymous(),
                post.getBoardType(),
                likeCount,
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
