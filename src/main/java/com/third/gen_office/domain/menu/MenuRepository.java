package com.third.gen_office.domain.menu;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByUseFlag(String useFlag);

    List<Menu> findByPrntMenuId(Long prntMenuId);
}
