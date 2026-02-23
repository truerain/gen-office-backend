package com.third.gen_office.mis.admin.rolemenu;

import com.third.gen_office.domain.role.RoleMenuEntity;
import com.third.gen_office.mis.admin.rolemenu.dto.RoleMenuView;
import com.third.gen_office.global.error.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "RoleMenu", description = "Role menu mapping API")
@RestController
@RequestMapping("/api/role-menus")
public class RoleMenuController {
    private final RoleMenuService roleMenuService;

    public RoleMenuController(RoleMenuService roleMenuService) {
        this.roleMenuService = roleMenuService;
    }

    @GetMapping
    @Operation(summary = "List role menu mappings")
    public List<RoleMenuEntity> list(
        @Parameter(description = "role id") @RequestParam(required = false) Long roleId,
        @Parameter(description = "menu id") @RequestParam(required = false) Long menuId
    ) {
        return roleMenuService.list(roleId, menuId);
    }

    @GetMapping("/{roleId}/{menuId}")
    @Operation(summary = "Get role menu mapping")
    public ResponseEntity<RoleMenuEntity> get(
        @Parameter(description = "role id") @PathVariable Long roleId,
        @Parameter(description = "menu id") @PathVariable Long menuId
    ) {
        RoleMenuEntity roleMenu = roleMenuService.get(roleId, menuId)
            .orElseThrow(() -> new NotFoundException("role_menu.not_found"));
        return ResponseEntity.ok(roleMenu);
    }

    @GetMapping("/view/{roleId}")
    @Operation(summary = "List menus with role mapping")
    public List<RoleMenuView> listByRole(
        @Parameter(description = "role id") @PathVariable Long roleId
    ) {
        return roleMenuService.listByRole(roleId);
    }

    @PostMapping
    @Operation(summary = "Create role menu mapping")
    public ResponseEntity<RoleMenuEntity> create(@RequestBody RoleMenuRequest request) {
        return roleMenuService.create(request)
            .map(roleMenu -> ResponseEntity.status(HttpStatus.CREATED).body(roleMenu))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @DeleteMapping("/{roleId}/{menuId}")
    @Operation(summary = "Delete role menu mapping")
    public ResponseEntity<Void> delete(
        @Parameter(description = "role id") @PathVariable Long roleId,
        @Parameter(description = "menu id") @PathVariable Long menuId
    ) {
        if (!roleMenuService.delete(roleId, menuId)) {
            throw new NotFoundException("role_menu.not_found");
        }
        return ResponseEntity.noContent().build();
    }
}
