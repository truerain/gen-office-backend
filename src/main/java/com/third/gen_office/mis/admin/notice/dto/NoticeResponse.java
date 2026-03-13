package com.third.gen_office.mis.admin.notice.dto;

import com.third.gen_office.domain.notice.NoticeEntity;

/**
 * DTO for {@link NoticeEntity}
 */
public class NoticeResponse {
    // 1. List view excludes content
    public record ListDto(
            Integer noticeId,
            String title,
            Integer fileSetId,
            String filenames,
            String dispStartDate,
            String dispEndDate,
            String popupYn,
            String useYn,
            Integer readCount,
            String lastUpdatedBy,
            String lastUpdatedDate
    ) {}

    // 2. Detail view
    public record DetailDto(
            Integer noticeId,
            String title,
            String content,
            Integer fileSetId,
            String dispStartDate,
            String dispEndDate,
            String popupYn,
            String useYn,
            Integer readCount,
            String lastUpdatedBy,
            String lastUpdatedDate
    ) {}
}

