package org.sopt.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import org.sopt.domain.BoardType;

@Schema(description = "게시글 작성 요청")
public record CreatePostRequest(

        @Schema(description = "게시글 제목", example = "오늘 학식 뭐임")
        String title,

        @Schema(description = "게시글 내용", example = "돈까스래")
        String content,

        @Schema(description = "작성자 사용자 ID", example = "1")
        Long userId,

        @Schema(description = "익명 여부", example = "true")
        Boolean isAnonymous,

        @Schema(description = "게시판 종류", example = "FREE")
        BoardType boardType
) {
}
