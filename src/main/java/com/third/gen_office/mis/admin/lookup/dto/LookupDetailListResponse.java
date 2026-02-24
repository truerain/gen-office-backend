package com.third.gen_office.mis.admin.lookup.dto;

import java.util.List;

public record LookupDetailListResponse(
    List<LookupDetailResponse> items,
    int page,
    int size,
    long total
) {}
