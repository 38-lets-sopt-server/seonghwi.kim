package org.sopt.dto.request;

public record CreatePostRequest(  // 게시글 작성 요청 DTO
        String title,
        String content,
        String author
) {
}
