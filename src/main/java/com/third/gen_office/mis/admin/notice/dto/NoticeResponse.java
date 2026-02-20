package com.third.gen_office.mis.admin.notice.dto;

import com.third.gen_office.mis.admin.notice.Notice;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.third.gen_office.mis.admin.notice.Notice}
 */
public class NoticeResponse {
    // 1. 목록 조회용: 본문(content)을 제외하여 전송량 최소화
    public record ListDto(
            Integer noticeId,
            String title,
            Integer readCount,
            String lastUpdatedBy,
            String lastUpdatedDate
    ) {
        public static ListDto from(Notice entity) {
            return new ListDto(
                    entity.getNoticeId(),
                    entity.getTitle(),
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
            String popupYn,
            Integer readCount,
            String lastUpdatedBy,
            String lastUpdatedDate
    ) {
        public static DetailDto from(Notice entity) {
            return new DetailDto(
                    entity.getNoticeId(),
                    entity.getTitle(),
                    entity.getContent(),
                    entity.getPopupYn(),
                    entity.getReadCount(),
                    entity.getLastUpdatedBy(),
                    entity.getLastUpdatedDate()
            );
        }
    }
}