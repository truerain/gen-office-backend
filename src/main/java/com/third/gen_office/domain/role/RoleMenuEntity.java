package com.third.gen_office.domain.role;

import com.third.gen_office.domain.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tb_cm_role_menu")
@Getter @Setter
@IdClass(RoleMenuId.class)
public class RoleMenuEntity extends BaseEntity {
    @Id
    @Column(name = "role_id")
    private Long roleId;

    @Id
    @Column(name = "menu_id")
    private Long menuId;

    @Column(name = "use_yn")
    private String useYn;
}
