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

    public List<Menu> chiidlMenu(Long id) { return menuRepository.findByPrntMenuId(id); }

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
        menu.setMenuLevel(request.menuLevel());
        menu.setPrntMenuId(request.prntMenuId());
        menu.setDsplFlag(request.dsplFlag());
        menu.setUseFlag(request.useFlag());
        menu.setSortOrder(request.sortOrder());
        menu.setUrl(request.url());
        menu.setParam1(request.param1());
        menu.setParam2(request.param2());
        menu.setParam3(request.param3());
        menu.setParam4(request.param4());
        menu.setParam5(request.param5());
        menu.setAbAuthFlag(request.abAuthFlag());
        menu.setCAuthFlag(request.cAuthFlag());
        menu.setEAuthFlag(request.eAuthFlag());
        menu.setFAuthFlag(request.fAuthFlag());
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
