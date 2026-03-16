package com.third.gen_office.domain.co.actuals;

import com.third.gen_office.domain.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tb_co_actuals")
public class CoActualEntity extends BaseEntity {
    @Id
    @Column(name = "acct_cd")
    private String acctCd;

    @Column(name = "acct_name")
    private String acctName;

    @Column(name = "acct_level")
    private Integer acctLevel;

    @Column(name = "parent_cd")
    private String parentCd;

    @Column(name = "dr_cr")
    private String drCr;

    @Column(name = "fiscal_yr")
    private String fiscalYr;

    @Column(name = "fiscal_prd")
    private String fiscalPrd;

    @Column(name = "org_cd")
    private String orgCd;

    @Column(name = "curr_act_amt")
    private Double currActAmt;

    @Column(name = "plan_amt")
    private Double planAmt;

    @Column(name = "prev_act_amt")
    private Double prevActAmt;

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
