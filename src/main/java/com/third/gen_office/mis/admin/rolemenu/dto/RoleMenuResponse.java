package com.third.gen_office.mis.admin.rolemenu.dto;

public record RoleMenuResponse(
    Long roleId,
    Long menuId,
    String useYn,
    String lastUpdatedBy,
    String lastUpdatedByName,
    String lastUpdatedAt
) {}
