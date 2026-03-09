package com.third.gen_office.mis.admin.rolemenu;

import com.third.gen_office.domain.role.RoleMenuEntity;
import com.third.gen_office.domain.role.RoleMenuId;
import com.third.gen_office.domain.role.RoleMenuRepository;
import com.third.gen_office.global.error.BadRequestException;
import com.third.gen_office.mis.admin.rolemenu.dto.RoleMenuBulkRequest;
import com.third.gen_office.mis.admin.rolemenu.dto.RoleMenuIdRequest;
import com.third.gen_office.mis.admin.rolemenu.dto.RoleMenuRequest;
import com.third.gen_office.mis.admin.rolemenu.dto.RoleMenuResponse;
import com.third.gen_office.mis.admin.rolemenu.dto.RoleMenuView;
import com.third.gen_office.mis.common.util.LastUpdatedByResolver;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        List<RoleMenuView> views = roleMenuRepository.findRoleMenuViewByRoleId(roleId);
        Map<String, String> updatedByNames = lastUpdatedByResolver
            .loadLastUpdatedByNames(roleMenuRepository.findByRoleId(roleId));
        return views.stream()
            .map(view -> new RoleMenuView(
                view.menuId(),
                view.menuName(),
                view.menuNameEng(),
                view.menuDesc(),
                view.menuDescEng(),
                view.menuLevel(),
                view.execComponent(),
                view.menuIcon(),
                view.parentMenuId(),
                view.displayYn(),
                view.sortOrder(),
                view.useYn(),
                view.lastUpdatedBy(),
                updatedByNames.get(view.lastUpdatedBy()),
                view.lastUpdatedDate()
            ))
            .toList();
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

    @Transactional
    public void bulkCommit(RoleMenuBulkRequest request) {
        if (request == null) {
            throw new BadRequestException("role_menu.bulk_invalid_request");
        }

        List<RoleMenuRequest> creates = request.creates() == null ? List.of() : request.creates();
        for (RoleMenuRequest item : creates) {
            if (item == null) {
                throw new BadRequestException("role_menu.create_invalid_request");
            }
            if (create(item).isEmpty()) {
                throw new BadRequestException("role_menu.create_invalid_request");
            }
        }

        List<RoleMenuRequest> updates = request.updates() == null ? List.of() : request.updates();
        for (RoleMenuRequest item : updates) {
            if (item == null || item.roleId() == null || item.menuId() == null) {
                throw new BadRequestException("role_menu.update_invalid_request");
            }
            if (create(item).isEmpty()) {
                throw new BadRequestException("role_menu.update_invalid_request");
            }
        }

        List<RoleMenuIdRequest> deletes = request.deletes() == null ? List.of() : request.deletes();
        for (RoleMenuIdRequest item : deletes) {
            if (item == null || item.roleId() == null || item.menuId() == null) {
                throw new BadRequestException("role_menu.delete_invalid_request");
            }
            if (!delete(item.roleId(), item.menuId())) {
                throw new BadRequestException("role_menu.delete_invalid_request");
            }
        }
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
