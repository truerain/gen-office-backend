package com.third.gen_office.mis.admin.role;

import com.third.gen_office.global.api.ApiResponse;
import com.third.gen_office.global.error.NotFoundException;
import com.third.gen_office.mis.admin.role.dto.RoleBulkRequest;
import com.third.gen_office.mis.admin.role.dto.RoleOptionResponse;
import com.third.gen_office.mis.admin.role.dto.RoleRequest;
import com.third.gen_office.mis.admin.role.dto.RoleResponse;
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
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<ApiResponse<List<RoleResponse>>> list() {
        return ResponseEntity.ok(ApiResponse.ok(roleService.list()));
    }

    @GetMapping("/options")
    @Operation(summary = "List role options for select")
    public ResponseEntity<ApiResponse<List<RoleOptionResponse>>> listOptions(
        @RequestParam(value = "useYn", required = false, defaultValue = "Y") String useYn
    ) {
        return ResponseEntity.ok(ApiResponse.ok(roleService.listOptions(useYn)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get role")
    public ResponseEntity<ApiResponse<RoleResponse>> get(@Parameter(description = "role id") @PathVariable Long id) {
        RoleResponse role = roleService.get(id)
            .orElseThrow(() -> new NotFoundException("role.not_found"));
        return ResponseEntity.ok(ApiResponse.ok(role));
    }

    @PostMapping
    @Operation(summary = "Create role")
    public ResponseEntity<ApiResponse<Void>> create(@RequestBody RoleRequest request) {
        roleService.create(request);
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update role")
    public ResponseEntity<ApiResponse<Void>> update(
        @Parameter(description = "role id") @PathVariable Long id,
        @RequestBody RoleRequest request
    ) {
        roleService.update(id, request)
            .orElseThrow(() -> new NotFoundException("role.not_found"));
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete role")
    public ResponseEntity<ApiResponse<Void>> delete(@Parameter(description = "role id") @PathVariable Long id) {
        if (!roleService.delete(id)) {
            throw new NotFoundException("role.not_found");
        }
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @PostMapping("/bulk")
    @Operation(summary = "Bulk commit roles")
    public ResponseEntity<ApiResponse<Void>> bulkCommit(@RequestBody RoleBulkRequest request) {
        roleService.bulkCommit(request);
        return ResponseEntity.ok(ApiResponse.ok());
    }
}

