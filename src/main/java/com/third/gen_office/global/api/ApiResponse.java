package com.third.gen_office.global.api;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "API response")
public record ApiResponse(
    @Schema(description = "Success flag") boolean success,
    @Schema(description = "Response code") String code,
    @Schema(description = "Message") String message
) {
    public static ApiResponse ok() {
        return new ApiResponse(true, "SUCCESS", null);
    }

    public static ApiResponse failure(String code, String message) {
        return new ApiResponse(false, code, message);
    }
}


