package com.third.gen_office.mis.admin.lookup.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Bulk lookup detail request")
public record BulkLookupDetailRequest(
    @Schema(description = "Rows to create") List<LookupDetailRequest> creates,
    @Schema(description = "Rows to update") List<LookupDetailRequest> updates,
    @Schema(description = "Rows to delete") List<LookupDetailRequest> deletes
) {}
