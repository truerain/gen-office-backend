package com.third.gen_office.mis.admin.role;

import com.third.gen_office.domain.role.RoleEntity;
import com.third.gen_office.domain.role.RoleRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<RoleEntity> list() {
        return roleRepository.findAll();
    }

    public Optional<RoleEntity> get(Long id) {
        return roleRepository.findById(id);
    }

    public RoleEntity create(RoleRequest request) {
        RoleEntity role = new RoleEntity();
        applyRequest(role, request);
        return roleRepository.save(role);
    }

    public Optional<RoleEntity> update(Long id, RoleRequest request) {
        return roleRepository.findById(id)
            .map(role -> {
                applyRequest(role, request);
                return roleRepository.save(role);
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
        role.setAttribute1(request.attribute1());
        role.setAttribute2(request.attribute2());
        role.setAttribute3(request.attribute3());
        role.setAttribute4(request.attribute4());
        role.setAttribute5(request.attribute5());
        role.setAttribute6(request.attribute6());
        role.setAttribute7(request.attribute7());
        role.setAttribute8(request.attribute8());
        role.setAttribute9(request.attribute9());
        role.setAttribute10(request.attribute10());
        role.setCreatedBy(request.createdBy());
        role.setLastUpdatedBy(request.lastUpdatedBy());
    }
}
