package com.third.gen_office.mis.admin.user;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "사용자 요청 DTO")
public record UserRequest(
    @Schema(description = "사번") String empNo,
    @Schema(description = "이름") String empName,
    @Schema(description = "영문 이름") String empNameEng,
    @Schema(description = "비밀번호") String password,
    @Schema(description = "이메일") String email,
    @Schema(description = "조직 ID") String orgId,
    @Schema(description = "직책") String title,
    @Schema(description = "언어 코드") String langCd,
    @Schema(description = "생성자") String createdBy,
    @Schema(description = "수정자") String lastUpdatedBy
) {}
