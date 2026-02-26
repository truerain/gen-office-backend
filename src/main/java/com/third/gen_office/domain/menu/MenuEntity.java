package com.third.gen_office.domain.menu;

import com.third.gen_office.domain.base.BaseEntity;
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
public class MenuEntity extends BaseEntity {
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

}
