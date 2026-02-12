package com.third.gen_office.mis.admin.rolemenu;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Role menu mapping request DTO")
public record RoleMenuRequest(
    @Schema(description = "Role id") Long roleId,
    @Schema(description = "Menu id") Long menuId,
    @Schema(description = "Use flag (Y/N)") String useYn
) {}
