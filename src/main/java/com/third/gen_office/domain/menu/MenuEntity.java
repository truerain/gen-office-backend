package com.third.gen_office.domain.menu;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Table(name = "tb_cm_menu")
public class MenuEntity {
    @Id
    @Column(name = "menu_id")
    private Long menuId;

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "menu_name_eng")
    private String menuNameEng;

    @Column(name = "menu_desc")
    private String menuDesc;

    @Column(name = "menu_desc_eng")
    private String menuDescEng;

    @Column(name = "menu_level")
    private Integer menuLevel;

    @Column(name = "exec_component")
    private String execComponent;

    @Column(name = "menu_icon")
    private String menuIcon;

    @Column(name = "parent_menu_id")
    private Long parentMenuId;

    @Column(name = "display_yn")
    private String displayYn;

    @Column(name = "use_yn")
    private String useYn;

    @Column(name = "sort_order")
    private Integer sortOrder;

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
