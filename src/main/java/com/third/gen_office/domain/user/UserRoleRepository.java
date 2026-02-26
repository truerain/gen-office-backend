package com.third.gen_office.domain.user;

import com.third.gen_office.mis.admin.userrole.dto.UserRoleDetailRow;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity, UserRoleId>,
    JpaSpecificationExecutor<UserRoleEntity> {
    List<UserRoleEntity> findByUserId(Long userId);
    List<UserRoleEntity> findByRoleId(Long roleId);
    boolean existsByUserIdAndRoleId(Long userId, Long roleId);

    @Query("""
        select
            ur.userId as userId,
            ur.roleId as roleId,
            ur.primaryYn as primaryYn,
            ur.useYn as useYn,
            u.empNo as empNo,
            u.empName as empName,
            u.orgName as orgName,
            r.roleName as roleName,
            ur.lastUpdatedBy as lastUpdatedBy,
            u2.empName as lastUpdatedByName,
            ur.lastUpdatedDate as lastUpdatedDate
        from UserRoleEntity ur
        join UserEntity u on u.userId = ur.userId
        join RoleEntity r on r.roleId = ur.roleId
        left join UserEntity u2 on u2.empNo = ur.lastUpdatedBy
        where (:userId is null or ur.userId = :userId)
          and (:roleId is null or ur.roleId = :roleId)
          and (:useYn is null or ur.useYn = :useYn)
        """)
    List<UserRoleDetailRow> findDetailed(
        @Param("userId") Long userId,
        @Param("roleId") Long roleId,
        @Param("useYn") String useYn,
        Sort sort
    );

    @Query("""
        select
            ur.userId as userId,
            ur.roleId as roleId,
            ur.primaryYn as primaryYn,
            ur.useYn as useYn,
            u.empNo as empNo,
            u.empName as empName,
            u.orgName as orgName,
            r.roleName as roleName,
            ur.lastUpdatedBy as lastUpdatedBy,
            u2.empName as lastUpdatedByName,
            ur.lastUpdatedDate as lastUpdatedDate
        from UserRoleEntity ur
        join UserEntity u on u.userId = ur.userId
        join RoleEntity r on r.roleId = ur.roleId
        left join UserEntity u2 on u2.empNo = ur.lastUpdatedBy
        where ur.userId = :userId
          and ur.roleId = :roleId
        """)
    UserRoleDetailRow findDetail(@Param("userId") Long userId, @Param("roleId") Long roleId);

    @Modifying
    @Query("""
        update UserRoleEntity ur
        set ur.primaryYn = 'N'
        where ur.userId = :userId
          and ur.roleId <> :roleId
        """)
    int clearPrimaryForUser(@Param("userId") Long userId, @Param("roleId") Long roleId);

    @Query("""
        select r.roleCd
        from UserRoleEntity ur
        join RoleEntity r on r.roleId = ur.roleId
        where ur.userId = :userId
          and ur.useYn = :useYn
        """)
    List<String> findRoleCdsByUserId(@Param("userId") Long userId, @Param("useYn") String useYn);

}
