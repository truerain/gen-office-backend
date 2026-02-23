package com.third.gen_office.domain.notice;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tb_cm_notice")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder

public class NoticeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Integer noticeId;

    @Column(name = "subject")
    private String title; // 스키마의 subject와 매핑

    @Column(name = "content", columnDefinition = "TEXT")
    private String content; // Tiptap HTML 데이터 저장용

    @Column(name = "file_set_id")
    private Integer fileSetId;

    @Column(name = "disp_start_date")
    private String dispStartDate;

    @Column(name = "disp_end_date")
    private String dispEndDate;

    @Column(name = "popup_yn", length = 1)
    @Pattern(regexp = "[YN]", message = "값은 'Y' 또는 'N'이어야 합니다.")
    private String popupYn;

    @Column(name = "use_yn", length = 1)
    @Pattern(regexp = "[YN]", message = "값은 'Y' 또는 'N'이어야 합니다.")
    private String useYn;

    @Column(name = "read_count")
    @Builder.Default
    private Integer readCount = 0;

    // 공통 확장 속성 필드 (attribute1 ~ 10)
    @Column(name = "attribute1")
    private String attribute1;
    @Column(name = "attribute2")
    private String attribute2;
    @Column(name = "attribute3")
    private String attribute3;
    @Column(name = "attribute4")
    private String attribute4;
    @Column(name = "attribute5")
    private String attribute5;
    @Column(name = "attribute6")
    private String attribute6;
    @Column(name = "attribute7")
    private String attribute7;
    @Column(name = "attribute8")
    private String attribute8;
    @Column(name = "attribute9")
    private String attribute9;
    @Column(name = "attribute10")
    private String attribute10;

    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "creation_date", updatable = false)
    private String creationDate;

    @LastModifiedBy
    @Column(name = "last_updated_by")
    private String lastUpdatedBy;

    @Column(name = "last_updated_date", updatable = false)
    private String lastUpdatedDate;
}
