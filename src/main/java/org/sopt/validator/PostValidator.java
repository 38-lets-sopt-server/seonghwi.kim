package org.sopt.validator;

import org.sopt.domain.BoardType;
import org.sopt.exception.BusinessException;
import org.sopt.exception.ErrorCode;
import org.springframework.stereotype.Component;

@Component
public class PostValidator {

    private static final int MAX_TITLE_LENGTH = 50;
    private static final int MAX_CONTENT_LENGTH = 500;

    public void validateCreatePost(
            String title,
            String content,
            Long userId,
            Boolean isAnonymous,
            BoardType boardType
    ) {
        validateTitle(title);
        validateContent(content);
        validateUserId(userId);
        validateIsAnonymous(isAnonymous);
        validateBoardType(boardType);
    }

    public void validateUpdatePost(
            String title,
            String content,
            Boolean isAnonymous
    ) {
        validateTitle(title);
        validateContent(content);
        validateIsAnonymous(isAnonymous);
    }

    private void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new BusinessException(ErrorCode.INVALID_POST_TITLE);
        }

        if (title.length() > MAX_TITLE_LENGTH) {
            throw new BusinessException(ErrorCode.INVALID_POST_TITLE_LENGTH);
        }
    }

    private void validateContent(String content) {
        if (content == null || content.isBlank()) {
            throw new BusinessException(ErrorCode.INVALID_POST_CONTENT);
        }

        if (content.length() > MAX_CONTENT_LENGTH) {
            throw new BusinessException(ErrorCode.INVALID_POST_CONTENT_LENGTH);
        }
    }

    private void validateUserId(Long userId) {
        if (userId == null) {
            throw new BusinessException(ErrorCode.INVALID_USER_ID);
        }
    }

    private void validateIsAnonymous(Boolean isAnonymous) {
        if (isAnonymous == null) {
            throw new BusinessException(ErrorCode.INVALID_POST_ANONYMOUS);
        }
    }

    private void validateBoardType(BoardType boardType) {
        if (boardType == null) {
            throw new BusinessException(ErrorCode.INVALID_POST_BOARD_TYPE);
        }
    }
}
