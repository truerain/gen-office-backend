package com.third.gen_office.mis.admin.user.dao;

import com.third.gen_office.mis.admin.user.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmpNo(String empNo);
    List<User> findByEmpNameContainingIgnoreCase(String empName);
}
