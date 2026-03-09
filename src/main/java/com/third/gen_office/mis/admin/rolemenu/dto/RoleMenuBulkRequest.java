package com.third.gen_office.mis.admin.rolemenu.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Role menu bulk commit request")
public record RoleMenuBulkRequest(
    @Schema(description = "Rows to create") List<RoleMenuRequest> creates,
    @Schema(description = "Rows to update") List<RoleMenuRequest> updates,
    @Schema(description = "Role menu ids to delete") List<RoleMenuIdRequest> deletes
) {}
