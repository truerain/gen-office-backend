package com.third.gen_office.mis.admin.user;

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
    @Schema(description = "Language code") String langCd,
    @Schema(description = "Attribute1") String attribute1,
    @Schema(description = "Attribute2") String attribute2,
    @Schema(description = "Attribute3") String attribute3,
    @Schema(description = "Attribute4") String attribute4,
    @Schema(description = "Attribute5") String attribute5,
    @Schema(description = "Attribute6") String attribute6,
    @Schema(description = "Attribute7") String attribute7,
    @Schema(description = "Attribute8") String attribute8,
    @Schema(description = "Attribute9") String attribute9,
    @Schema(description = "Attribute10") String attribute10,
    @Schema(description = "Created by") String createdBy,
    @Schema(description = "Last updated by") String lastUpdatedBy
) {}
