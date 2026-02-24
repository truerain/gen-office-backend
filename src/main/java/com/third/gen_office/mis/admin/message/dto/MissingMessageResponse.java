package com.third.gen_office.mis.admin.message.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Missing message response")
public record MissingMessageResponse(
    @Schema(description = "Items") List<MissingMessageItem> items
) {}
