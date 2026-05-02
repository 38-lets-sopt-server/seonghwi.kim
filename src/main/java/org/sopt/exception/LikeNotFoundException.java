package org.sopt.exception;

public class LikeNotFoundException extends BusinessException {

    public LikeNotFoundException(Long userId, Long postId) {
        super(
                ErrorCode.LIKE_NOT_FOUND,
                userId + "번 사용자는 " + postId + "번 게시글에 좋아요를 누르지 않았습니다."
        );
    }
}
