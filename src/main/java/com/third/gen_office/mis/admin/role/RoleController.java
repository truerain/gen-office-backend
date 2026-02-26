package com.third.gen_office.mis.admin.role;

import com.third.gen_office.global.error.NotFoundException;
import com.third.gen_office.mis.admin.role.dto.RoleRequest;
import com.third.gen_office.mis.admin.role.dto.RoleResponse;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Role", description = "Role management API")
@RestController
@RequestMapping("/api/roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    @Operation(summary = "List roles")
    public List<RoleResponse> list() {
        return roleService.list();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get role")
    public ResponseEntity<RoleResponse> get(@Parameter(description = "role id") @PathVariable Long id) {
        RoleResponse role = roleService.get(id)
            .orElseThrow(() -> new NotFoundException("role.not_found"));
        return ResponseEntity.ok(role);
    }

    @PostMapping
    @Operation(summary = "Create role")
    public ResponseEntity<RoleResponse> create(@RequestBody RoleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.create(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update role")
    public ResponseEntity<RoleResponse> update(
        @Parameter(description = "role id") @PathVariable Long id,
        @RequestBody RoleRequest request
    ) {
        RoleResponse role = roleService.update(id, request)
            .orElseThrow(() -> new NotFoundException("role.not_found"));
        return ResponseEntity.ok(role);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete role")
    public ResponseEntity<Void> delete(@Parameter(description = "role id") @PathVariable Long id) {
        if (!roleService.delete(id)) {
            throw new NotFoundException("role.not_found");
        }
        return ResponseEntity.noContent().build();
    }
}
