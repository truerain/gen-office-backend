package com.third.gen_office.mis.admin.role.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Role request DTO")
public record RoleRequest(
    @Schema(description = "Role code") String roleCd,
    @Schema(description = "Role name") String roleName,
    @Schema(description = "Role name (English)") String roleNameEng,
    @Schema(description = "Role description") String roleDesc,
    @Schema(description = "Sort order") Integer sortOrder,
    @Schema(description = "Use flag (Y/N)") String useYn
) {}
