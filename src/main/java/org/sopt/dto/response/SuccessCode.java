package org.sopt.dto.response;

import org.springframework.http.HttpStatus;

public enum SuccessCode {

    POST_CREATE_SUCCESS(
            HttpStatus.CREATED,
            "POST_CREATE_SUCCESS",
            "게시글 작성에 성공했습니다."
    ),

    POST_LIST_SUCCESS(
            HttpStatus.OK,
            "POST_LIST_SUCCESS",
            "게시글 목록 조회에 성공했습니다."
    ),

    POST_DETAIL_SUCCESS(
            HttpStatus.OK,
            "POST_DETAIL_SUCCESS",
            "게시글 상세 조회에 성공했습니다."
    ),

    POST_UPDATE_SUCCESS(
            HttpStatus.OK,
            "POST_UPDATE_SUCCESS",
            "게시글 수정에 성공했습니다."
    );

    private final HttpStatus status;
    private final String code;
    private final String message;

    SuccessCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
