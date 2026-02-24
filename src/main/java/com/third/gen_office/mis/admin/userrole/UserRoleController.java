package com.third.gen_office.mis.admin.userrole;

import com.third.gen_office.mis.admin.userrole.dto.UserRoleRequest;
import com.third.gen_office.mis.admin.userrole.dto.UserRoleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
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

@Tag(name = "UserRole", description = "User role mapping API")
@RestController
@RequestMapping("/api/mis/admin")
public class UserRoleController {
    private final UserRoleService userRoleService;

    public UserRoleController(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @GetMapping("/user-roles")
    @Operation(summary = "List user role mappings")
    public java.util.List<UserRoleResponse> list(
        @RequestParam(value = "userId", required = false) Long userId,
        @RequestParam(value = "roleId", required = false) Long roleId,
        @RequestParam(value = "useYn", required = false) String useYn,
        @RequestParam(value = "sort", required = false) String sort
    ) {
        return userRoleService.list(userId, roleId, useYn, sort);
    }

    @GetMapping("/user-roles/{userId}/{roleId}")
    @Operation(summary = "Get user role mapping")
    public UserRoleResponse get(
        @Parameter(description = "user id") @PathVariable Long userId,
        @Parameter(description = "role id") @PathVariable Long roleId
    ) {
        return userRoleService.get(userId, roleId);
    }

    @PostMapping("/user-roles")
    @Operation(summary = "Create user role mapping")
    public ResponseEntity<UserRoleResponse> create(@RequestBody UserRoleRequest request) {
        UserRoleResponse response = userRoleService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/user-roles/{userId}/{roleId}")
    @Operation(summary = "Update user role mapping")
    public UserRoleResponse update(
        @Parameter(description = "user id") @PathVariable Long userId,
        @Parameter(description = "role id") @PathVariable Long roleId,
        @RequestBody UserRoleRequest request
    ) {
        return userRoleService.update(userId, roleId, request);
    }

    @DeleteMapping("/user-roles/{userId}/{roleId}")
    @Operation(summary = "Delete user role mapping (soft)")
    public ResponseEntity<Void> delete(
        @Parameter(description = "user id") @PathVariable Long userId,
        @Parameter(description = "role id") @PathVariable Long roleId
    ) {
        userRoleService.delete(userId, roleId);
        return ResponseEntity.noContent().build();
    }

}
