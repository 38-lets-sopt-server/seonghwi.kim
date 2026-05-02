package org.sopt.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "좋아요 요청")
public record LikeRequest(

        @Schema(description = "좋아요를 누르거나 취소할 사용자 ID", example = "1")
        @NotNull(message = "INVALID_USER_ID")
        Long userId
) {
}
