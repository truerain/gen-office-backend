package com.third.gen_office.mis.admin.role;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Role request DTO")
public record RoleRequest(
    @Schema(description = "Role code") String roleCd,
    @Schema(description = "Role name") String roleName,
    @Schema(description = "Role name (English)") String roleNameEng,
    @Schema(description = "Role description") String roleDesc,
    @Schema(description = "Sort order") Integer sortOrder,
    @Schema(description = "Use flag (Y/N)") String useYn,
    @Schema(description = "Attribute1") String attribute1,
    @Schema(description = "Attribute2") String attribute2,
    @Schema(description = "Attribute3") String attribute3,
    @Schema(description = "Attribute4") String attribute4,
    @Schema(description = "Attribute5") String attribute5,
    @Schema(description = "Attribute6") String attribute6,
    @Schema(description = "Attribute7") String attribute7,
    @Schema(description = "Attribute8") String attribute8,
    @Schema(description = "Attribute9") String attribute9,
    @Schema(description = "Attribute10") String attribute10,
    @Schema(description = "Created by") String createdBy,
    @Schema(description = "Last updated by") String lastUpdatedBy
) {}
