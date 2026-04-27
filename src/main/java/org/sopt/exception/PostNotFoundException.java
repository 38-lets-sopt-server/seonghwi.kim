package org.sopt.exception;

public class PostNotFoundException extends BusinessException {

    public PostNotFoundException(Long id) {
        super(ErrorCode.POST_NOT_FOUND, id + "번 게시글을 찾을 수 없습니다.");
    }
}
