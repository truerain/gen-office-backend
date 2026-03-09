package com.third.gen_office.mis.admin.rolemenu.dto;

import java.time.LocalDateTime;

public record RoleMenuView(
    Long menuId,
    String menuName,
    String menuNameEng,
    String menuDesc,
    String menuDescEng,
    Integer menuLevel,
    String execComponent,
    String menuIcon,
    Long parentMenuId,
    String displayYn,
    Integer sortOrder,
    String useYn,
    String lastUpdatedBy,
    String lastUpdatedByName,
    LocalDateTime lastUpdatedDate
) {}
