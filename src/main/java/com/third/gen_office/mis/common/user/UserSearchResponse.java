package com.third.gen_office.mis.common.user;

public record UserSearchResponse(
    Long userId,
    String empNo,
    String empName,
    String titleCd,
    String titleName,
    String orgId,
    String orgName
) {}
