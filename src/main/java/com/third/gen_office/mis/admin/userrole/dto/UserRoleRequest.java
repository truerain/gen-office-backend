package com.third.gen_office.mis.admin.userrole.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "User role request DTO")
public record UserRoleRequest(
    @Schema(description = "User id") Long userId,
    @Schema(description = "Role id") Long roleId,
    @Schema(description = "Use Y/N") String useYn,
    String attribute1,
    String attribute2,
    String attribute3,
    String attribute4,
    String attribute5,
    String attribute6,
    String attribute7,
    String attribute8,
    String attribute9,
    String attribute10
) {}
