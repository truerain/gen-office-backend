package com.third.gen_office.mis.admin.userrole.dto;

import java.time.LocalDateTime;

public interface UserRoleDetailRow {
    Long getUserId();
    Long getRoleId();
    String getPrimaryYn();
    String getUseYn();
    String getEmpNo();
    String getEmpName();
    String getOrgName();
    String getRoleName();
    String getLastUpdatedBy();
    String getLastUpdatedByName();
    LocalDateTime getLastUpdatedDate();
}
