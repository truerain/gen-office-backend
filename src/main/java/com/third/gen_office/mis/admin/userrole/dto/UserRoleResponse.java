package com.third.gen_office.mis.admin.userrole.dto;

public record UserRoleResponse(
    Long userId,
    Long roleId,
    String primaryYn,
    String useYn,
    String empNo,
    String empName,
    String orgName,
    String roleName,
    String lastUpdatedBy,
    String lastUpdatedByName,
    String lastUpdatedDate
) {}
