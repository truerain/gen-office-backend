package com.third.gen_office.mis.admin.userrole.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Bulk user role request")
public record BulkUserRoleRequest(
    @Schema(description = "Rows to create") List<UserRoleRequest> creates,
    @Schema(description = "Rows to update") List<UserRoleRequest> updates,
    @Schema(description = "Rows to delete") List<UserRoleRequest> deletes
) {}
