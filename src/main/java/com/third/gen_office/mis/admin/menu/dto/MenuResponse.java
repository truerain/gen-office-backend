package com.third.gen_office.mis.admin.menu.dto;

public record MenuResponse(
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
    String useYn,
    Integer sortOrder,
    String lastUpdatedBy,
    String lastUpdatedByName,
    String lastUpdatedDate
) {}
