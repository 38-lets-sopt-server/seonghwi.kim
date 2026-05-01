package org.sopt.dto.response;

public record BaseResponse<T>(
        boolean success,
        String code,
        String message,
        T data
) {

    public static <T> BaseResponse<T> success(SuccessCode successCode, T data) {
        return new BaseResponse<>(
                true,
                successCode.getCode(),
                successCode.getMessage(),
                data
        );
    }

    public static <T> BaseResponse<T> fail(String code, String message) {
        return new BaseResponse<>(
                false,
                code,
                message,
                null
        );
    }
}
