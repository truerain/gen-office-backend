package com.third.gen_office.mis.admin.message.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Message response")
public record MessageResponse(
    @Schema(description = "Namespace") String namespace,
    @Schema(description = "Message code") String messageCd,
    @Schema(description = "Language code") String langCd,
    @Schema(description = "Message text") String messageTxt,
    @Schema(description = "Created at") String createdAt,
    @Schema(description = "Updated at") String updatedAt
) {}
