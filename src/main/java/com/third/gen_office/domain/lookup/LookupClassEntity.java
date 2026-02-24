package com.third.gen_office.domain.lookup;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
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
@Table(name = "tb_cm_lkup_master")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class LookupClassEntity {
    @Id
    @Column(name = "lkup_clss_cd")
    private String lkupClssCd;

    @Column(name = "lkup_clss_name")
    private String lkupClssName;

    @Column(name = "lkup_clss_desc")
    private String lkupClssDesc;

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
