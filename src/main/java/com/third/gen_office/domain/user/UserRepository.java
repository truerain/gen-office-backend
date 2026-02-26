package com.third.gen_office.domain.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmpNo(String empNo);
    List<UserEntity> findByEmpNoIn(List<String> empNos);
    List<UserEntity> findByEmpNameContainingIgnoreCase(String empName);
}
