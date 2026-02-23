package com.third.gen_office.mis.admin.rolemenu.dto;

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
    String useYn
) {}
