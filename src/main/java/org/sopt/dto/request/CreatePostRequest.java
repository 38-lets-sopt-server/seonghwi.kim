package org.sopt.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.sopt.domain.BoardType;

@Schema(description = "게시글 작성 요청")
public record CreatePostRequest(

        @Schema(description = "게시글 제목", example = "오늘 학식 뭐임")
        @NotBlank(message = "INVALID_POST_TITLE")
        @Size(max = 50, message = "INVALID_POST_TITLE_LENGTH")
        String title,

        @Schema(description = "게시글 내용", example = "돈까스래")
        @NotBlank(message = "INVALID_POST_CONTENT")
        @Size(max = 500, message = "INVALID_POST_CONTENT_LENGTH")
        String content,

        @Schema(description = "작성자 사용자 ID", example = "1")
        @NotNull(message = "INVALID_USER_ID")
        Long userId,

        @Schema(description = "익명 여부", example = "true")
        @NotNull(message = "INVALID_POST_ANONYMOUS")
        Boolean isAnonymous,

        @Schema(description = "게시판 종류", example = "FREE")
        @NotNull(message = "INVALID_POST_BOARD_TYPE")
        BoardType boardType
) {
}
