package com.third.gen_office.mis.admin.message.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Create message request")
public record MessageCreateRequest(
    @Schema(description = "Namespace") String namespace,
    @Schema(description = "Message code") String messageCd,
    @Schema(description = "Language code") String langCd,
    @Schema(description = "Message text") String messageTxt
) {}
