package com.third.gen_office.mis.common.code.dto;

public record CodeDetailResponse(
    String lkupClssCd,
    String code,
    String name,
    Integer sortOrder,
    String useYn
) {}
