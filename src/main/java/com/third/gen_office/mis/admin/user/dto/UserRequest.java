package com.third.gen_office.mis.admin.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "User request DTO")
public record UserRequest(
    @Schema(description = "Employee number") String empNo,
    @Schema(description = "Employee name") String empName,
    @Schema(description = "Employee name (English)") String empNameEng,
    @Schema(description = "Password") String password,
    @Schema(description = "Email") String email,
    @Schema(description = "Organization ID") String orgId,
    @Schema(description = "Organization name") String orgName,
    @Schema(description = "Title code") String titleCd,
    @Schema(description = "Title name") String titleName,
    @Schema(description = "Work telephone") String workTel,
    @Schema(description = "Mobile telephone") String mobileTel,
    @Schema(description = "Language code") String langCd
) {}
