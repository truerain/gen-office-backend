package com.third.gen_office.mis.admin.rolemenu;

import com.third.gen_office.domain.role.RoleMenuEntity;
import com.third.gen_office.domain.role.RoleMenuId;
import com.third.gen_office.domain.role.RoleMenuRepository;
import com.third.gen_office.mis.admin.rolemenu.dto.RoleMenuRequest;
import com.third.gen_office.mis.admin.rolemenu.dto.RoleMenuResponse;
import com.third.gen_office.mis.admin.rolemenu.dto.RoleMenuView;
import com.third.gen_office.mis.common.util.LastUpdatedByResolver;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class RoleMenuService {
    private final RoleMenuRepository roleMenuRepository;
    private final LastUpdatedByResolver lastUpdatedByResolver;

    public RoleMenuService(RoleMenuRepository roleMenuRepository, LastUpdatedByResolver lastUpdatedByResolver) {
        this.roleMenuRepository = roleMenuRepository;
        this.lastUpdatedByResolver = lastUpdatedByResolver;
    }

    public List<RoleMenuResponse> list(Long roleId, Long menuId) {
        List<RoleMenuEntity> entities;
        if (roleId != null) {
            entities = roleMenuRepository.findByRoleId(roleId);
        } else if (menuId != null) {
            entities = roleMenuRepository.findByMenuId(menuId);
        } else {
            entities = roleMenuRepository.findAll();
        }
        Map<String, String> updatedByNames = lastUpdatedByResolver.loadLastUpdatedByNames(entities);
        return entities.stream()
            .map(entity -> toResponse(entity, updatedByNames.get(entity.getLastUpdatedBy())))
            .toList();
    }

    public Optional<RoleMenuResponse> get(Long roleId, Long menuId) {
        return roleMenuRepository.findById(new RoleMenuId(roleId, menuId))
            .map(entity -> toResponse(entity, lastUpdatedByResolver.resolveLastUpdatedByName(entity.getLastUpdatedBy())));
    }

    public List<RoleMenuView> listByRole(Long roleId) {
        return roleMenuRepository.findRoleMenuViewByRoleId(roleId);
    }

    public Optional<RoleMenuResponse> create(RoleMenuRequest request) {
        if (request.roleId() == null || request.menuId() == null) {
            return Optional.empty();
        }
        RoleMenuEntity entity = roleMenuRepository
            .findById(new RoleMenuId(request.roleId(), request.menuId()))
            .orElseGet(RoleMenuEntity::new);
        entity.setRoleId(request.roleId());
        entity.setMenuId(request.menuId());
        entity.setUseYn(request.useYn());
        RoleMenuEntity saved = roleMenuRepository.save(entity);
        String updatedByName = lastUpdatedByResolver.resolveLastUpdatedByName(saved.getLastUpdatedBy());
        return Optional.of(toResponse(saved, updatedByName));
    }

    public boolean delete(Long roleId, Long menuId) {
        if (!roleMenuRepository.existsByRoleIdAndMenuId(roleId, menuId)) {
            return false;
        }
        roleMenuRepository.deleteByRoleIdAndMenuId(roleId, menuId);
        return true;
    }

    private RoleMenuResponse toResponse(RoleMenuEntity entity, String updatedByName) {
        return new RoleMenuResponse(
            entity.getRoleId(),
            entity.getMenuId(),
            entity.getUseYn(),
            entity.getLastUpdatedBy(),
            updatedByName,
            entity.getLastUpdatedDate() == null ? null : entity.getLastUpdatedDate().toString()
        );
    }
}
