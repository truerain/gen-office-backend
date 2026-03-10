package com.third.gen_office.mis.admin.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Bulk user request")
public record BulkUserRequest(
    @Schema(description = "Rows to create") List<UserRequest> creates,
    @Schema(description = "Rows to update") List<UserRequest> updates,
    @Schema(description = "Rows to delete") List<UserRequest> deletes
) {}
