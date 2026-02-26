package com.third.gen_office.mis.admin.menu;

import com.third.gen_office.domain.menu.MenuEntity;
import com.third.gen_office.domain.menu.MenuRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.third.gen_office.mis.admin.menu.dto.MenuRequest;
import com.third.gen_office.mis.admin.menu.dto.MenuResponse;
import com.third.gen_office.mis.common.util.LastUpdatedByResolver;
import org.springframework.stereotype.Service;

@Service
public class MenuService {
    private final MenuRepository menuRepository;

    private final LastUpdatedByResolver lastUpdatedByResolver;

    public MenuService(MenuRepository menuRepository,
                       LastUpdatedByResolver lastUpdatedByResolver) {
        this.menuRepository = menuRepository;
        this.lastUpdatedByResolver = lastUpdatedByResolver;
    }

    public List<MenuResponse> list() {
        List<MenuEntity> entities = menuRepository.findAll();
        Map<String, String> updatedByNames = lastUpdatedByResolver.loadLastUpdatedByNames(entities);

        return entities.stream()
            .map(entity -> toResponse(entity, updatedByNames.get(entity.getLastUpdatedBy())) )
            .toList();
    }

    public List<MenuResponse> childMenu(Long id) {
        List<MenuEntity> entities = menuRepository.findByParentMenuId(id);
        Map<String, String> updatedByNames = lastUpdatedByResolver.loadLastUpdatedByNames(entities);

        return entities
            .stream()
            .map(entity -> toResponse(entity, updatedByNames.get(entity.getLastUpdatedBy())) )
            .toList();
    }

    public Optional<MenuResponse> get(Long id) {
        return menuRepository.findById(id)
            .map(menu -> toResponse(menu, lastUpdatedByResolver.resolveLastUpdatedByName(menu.getLastUpdatedBy())));
    }

    public MenuResponse create(MenuRequest request) {
        MenuEntity menu = new MenuEntity();
        applyRequest(menu, request);
        menu.setMenuId(request.menuId());
        MenuEntity saved = menuRepository.save(menu);
        String updatedByName = lastUpdatedByResolver.resolveLastUpdatedByName(saved.getLastUpdatedBy());
        return toResponse(saved, updatedByName);
    }

    public Optional<MenuResponse> update(Long id, MenuRequest request) {
        return menuRepository.findById(id)
            .map(menu -> {
                applyRequest(menu, request);
                MenuEntity saved = menuRepository.save(menu);
                String updatedByName = lastUpdatedByResolver.resolveLastUpdatedByName(saved.getLastUpdatedBy());
                return toResponse(saved, updatedByName);
            });
    }

    public boolean delete(Long id) {
        if (!menuRepository.existsById(id)) {
            return false;
        }
        menuRepository.deleteById(id);
        return true;
    }

    private void applyRequest(MenuEntity menu, MenuRequest request) {
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
    }

    private MenuResponse toResponse(MenuEntity menu, String updatedByName) {
        return new MenuResponse(
            menu.getMenuId(),
            menu.getMenuName(),
            menu.getMenuNameEng(),
            menu.getMenuDesc(),
            menu.getMenuDescEng(),
            menu.getMenuLevel(),
            menu.getExecComponent(),
            menu.getMenuIcon(),
            menu.getParentMenuId(),
            menu.getDisplayYn(),
            menu.getUseYn(),
            menu.getSortOrder(),
            menu.getLastUpdatedBy(),
            updatedByName,
            menu.getLastUpdatedDate()
        );
    }
}
