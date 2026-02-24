package com.third.gen_office.mis.admin.message.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Missing message item")
public record MissingMessageItem(
    @Schema(description = "Namespace") String namespace,
    @Schema(description = "Message code") String messageCd,
    @Schema(description = "Base language") String baseLang,
    @Schema(description = "Target language") String targetLang
) {}
