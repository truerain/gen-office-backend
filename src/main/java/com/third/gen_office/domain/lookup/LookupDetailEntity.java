package com.third.gen_office.domain.lookup;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tb_cm_lkup_detail")
@IdClass(LookupDetailId.class)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class LookupDetailEntity {
    @Id
    @Column(name = "lkup_clss_cd")
    private String lkupClssCd;

    @Id
    @Column(name = "lkup_cd")
    private String lkupCd;

    @Column(name = "lkup_name")
    private String lkupName;

    @Column(name = "lkup_name_eng")
    private String lkupNameEng;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @Column(name = "use_yn")
    private String useYn;

    private String attribute1;
    private String attribute2;
    private String attribute3;
    private String attribute4;
    private String attribute5;
    private String attribute6;
    private String attribute7;
    private String attribute8;
    private String attribute9;
    private String attribute10;
    private String attribute11;
    private String attribute12;
    private String attribute13;
    private String attribute14;
    private String attribute15;
    private String attribute16;
    private String attribute17;
    private String attribute18;
    private String attribute19;
    private String attribute20;

    @Column(name = "creation_date", insertable = false, updatable = false)
    private String creationDate;

    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "last_updated_date", insertable = false, updatable = false)
    private String lastUpdatedDate;

    @LastModifiedBy
    @Column(name = "last_updated_by")
    private String lastUpdatedBy;
}
