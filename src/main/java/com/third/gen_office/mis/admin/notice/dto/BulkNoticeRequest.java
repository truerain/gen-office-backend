package com.third.gen_office.mis.admin.notice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Bulk notice request")
public record BulkNoticeRequest(
    @Schema(description = "Rows to create") List<NoticeRequest> creates,
    @Schema(description = "Rows to update") List<NoticeRequest> updates,
    @Schema(description = "Rows to delete") List<NoticeRequest> deletes
) {}
