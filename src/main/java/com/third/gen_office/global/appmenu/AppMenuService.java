package com.third.gen_office.global.appmenu;

import com.third.gen_office.domain.menu.AppMenuItem;
import com.third.gen_office.domain.menu.MenuRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class AppMenuService {
    private final MenuRepository menuRepository;

    public AppMenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public List<AppMenuItem> list() {
        return menuRepository.findAppMenusByUseYn("Y");
    }

    public Optional<AppMenuItem> get(Long id) {
        return menuRepository.findAppMenuByMenuIdAndUseYn(id, "Y");
    }
}
