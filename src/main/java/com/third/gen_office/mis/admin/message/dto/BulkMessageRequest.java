package com.third.gen_office.mis.admin.message.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Bulk message request")
public record BulkMessageRequest(
    @Schema(description = "Items") List<MessageCreateRequest> items
) {}
