package com.third.gen_office.mis.admin.user.dto;

public record UserResponse(
    Long userId,
    String empNo,
    String empName,
    String empNameEng,
    String email,
    String orgId,
    String orgName,
    String titleCd,
    String titleName,
    String workTel,
    String mobileTel,
    String langCd,
    String lastUpdatedBy,
    String lastUpdatedByName,
    String lastUpdatedDate
) {}
