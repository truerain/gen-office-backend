package com.third.gen_office.mis.admin.user;

import com.third.gen_office.global.api.ApiResponse;
import com.third.gen_office.global.error.NotFoundException;
import com.third.gen_office.mis.admin.user.dto.BulkUserRequest;
import com.third.gen_office.mis.admin.user.dto.UserRequest;
import com.third.gen_office.mis.admin.user.dto.UserResponse;
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

@Tag(name = "User", description = "?ъ슜??愿由?API")
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "?ъ슜??紐⑸줉 議고쉶")
    public List<UserResponse> list(
        @Parameter(description = "?ъ썝紐?寃??遺遺꾩씪移?") @RequestParam(required = false) String empName
    ) {
        if (empName == null || empName.isBlank()) {
            return userService.list();
        }
        return userService.listByEmpName(empName);
    }

    @GetMapping("/{id}")
    @Operation(summary = "?ъ슜???④굔 議고쉶")
    public ResponseEntity<UserResponse> get(@Parameter(description = "user id") @PathVariable Long id) {
        UserResponse userEntity = userService.get(id)
            .orElseThrow(() -> new NotFoundException("user.not_found"));
        return ResponseEntity.ok(userEntity);
    }

    @PostMapping
    @Operation(summary = "?ъ슜???앹꽦")
    public ResponseEntity<ApiResponse> create(@RequestBody UserRequest request) {
        userService.create(request);
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @PutMapping("/{id}")
    @Operation(summary = "?ъ슜???섏젙")
    public ResponseEntity<ApiResponse> update(
        @Parameter(description = "user id") @PathVariable Long id,
        @RequestBody UserRequest request
    ) {
        userService.update(id, request)
            .orElseThrow(() -> new NotFoundException("user.not_found"));
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "?ъ슜????젣")
    public ResponseEntity<ApiResponse> delete(@Parameter(description = "user id") @PathVariable Long id) {
        if (!userService.delete(id)) {
            throw new NotFoundException("user.not_found");
        }
        return ResponseEntity.ok(ApiResponse.ok());
    }
    @PostMapping("/bulk")
    @Operation(summary = "Bulk commit users")
    public ResponseEntity<ApiResponse> bulkCommit(@RequestBody BulkUserRequest request) {
        userService.bulkCommit(request);
        return ResponseEntity.ok(ApiResponse.ok());
    }
}

