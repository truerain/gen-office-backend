package com.third.gen_office.domain.menu;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "menu")
public class Menu {
    @Id
    @Column(name = "menu_id")
    private Long menuId;

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "menu_name_eng")
    private String menuNameEng;

    @Column(name = "menu_desc")
    private String menuDesc;

    @Column(name = "menu_level")
    private Integer menuLevel;

    @Column(name = "prnt_menu_id")
    private Long prntMenuId;

    @Column(name = "dspl_flag")
    private String dsplFlag;

    @Column(name = "use_flag")
    private String useFlag;

    @Column(name = "sort_order")
    private Integer sortOrder;

    private String url;

    private String param1;
    private String param2;
    private String param3;
    private String param4;
    private String param5;

    @Column(name = "ab_auth_flag")
    private String abAuthFlag;

    @Column(name = "c_auth_flag")
    private String cAuthFlag;

    @Column(name = "e_auth_flag")
    private String eAuthFlag;

    @Column(name = "f_auth_flag")
    private String fAuthFlag;

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

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "last_update_date", insertable = false, updatable = false)
    private String lastUpdateDate;

    @Column(name = "last_updated_by")
    private String lastUpdatedBy;

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuNameEng() {
        return menuNameEng;
    }

    public void setMenuNameEng(String menuNameEng) {
        this.menuNameEng = menuNameEng;
    }

    public String getMenuDesc() {
        return menuDesc;
    }

    public void setMenuDesc(String menuDesc) {
        this.menuDesc = menuDesc;
    }

    public Integer getMenuLevel() {
        return menuLevel;
    }

    public void setMenuLevel(Integer menuLevel) {
        this.menuLevel = menuLevel;
    }

    public Long getPrntMenuId() {
        return prntMenuId;
    }

    public void setPrntMenuId(Long prntMenuId) {
        this.prntMenuId = prntMenuId;
    }

    public String getDsplFlag() {
        return dsplFlag;
    }

    public void setDsplFlag(String dsplFlag) {
        this.dsplFlag = dsplFlag;
    }

    public String getUseFlag() {
        return useFlag;
    }

    public void setUseFlag(String useFlag) {
        this.useFlag = useFlag;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }

    public String getParam3() {
        return param3;
    }

    public void setParam3(String param3) {
        this.param3 = param3;
    }

    public String getParam4() {
        return param4;
    }

    public void setParam4(String param4) {
        this.param4 = param4;
    }

    public String getParam5() {
        return param5;
    }

    public void setParam5(String param5) {
        this.param5 = param5;
    }

    public String getAbAuthFlag() {
        return abAuthFlag;
    }

    public void setAbAuthFlag(String abAuthFlag) {
        this.abAuthFlag = abAuthFlag;
    }

    public String getCAuthFlag() {
        return cAuthFlag;
    }

    public void setCAuthFlag(String cAuthFlag) {
        this.cAuthFlag = cAuthFlag;
    }

    public String getEAuthFlag() {
        return eAuthFlag;
    }

    public void setEAuthFlag(String eAuthFlag) {
        this.eAuthFlag = eAuthFlag;
    }

    public String getFAuthFlag() {
        return fAuthFlag;
    }

    public void setFAuthFlag(String fAuthFlag) {
        this.fAuthFlag = fAuthFlag;
    }

    public String getAttribute1() {
        return attribute1;
    }

    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1;
    }

    public String getAttribute2() {
        return attribute2;
    }

    public void setAttribute2(String attribute2) {
        this.attribute2 = attribute2;
    }

    public String getAttribute3() {
        return attribute3;
    }

    public void setAttribute3(String attribute3) {
        this.attribute3 = attribute3;
    }

    public String getAttribute4() {
        return attribute4;
    }

    public void setAttribute4(String attribute4) {
        this.attribute4 = attribute4;
    }

    public String getAttribute5() {
        return attribute5;
    }

    public void setAttribute5(String attribute5) {
        this.attribute5 = attribute5;
    }

    public String getAttribute6() {
        return attribute6;
    }

    public void setAttribute6(String attribute6) {
        this.attribute6 = attribute6;
    }

    public String getAttribute7() {
        return attribute7;
    }

    public void setAttribute7(String attribute7) {
        this.attribute7 = attribute7;
    }

    public String getAttribute8() {
        return attribute8;
    }

    public void setAttribute8(String attribute8) {
        this.attribute8 = attribute8;
    }

    public String getAttribute9() {
        return attribute9;
    }

    public void setAttribute9(String attribute9) {
        this.attribute9 = attribute9;
    }

    public String getAttribute10() {
        return attribute10;
    }

    public void setAttribute10(String attribute10) {
        this.attribute10 = attribute10;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
}
