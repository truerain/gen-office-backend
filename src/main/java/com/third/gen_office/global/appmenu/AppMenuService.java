package com.third.gen_office.global.appmenu;

import com.third.gen_office.domain.menu.Menu;
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

    public List<Menu> list() {
        return menuRepository.findByUseFlag("Y");
    }

    public Optional<Menu> get(Long id) {
        return menuRepository.findById(id);
    }
}
