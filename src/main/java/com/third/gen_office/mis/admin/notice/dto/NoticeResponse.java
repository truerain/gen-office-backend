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
            String dispStartDate,
            String dispEndDate,
            String popupYn,
            String useYn,
            Integer readCount,
            String lastUpdatedBy,
            String lastUpdatedDate
    ) {
        public static ListDto from(NoticeEntity entity) {
            return new ListDto(
                    entity.getNoticeId(),
                    entity.getTitle(),
                    entity.getFileSetId(),
                    entity.getDispStartDate(),
                    entity.getDispEndDate(),
                    entity.getPopupYn(),
                    entity.getUseYn(),
                    entity.getReadCount(),
                    entity.getLastUpdatedBy(),
                    entity.getLastUpdatedDate() == null ? null : entity.getLastUpdatedDate().toString()
            );
        }
    }

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
    ) {
        public static DetailDto from(NoticeEntity entity) {
            return new DetailDto(
                    entity.getNoticeId(),
                    entity.getTitle(),
                    entity.getContent(),
                    entity.getFileSetId(),
                    entity.getDispStartDate(),
                    entity.getDispEndDate(),
                    entity.getPopupYn(),
                    entity.getUseYn(),
                    entity.getReadCount(),
                    entity.getLastUpdatedBy(),
                    entity.getLastUpdatedDate() == null ? null : entity.getLastUpdatedDate().toString()
            );
        }
    }
}
