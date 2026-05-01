package org.sopt.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    POST_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            "POST_NOT_FOUND",
            "존재하지 않는 게시글입니다."
    ),

    USER_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            "USER_NOT_FOUND",
            "존재하지 않는 사용자입니다."
    ),

    INVALID_USER_ID(
            HttpStatus.BAD_REQUEST,
            "INVALID_USER_ID",
            "사용자 ID는 필수입니다."
    ),

    INVALID_POST_TITLE(
            HttpStatus.BAD_REQUEST,
            "INVALID_POST_TITLE",
            "제목은 필수입니다."
    ),

    INVALID_POST_TITLE_LENGTH(
            HttpStatus.BAD_REQUEST,
            "INVALID_POST_TITLE_LENGTH",
            "제목은 50자 이하여야 합니다."
    ),

    INVALID_POST_CONTENT(
            HttpStatus.BAD_REQUEST,
            "INVALID_POST_CONTENT",
            "내용은 필수입니다."
    ),

    INVALID_POST_CONTENT_LENGTH(
            HttpStatus.BAD_REQUEST,
            "INVALID_POST_CONTENT_LENGTH",
            "내용은 500자 이하여야 합니다."
    ),

    INVALID_POST_ANONYMOUS(
            HttpStatus.BAD_REQUEST,
            "INVALID_POST_ANONYMOUS",
            "익명 여부는 필수입니다."
    ),

    INVALID_POST_BOARD_TYPE(
            HttpStatus.BAD_REQUEST,
            "INVALID_POST_BOARD_TYPE",
            "게시판 종류는 필수입니다."
    ),

    INVALID_PAGE_REQUEST(
            HttpStatus.BAD_REQUEST,
            "INVALID_PAGE_REQUEST",
            "page는 0 이상이어야 합니다."
    ),

    INVALID_SIZE_REQUEST(
            HttpStatus.BAD_REQUEST,
            "INVALID_SIZE_REQUEST",
            "size는 1 이상이어야 합니다."
    ),

    INVALID_REQUEST_PARAMETER(
            HttpStatus.BAD_REQUEST,
            "INVALID_REQUEST_PARAMETER",
            "요청 파라미터 형식이 올바르지 않습니다."
    ),

    INVALID_REQUEST_BODY(
            HttpStatus.BAD_REQUEST,
            "INVALID_REQUEST_BODY",
            "요청 본문 형식이 올바르지 않습니다."
    ),

    POST_FORBIDDEN(
            HttpStatus.FORBIDDEN,
            "POST_FORBIDDEN",
            "게시글에 대한 권한이 없습니다."
    ),

    INTERNAL_SERVER_ERROR(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "INTERNAL_SERVER_ERROR",
            "서버 내부 오류가 발생했습니다."
    );

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
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
