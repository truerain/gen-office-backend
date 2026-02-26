package com.third.gen_office.mis.admin.menu.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "메뉴 요청 DTO")
public record MenuRequest(
    @Schema(description = "메뉴 ID") Long menuId,
    @Schema(description = "메뉴명") String menuName,
    @Schema(description = "메뉴명(영문)") String menuNameEng,
    @Schema(description = "메뉴 설명") String menuDesc,
    @Schema(description = "메뉴 설명(영문)") String menuDescEng,
    @Schema(description = "메뉴 레벨") Integer menuLevel,
    @Schema(description = "실행 컴포넌트") String execComponent,
    @Schema(description = "메뉴 아이콘") String menuIcon,
    @Schema(description = "상위 메뉴 ID") Long parentMenuId,
    @Schema(description = "표시 여부") String displayYn,
    @Schema(description = "사용 여부") String useYn,
    @Schema(description = "정렬 순서") Integer sortOrder
) {}
