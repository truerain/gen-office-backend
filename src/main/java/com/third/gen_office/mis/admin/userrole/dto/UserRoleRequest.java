package com.third.gen_office.mis.admin.userrole.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "User role request DTO")
public record UserRoleRequest(
    @Schema(description = "User id") Long userId,
    @Schema(description = "Role id") Long roleId,
    @Schema(description = "Primary Y/N") String primaryYn,
    @Schema(description = "Use Y/N") String useYn
) {}
