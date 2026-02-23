package com.third.gen_office.domain.menu;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MenuRepository extends JpaRepository<MenuEntity, Long> {
    List<MenuEntity> findByUseYn(String useYn);

    List<MenuEntity> findByParentMenuId(Long parentMenuId);

    @Query("""
        select
            m.menuId as menuId,
            m.menuName as menuName,
            m.menuNameEng as menuNameEng,
            m.menuDesc as menuDesc,
            m.menuDescEng as menuDescEng,
            m.menuLevel as menuLevel,
            m.execComponent as execComponent,
            m.menuIcon as menuIcon,
            m.parentMenuId as parentMenuId,
            m.displayYn as displayYn,
            m.sortOrder as sortOrder
        from MenuEntity m
        where m.useYn = :useYn
        order by m.sortOrder asc, m.menuId asc
        """)
    List<AppMenuItem> findAppMenusByUseYn(@Param("useYn") String useYn);

    @Query("""
        select
            m.menuId as menuId,
            m.menuName as menuName,
            m.menuNameEng as menuNameEng,
            m.menuDesc as menuDesc,
            m.menuDescEng as menuDescEng,
            m.menuLevel as menuLevel,
            m.execComponent as execComponent,
            m.menuIcon as menuIcon,
            m.parentMenuId as parentMenuId,
            m.displayYn as displayYn,
            m.sortOrder as sortOrder
        from MenuEntity m
        where m.menuId = :menuId
          and m.useYn = :useYn
        """)
    Optional<AppMenuItem> findAppMenuByMenuIdAndUseYn(
        @Param("menuId") Long menuId,
        @Param("useYn") String useYn
    );
}
