package com.third.gen_office.mis.admin.lookup.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Bulk lookup master request")
public record BulkLookupMasterRequest(
    @Schema(description = "Rows to create") List<LookupMasterRequest> creates,
    @Schema(description = "Rows to update") List<LookupMasterRequest> updates,
    @Schema(description = "Rows to delete") List<LookupMasterRequest> deletes
) {}
