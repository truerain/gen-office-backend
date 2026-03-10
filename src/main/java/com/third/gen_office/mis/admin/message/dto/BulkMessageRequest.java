package com.third.gen_office.mis.admin.message.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Bulk message request")
public record BulkMessageRequest(
    @Schema(description = "Rows to create") List<MessageRequest> creates,
    @Schema(description = "Rows to update") List<MessageRequest> updates,
    @Schema(description = "Rows to delete") List<MessageRequest> deletes
) {}
