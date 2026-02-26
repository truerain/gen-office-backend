package com.third.gen_office.mis.admin.lookup.dto;

public record LookupDetailResponse(
    String lkupClssCd,
    String lkupCd,
    String lkupName,
    String lkupNameEng,
    Integer sortOrder,
    String useYn,
    String attribute1,
    String attribute2,
    String attribute3,
    String attribute4,
    String attribute5,
    String attribute6,
    String attribute7,
    String attribute8,
    String attribute9,
    String attribute10,
    String lastUpdatedBy,
    String lastUpdatedByName,
    String lastUpdatedAt
) {}
