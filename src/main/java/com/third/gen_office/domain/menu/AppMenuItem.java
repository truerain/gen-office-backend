package com.third.gen_office.domain.menu;

public interface AppMenuItem {
    Long getMenuId();

    String getMenuName();

    String getMenuNameEng();

    String getMenuDesc();

    String getMenuDescEng();

    Integer getMenuLevel();

    String getExecComponent();

    String getMenuIcon();

    Long getParentMenuId();

    String getDisplayYn();

    Integer getSortOrder();
}
