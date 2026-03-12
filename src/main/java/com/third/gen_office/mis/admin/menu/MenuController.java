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

@Tag(name = "Menu", description = "Menu management API")
@RestController
@RequestMapping("/api/menus")
public class MenuController {
    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping
    @Operation(summary = "List menus")
    public ResponseEntity<ApiResponse<List<MenuResponse>>> list() {
        return ResponseEntity.ok(ApiResponse.ok(menuService.list()));
    }

    @GetMapping("/submenu/{id}")
    @Operation(summary = "List submenu items")
    public ResponseEntity<ApiResponse<List<MenuResponse>>> childMenu(
        @Parameter(description = "Parent menu ID") @PathVariable Long id
    ) {
        return ResponseEntity.ok(ApiResponse.ok(menuService.childMenu(id)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get menu")
    public ResponseEntity<ApiResponse<MenuResponse>> get(
        @Parameter(description = "Menu ID") @PathVariable Long id
    ) {
        MenuResponse menu = menuService.get(id)
            .orElseThrow(() -> new NotFoundException("menu.not_found"));
        return ResponseEntity.ok(ApiResponse.ok(menu));
    }

    @PostMapping
    @Operation(summary = "Create menu")
    public ResponseEntity<ApiResponse<Void>> create(@RequestBody MenuRequest request) {
        menuService.create(request);
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update menu")
    public ResponseEntity<ApiResponse<Void>> update(
        @Parameter(description = "Menu ID") @PathVariable Long id,
        @RequestBody MenuRequest request
    ) {
        menuService.update(id, request)
            .orElseThrow(() -> new NotFoundException("menu.not_found"));
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete menu")
    public ResponseEntity<ApiResponse<Void>> delete(@Parameter(description = "Menu ID") @PathVariable Long id) {
        if (!menuService.delete(id)) {
            throw new NotFoundException("menu.not_found");
        }
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @PostMapping("/bulk")
    @Operation(summary = "Bulk commit menus")
    public ResponseEntity<ApiResponse<Void>> bulkCommit(@RequestBody MenuBulkRequest request) {
        menuService.bulkCommit(request);
        return ResponseEntity.ok(ApiResponse.ok());
    }
}
