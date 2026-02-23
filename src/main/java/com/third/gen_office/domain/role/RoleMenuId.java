package com.third.gen_office.domain.role;

import java.io.Serializable;
import java.util.Objects;

public class RoleMenuId implements Serializable {
    private Long roleId;
    private Long menuId;

    public RoleMenuId() {
    }

    public RoleMenuId(Long roleId, Long menuId) {
        this.roleId = roleId;
        this.menuId = menuId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public Long getMenuId() {
        return menuId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RoleMenuId that = (RoleMenuId) o;
        return Objects.equals(roleId, that.roleId)
            && Objects.equals(menuId, that.menuId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, menuId);
    }
}
