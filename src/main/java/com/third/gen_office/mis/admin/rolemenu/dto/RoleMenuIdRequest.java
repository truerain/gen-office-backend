package com.third.gen_office.mis.admin.rolemenu.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Role menu id request")
public record RoleMenuIdRequest(
    @Schema(description = "Role id") Long roleId,
    @Schema(description = "Menu id") Long menuId
) {}
