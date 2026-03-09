package com.third.gen_office.mis.admin.menu;

import com.third.gen_office.global.api.ApiResponse;
import com.third.gen_office.global.error.NotFoundException;
import com.third.gen_office.mis.admin.menu.dto.MenuBulkRequest;
import com.third.gen_office.mis.admin.menu.dto.MenuRequest;
import com.third.gen_office.mis.admin.menu.dto.MenuResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
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
    public List<MenuResponse> list() {
        return menuService.list();
    }

    @GetMapping("/submenu/{id}")
    @Operation(summary = "하위 메뉴 목록 조회")
    public List<MenuResponse> childMenu(@Parameter(description = "상위 메뉴 ID") @PathVariable Long id) {
        return menuService.childMenu(id);
    }

    @GetMapping("/{id}")
    @Operation(summary = "메뉴 단건 조회")
    public ResponseEntity<MenuResponse> get(@Parameter(description = "메뉴 ID") @PathVariable Long id) {
        MenuResponse menu = menuService.get(id)
            .orElseThrow(() -> new NotFoundException("menu.not_found"));
        return ResponseEntity.ok(menu);
    }

    @PostMapping
    @Operation(summary = "메뉴 생성")
    public ResponseEntity<ApiResponse> create(@RequestBody MenuRequest request) {
        menuService.create(request);
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @PutMapping("/{id}")
    @Operation(summary = "메뉴 수정")
    public ResponseEntity<ApiResponse> update(
        @Parameter(description = "메뉴 ID") @PathVariable Long id,
        @RequestBody MenuRequest request
    ) {
        menuService.update(id, request)
            .orElseThrow(() -> new NotFoundException("menu.not_found"));
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "메뉴 삭제")
    public ResponseEntity<ApiResponse> delete(@Parameter(description = "메뉴 ID") @PathVariable Long id) {
        if (!menuService.delete(id)) {
            throw new NotFoundException("menu.not_found");
        }
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @PostMapping("/bulk")
    @Operation(summary = "메뉴 일괄 저장")
    public ResponseEntity<ApiResponse> bulkCommit(@RequestBody MenuBulkRequest request) {
        menuService.bulkCommit(request);
        return ResponseEntity.ok(ApiResponse.ok());
    }
}
