package com.third.gen_office.mis.admin.message.dto;

public record MessageRequest(
    String namespace,
    String messageCd,
    String langCd,
    String messageTxt
) {}
