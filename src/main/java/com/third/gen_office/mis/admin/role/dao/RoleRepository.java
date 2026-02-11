package com.third.gen_office.mis.admin.role.dao;

import com.third.gen_office.infrastructure.authorization.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
}
