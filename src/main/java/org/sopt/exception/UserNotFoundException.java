package org.sopt.exception;

public class UserNotFoundException extends BusinessException {

    public UserNotFoundException(Long id) {
        super(ErrorCode.USER_NOT_FOUND, id + "번 사용자를 찾을 수 없습니다.");
    }
}
