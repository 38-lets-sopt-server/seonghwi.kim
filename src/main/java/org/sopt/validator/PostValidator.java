package org.sopt.validator;

import org.sopt.exception.BusinessException;
import org.sopt.exception.ErrorCode;
import org.springframework.stereotype.Component;

@Component
public class PostValidator {

    private static final int MAX_TITLE_LENGTH = 50;

    public void validatePost(String title, String content) {
        validateTitle(title);
        validateContent(content);
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
    }
}
