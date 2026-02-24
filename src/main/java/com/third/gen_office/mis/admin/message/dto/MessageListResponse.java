package com.third.gen_office.mis.admin.message.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Message list response")
public record MessageListResponse(
    @Schema(description = "Items") List<MessageResponse> items,
    @Schema(description = "Page number") int page,
    @Schema(description = "Page size") int size,
    @Schema(description = "Total count") long total
) {}
