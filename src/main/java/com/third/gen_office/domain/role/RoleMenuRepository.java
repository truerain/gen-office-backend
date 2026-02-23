package com.third.gen_office.domain.role;

import com.third.gen_office.domain.role.RoleMenuEntity;
import com.third.gen_office.domain.role.RoleMenuId;
import com.third.gen_office.mis.admin.rolemenu.dto.RoleMenuView;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleMenuRepository extends JpaRepository<RoleMenuEntity, RoleMenuId> {
    List<RoleMenuEntity> findByRoleId(Long roleId);
    List<RoleMenuEntity> findByMenuId(Long menuId);
    boolean existsByRoleIdAndMenuId(Long roleId, Long menuId);
    void deleteByRoleIdAndMenuId(Long roleId, Long menuId);

    @Query("""
        select new com.third.gen_office.mis.admin.rolemenu.dto.RoleMenuView(
            m.menuId,
            m.menuName,
            m.menuNameEng,
            m.menuDesc,
            m.menuDescEng,
            m.menuLevel,
            m.execComponent,
            m.menuIcon,
            m.parentMenuId,
            m.displayYn,
            m.sortOrder,
            coalesce(rm.useYn, 'N')
        )
        from MenuEntity m
        left join RoleMenuEntity rm
            on rm.menuId = m.menuId and rm.roleId = :roleId
        where m.useYn = 'Y'
          and (
            m.parentMenuId = 0
            or exists (
                select 1
                from MenuEntity p
                where p.menuId = m.parentMenuId
                  and p.useYn = 'Y'
            )
          )
        order by m.sortOrder asc, m.menuId asc
        """)
    List<RoleMenuView> findRoleMenuViewByRoleId(@Param("roleId") Long roleId);
}
