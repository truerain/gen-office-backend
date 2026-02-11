package com.third.gen_office.mis.admin.rolemenu.dao;

import com.third.gen_office.infrastructure.authorization.RoleMenuEntity;
import com.third.gen_office.infrastructure.authorization.RoleMenuId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleMenuRepository extends JpaRepository<RoleMenuEntity, RoleMenuId> {
    List<RoleMenuEntity> findByRoleId(Long roleId);
    List<RoleMenuEntity> findByMenuId(Long menuId);
    boolean existsByRoleIdAndMenuId(Long roleId, Long menuId);
    void deleteByRoleIdAndMenuId(Long roleId, Long menuId);
}
