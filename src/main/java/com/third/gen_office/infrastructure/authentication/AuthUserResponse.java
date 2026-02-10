package com.third.gen_office.infrastructure.authentication;

import java.util.List;
import com.third.gen_office.infrastructure.authorization.UserPrincipal;

public record AuthUserResponse(
    Long userId,
    String empNo,
    String empName,
    String orgId,
    String orgName,
    List<String> roles
) {
    public static AuthUserResponse from(UserPrincipal principal) {
        return new AuthUserResponse(
            principal.getUserId(),
            principal.getEmpNo(),
            principal.getEmpName(),
            principal.getOrgId(),
            principal.getOrgName(),
            principal.getRoles()
        );
    }
}
