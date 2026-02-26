package com.third.gen_office.domain.lookup;

import com.third.gen_office.domain.base.BaseEntity;
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
import lombok.experimental.SuperBuilder;
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
@SuperBuilder
public class LookupDetailEntity extends BaseEntity {
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
}
