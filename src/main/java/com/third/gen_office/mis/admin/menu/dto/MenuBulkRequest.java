package com.third.gen_office.mis.admin.menu.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Menu bulk commit request")
public record MenuBulkRequest(
    @Schema(description = "Rows to create") List<MenuRequest> creates,
    @Schema(description = "Rows to update") List<MenuRequest> updates,
    @Schema(description = "Menu ids to delete") List<Long> deletes
) {}
