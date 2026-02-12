package com.third.gen_office.mis.admin.menu;

import com.third.gen_office.domain.menu.Menu;
import com.third.gen_office.domain.menu.MenuRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class MenuService {
    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public List<Menu> list() {
        return menuRepository.findAll();
    }

    public List<Menu> chiidlMenu(Long id) { return menuRepository.findByParentMenuId(id); }

    public Optional<Menu> get(Long id) {
        return menuRepository.findById(id);
    }

    public Menu create(MenuRequest request) {
        Menu menu = new Menu();
        applyRequest(menu, request);
        menu.setMenuId(request.menuId());
        return menuRepository.save(menu);
    }

    public Optional<Menu> update(Long id, MenuRequest request) {
        return menuRepository.findById(id)
            .map(menu -> {
                applyRequest(menu, request);
                return menuRepository.save(menu);
            });
    }

    public boolean delete(Long id) {
        if (!menuRepository.existsById(id)) {
            return false;
        }
        menuRepository.deleteById(id);
        return true;
    }

    private void applyRequest(Menu menu, MenuRequest request) {
        menu.setMenuName(request.menuName());
        menu.setMenuNameEng(request.menuNameEng());
        menu.setMenuDesc(request.menuDesc());
        menu.setMenuDescEng(request.menuDescEng());
        menu.setMenuLevel(request.menuLevel());
        menu.setExecComponent(request.execComponent());
        menu.setMenuIcon(request.menuIcon());
        menu.setParentMenuId(request.parentMenuId());
        menu.setDisplayYn(request.displayYn());
        menu.setUseYn(request.useYn());
        menu.setSortOrder(request.sortOrder());
        menu.setAttribute1(request.attribute1());
        menu.setAttribute2(request.attribute2());
        menu.setAttribute3(request.attribute3());
        menu.setAttribute4(request.attribute4());
        menu.setAttribute5(request.attribute5());
        menu.setAttribute6(request.attribute6());
        menu.setAttribute7(request.attribute7());
        menu.setAttribute8(request.attribute8());
        menu.setAttribute9(request.attribute9());
        menu.setAttribute10(request.attribute10());
        menu.setCreatedBy(request.createdBy());
        menu.setLastUpdatedBy(request.lastUpdatedBy());
    }
}
