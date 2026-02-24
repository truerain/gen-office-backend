package com.third.gen_office.mis.admin.message.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Bulk message response")
public record BulkMessageResponse(
    @Schema(description = "Inserted count") int inserted,
    @Schema(description = "Updated count") int updated,
    @Schema(description = "Skipped count") int skipped
) {}
