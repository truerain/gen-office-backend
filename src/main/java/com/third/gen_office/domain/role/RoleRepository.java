package com.third.gen_office.domain.role;

import com.third.gen_office.domain.role.RoleEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    List<RoleEntity> findByUseYnOrderBySortOrderAscRoleIdAsc(String useYn);
}
