package com.third.gen_office.mis.admin.role;

import com.third.gen_office.domain.role.RoleEntity;
import com.third.gen_office.domain.role.RoleRepository;
import com.third.gen_office.mis.admin.role.dto.RoleRequest;
import com.third.gen_office.mis.admin.role.dto.RoleResponse;
import com.third.gen_office.mis.common.util.LastUpdatedByResolver;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    private final LastUpdatedByResolver lastUpdatedByResolver;

    public RoleService(RoleRepository roleRepository, LastUpdatedByResolver lastUpdatedByResolver) {
        this.roleRepository = roleRepository;
        this.lastUpdatedByResolver = lastUpdatedByResolver;
    }

    public List<RoleResponse> list() {
        List<RoleEntity> entities = roleRepository.findAll();
        Map<String, String> updatedByNames = lastUpdatedByResolver.loadLastUpdatedByNames(entities);
        return entities.stream()
            .map(entity -> toResponse(entity, updatedByNames.get(entity.getLastUpdatedBy())))
            .toList();
    }

    public Optional<RoleResponse> get(Long id) {
        return roleRepository.findById(id)
            .map(role -> toResponse(role, lastUpdatedByResolver.resolveLastUpdatedByName(role.getLastUpdatedBy())));
    }

    public RoleResponse create(RoleRequest request) {
        RoleEntity role = new RoleEntity();
        applyRequest(role, request);
        RoleEntity saved = roleRepository.save(role);
        String updatedByName = lastUpdatedByResolver.resolveLastUpdatedByName(saved.getLastUpdatedBy());
        return toResponse(saved, updatedByName);
    }

    public Optional<RoleResponse> update(Long id, RoleRequest request) {
        return roleRepository.findById(id)
            .map(role -> {
                applyRequest(role, request);
                RoleEntity saved = roleRepository.save(role);
                String updatedByName = lastUpdatedByResolver.resolveLastUpdatedByName(saved.getLastUpdatedBy());
                return toResponse(saved, updatedByName);
            });
    }

    public boolean delete(Long id) {
        if (!roleRepository.existsById(id)) {
            return false;
        }
        roleRepository.deleteById(id);
        return true;
    }

    private void applyRequest(RoleEntity role, RoleRequest request) {
        role.setRoleCd(request.roleCd());
        role.setRoleName(request.roleName());
        role.setRoleNameEng(request.roleNameEng());
        role.setRoleDesc(request.roleDesc());
        role.setSortOrder(request.sortOrder());
        role.setUseYn(request.useYn());
    }

    private RoleResponse toResponse(RoleEntity role, String updatedByName) {
        return new RoleResponse(
            role.getRoleId(),
            role.getRoleCd(),
            role.getRoleName(),
            role.getRoleNameEng(),
            role.getRoleDesc(),
            role.getSortOrder(),
            role.getUseYn(),
            role.getLastUpdatedBy(),
            updatedByName,
            role.getLastUpdatedDate() == null ? null : role.getLastUpdatedDate().toString()
        );
    }
}
