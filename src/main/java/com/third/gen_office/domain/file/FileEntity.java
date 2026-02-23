package com.third.gen_office.domain.file;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Table(name = "tb_cm_file")
public class FileEntity {

    protected FileEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Integer fileId;

    // 여러 파일이 하나의 세트로 묶이는 ID (공지사항의 file_set_id와 매핑)
    @Column(name = "file_set_id", nullable = false)
    private Integer fileSetId;

    @Column(name = "original_file_name", nullable = false)
    private String originalFileName; // 사용자가 올린 원래 파일명

    @Column(name = "stored_file_name", nullable = false)
    private String storedFileName;   // 서버(저장소)에 실제 저장된 파일명

    @Column(name = "file_path", nullable = false)
    private String filePath;         // 저장 경로

    @Column(name = "file_extension")
    private String fileExtension;    // 확장자

    @Column(name = "file_size")
    private Long fileSize;           // 파일 크기 (byte)

    @Column(name = "use_yn", length = 1)
    @Pattern(regexp = "[YN]", message = "값은 'Y' 또는 'N'이어야 합니다.")
    private String useYn;

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
    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @CreationTimestamp
    @Column(name = "creation_date", updatable = false)
    private LocalDateTime creationDate;

    @LastModifiedBy
    @Column(name = "last_updated_by")
    private String lastUpdatedBy;

    @UpdateTimestamp
    @Column(name = "last_updated_date")
    private LocalDateTime lastUpdatedDate;

    // 파일 생성을 위한 빌더 패턴이나 생성자
    public FileEntity(Integer fileSetId, String originalFileName, String storedFileName,
                      String filePath, String fileExtension, Long fileSize, String createdBy) {
        this.fileSetId = fileSetId;
        this.originalFileName = originalFileName;
        this.storedFileName = storedFileName;
        this.filePath = filePath;
        this.fileExtension = fileExtension;
        this.fileSize = fileSize;
        this.createdBy = createdBy;
        this.creationDate = LocalDateTime.now();
    }
}
