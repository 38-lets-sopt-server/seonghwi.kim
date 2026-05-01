package org.sopt.exception;

public class LikeAlreadyExistsException extends BusinessException {

    public LikeAlreadyExistsException(Long userId, Long postId) {
        super(
                ErrorCode.LIKE_ALREADY_EXISTS,
                userId + "번 사용자는 " + postId + "번 게시글에 이미 좋아요를 눌렀습니다."
        );
    }
}
