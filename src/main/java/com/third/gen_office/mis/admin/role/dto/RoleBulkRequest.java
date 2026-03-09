package com.third.gen_office.mis.admin.role.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Role bulk commit request")
public record RoleBulkRequest(
    @Schema(description = "Rows to create") List<RoleRequest> creates,
    @Schema(description = "Rows to update") List<RoleRequest> updates,
    @Schema(description = "Role ids to delete") List<Long> deletes
) {}
