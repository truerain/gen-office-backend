package com.third.gen_office.mis.admin.role.dto;

public record RoleResponse(
    Long roleId,
    String roleCd,
    String roleName,
    String roleNameEng,
    String roleDesc,
    Integer sortOrder,
    String useYn,
    String lastUpdatedBy,
    String lastUpdatedByName,
    String lastUpdatedAt
) {}
