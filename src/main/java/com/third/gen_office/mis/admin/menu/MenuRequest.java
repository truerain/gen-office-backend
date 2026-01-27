package com.third.gen_office.mis.admin.menu;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "메뉴 요청 DTO")
public record MenuRequest(
    @Schema(description = "메뉴 ID") Long menuId,
    @Schema(description = "메뉴명") String menuName,
    @Schema(description = "메뉴명(영문)") String menuNameEng,
    @Schema(description = "메뉴 설명") String menuDesc,
    @Schema(description = "메뉴 레벨") Integer menuLevel,
    @Schema(description = "상위 메뉴 ID") Long prntMenuId,
    @Schema(description = "표시 여부") String dsplFlag,
    @Schema(description = "사용 여부") String useFlag,
    @Schema(description = "정렬 순서") Integer sortOrder,
    @Schema(description = "URL") String url,
    @Schema(description = "파라미터1") String param1,
    @Schema(description = "파라미터2") String param2,
    @Schema(description = "파라미터3") String param3,
    @Schema(description = "파라미터4") String param4,
    @Schema(description = "파라미터5") String param5,
    @Schema(description = "A/B 권한") String abAuthFlag,
    @Schema(description = "C 권한") String cAuthFlag,
    @Schema(description = "E 권한") String eAuthFlag,
    @Schema(description = "F 권한") String fAuthFlag,
    @Schema(description = "속성1") String attribute1,
    @Schema(description = "속성2") String attribute2,
    @Schema(description = "속성3") String attribute3,
    @Schema(description = "속성4") String attribute4,
    @Schema(description = "속성5") String attribute5,
    @Schema(description = "속성6") String attribute6,
    @Schema(description = "속성7") String attribute7,
    @Schema(description = "속성8") String attribute8,
    @Schema(description = "속성9") String attribute9,
    @Schema(description = "속성10") String attribute10,
    @Schema(description = "생성자") String createdBy,
    @Schema(description = "수정자") String lastUpdatedBy
) {}
