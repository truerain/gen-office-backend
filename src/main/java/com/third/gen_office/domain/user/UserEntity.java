package com.third.gen_office.domain.user;

import com.third.gen_office.domain.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.EntityListeners;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter
@Table(name = "tb_cm_user")
public class UserEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "emp_no", nullable = false, unique = true)
    private String empNo;

    @Column(name = "emp_name", nullable = false)
    private String empName;

    @Column(name = "emp_name_eng", nullable = false)
    private String empNameEng;

    @Column(name = "password", nullable = false)
    private String password;

    private String email;

    @Column(name = "org_id", nullable = false)
    private String orgId;

    @Column(name = "org_name")
    private String orgName;

    @Column(name = "title_cd")
    private String titleCd;

    @Column(name = "title_name")
    private String titleName;

    @Column(name = "work_tel")
    private String workTel;

    @Column(name = "mobile_tel")
    private String mobileTel;

    @Column(name = "lang_cd", nullable = false)
    private String langCd;

}
