package com.third.gen_office.mis.admin.menu;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import com.third.gen_office.global.error.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Menu", description = "메뉴 관리 API")
@RestController
@RequestMapping("/api/menus")
public class MenuController {
    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping
    @Operation(summary = "메뉴 목록 조회")
    public List<Menu> list() {
        return menuService.list();
    }

    @GetMapping("/{id}")
    @Operation(summary = "메뉴 단건 조회")
    public ResponseEntity<Menu> get(@Parameter(description = "menu id") @PathVariable Long id) {
        Menu menu = menuService.get(id)
            .orElseThrow(() -> new NotFoundException("menu.not_found"));
        return ResponseEntity.ok(menu);
    }

    @PostMapping
    @Operation(summary = "메뉴 생성")
    public ResponseEntity<Menu> create(@RequestBody MenuRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(menuService.create(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "메뉴 수정")
    public ResponseEntity<Menu> update(
        @Parameter(description = "menu id") @PathVariable Long id,
        @RequestBody MenuRequest request
    ) {
        Menu menu = menuService.update(id, request)
            .orElseThrow(() -> new NotFoundException("menu.not_found"));
        return ResponseEntity.ok(menu);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "메뉴 삭제")
    public ResponseEntity<Void> delete(@Parameter(description = "menu id") @PathVariable Long id) {
        if (!menuService.delete(id)) {
            throw new NotFoundException("menu.not_found");
        }
        return ResponseEntity.noContent().build();
    }
}
