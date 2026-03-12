package com.third.gen_office.global.api;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "API response")
public record ApiResponse<T>(
    @Schema(description = "Success flag") boolean success,
    @Schema(description = "Response code") String code,
    @Schema(description = "Message") String message,
    @Schema(description = "Response payload") T data
) {
    public static <T> ApiResponse<T> ok() {
        return new ApiResponse<>(true, "SUCCESS", null, null);
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(true, "SUCCESS", null, data);
    }

    public static <T> ApiResponse<T> failure(String code, String message) {
        return new ApiResponse<>(false, code, message, null);
    }
}


