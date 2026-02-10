package com.third.gen_office.global.appmenu;

import com.third.gen_office.domain.menu.Menu;
import com.third.gen_office.global.error.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "AppMenu", description = "Application menu API")
@RestController
@RequestMapping("/api/app-menus")
public class AppMenuController {
    private final AppMenuService appMenuService;

    public AppMenuController(AppMenuService appMenuService) {
        this.appMenuService = appMenuService;
    }

    @GetMapping
    @Operation(summary = "List app menus")
    public List<Menu> list() {
        return appMenuService.list();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get app menu")
    public ResponseEntity<Menu> get(@Parameter(description = "menu id") @PathVariable Long id) {
        Menu menu = appMenuService.get(id)
            .orElseThrow(() -> new NotFoundException("menu.not_found"));
        return ResponseEntity.ok(menu);
    }
}
