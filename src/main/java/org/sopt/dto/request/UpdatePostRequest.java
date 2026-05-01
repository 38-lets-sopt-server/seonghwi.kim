package org.sopt.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "게시글 수정 요청")
public record UpdatePostRequest(

        @Schema(description = "수정할 게시글 제목", example = "수정된 제목입니다")
        @NotBlank(message = "INVALID_POST_TITLE")
        @Size(max = 50, message = "INVALID_POST_TITLE_LENGTH")
        String title,

        @Schema(description = "수정할 게시글 내용", example = "수정된 내용입니다")
        @NotBlank(message = "INVALID_POST_CONTENT")
        @Size(max = 500, message = "INVALID_POST_CONTENT_LENGTH")
        String content,

        @Schema(description = "익명 여부", example = "false")
        @NotNull(message = "INVALID_POST_ANONYMOUS")
        Boolean isAnonymous
) {
}
