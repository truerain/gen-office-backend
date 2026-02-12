package com.third.gen_office.mis.admin.rolemenu;

import com.third.gen_office.infrastructure.authorization.RoleMenuEntity;
import com.third.gen_office.infrastructure.authorization.RoleMenuId;
import com.third.gen_office.mis.admin.rolemenu.dao.RoleMenuRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class RoleMenuService {
    private final RoleMenuRepository roleMenuRepository;

    public RoleMenuService(RoleMenuRepository roleMenuRepository) {
        this.roleMenuRepository = roleMenuRepository;
    }

    public List<RoleMenuEntity> list(Long roleId, Long menuId) {
        if (roleId != null) {
            return roleMenuRepository.findByRoleId(roleId);
        }
        if (menuId != null) {
            return roleMenuRepository.findByMenuId(menuId);
        }
        return roleMenuRepository.findAll();
    }

    public Optional<RoleMenuEntity> get(Long roleId, Long menuId) {
        return roleMenuRepository.findById(new RoleMenuId(roleId, menuId));
    }

    public List<RoleMenuView> listByRole(Long roleId) {
        return roleMenuRepository.findRoleMenuViewByRoleId(roleId);
    }

    public Optional<RoleMenuEntity> create(RoleMenuRequest request) {
        if (request.roleId() == null || request.menuId() == null) {
            return Optional.empty();
        }
        RoleMenuEntity entity = roleMenuRepository
            .findById(new RoleMenuId(request.roleId(), request.menuId()))
            .orElseGet(RoleMenuEntity::new);
        entity.setRoleId(request.roleId());
        entity.setMenuId(request.menuId());
        entity.setUseYn(request.useYn());
        return Optional.of(roleMenuRepository.save(entity));
    }

    public boolean delete(Long roleId, Long menuId) {
        if (!roleMenuRepository.existsByRoleIdAndMenuId(roleId, menuId)) {
            return false;
        }
        roleMenuRepository.deleteByRoleIdAndMenuId(roleId, menuId);
        return true;
    }
}
