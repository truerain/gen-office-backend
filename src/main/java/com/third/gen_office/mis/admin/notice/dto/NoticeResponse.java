package com.third.gen_office.mis.admin.notice.dto;

import com.third.gen_office.domain.notice.NoticeEntity;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Pattern;

/**
 * DTO for {@link NoticeEntity}
 */
public class NoticeResponse {
    // 1. 목록 조회용: 본문(content)을 제외하여 전송량 최소화
    public record ListDto(
            Integer noticeId,
            String title,
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
                    entity.getDispStartDate(),
                    entity.getDispEndDate(),
                    entity.getPopupYn(),
                    entity.getUseYn(),
                    entity.getReadCount(),
                    entity.getLastUpdatedBy(),
                    entity.getLastUpdatedDate()
            );
        }
    }

    // 2. 상세 조회용
    public record DetailDto(
            Integer noticeId,
            String title,
            String content,
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
                    entity.getDispStartDate(),
                    entity.getDispEndDate(),
                    entity.getPopupYn(),
                    entity.getUseYn(),
                    entity.getReadCount(),
                    entity.getLastUpdatedBy(),
                    entity.getLastUpdatedDate()
            );
        }
    }
}
