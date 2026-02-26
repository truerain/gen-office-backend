package com.third.gen_office.domain.role;

import com.third.gen_office.domain.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter
@Table(name = "tb_cm_role")
public class RoleEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "role_cd", nullable = false, unique = true)
    private String roleCd;

    @Column(name = "role_name", nullable = false)
    private String roleName;

    @Column(name = "role_name_eng", nullable = false)
    private String roleNameEng;

    @Column(name = "role_desc")
    private String roleDesc;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @Column(name = "use_yn")
    private String useYn;

}
