package com.third.gen_office.domain.user;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity, UserRoleId>,
    JpaSpecificationExecutor<UserRoleEntity> {
    List<UserRoleEntity> findByUserId(Long userId);
    List<UserRoleEntity> findByRoleId(Long roleId);
    boolean existsByUserIdAndRoleId(Long userId, Long roleId);

}
