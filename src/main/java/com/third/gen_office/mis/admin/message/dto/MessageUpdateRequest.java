package com.third.gen_office.mis.admin.message.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Update message request")
public record MessageUpdateRequest(
    @Schema(description = "Message text") String messageTxt
) {}
