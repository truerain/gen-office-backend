package com.third.gen_office.mis.admin.user;

import com.third.gen_office.domain.user.UserEntity;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User", description = "사용자 관리 API")
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "사용자 목록 조회")
    public List<UserEntity> list(
        @Parameter(description = "이름 검색(부분 일치)") @RequestParam(required = false) String empName
    ) {
        if (empName == null || empName.isBlank()) {
            return userService.list();
        }
        return userService.listByEmpName(empName);
    }

    @GetMapping("/{id}")
    @Operation(summary = "사용자 단건 조회")
    public ResponseEntity<UserEntity> get(@Parameter(description = "user id") @PathVariable Long id) {
        UserEntity userEntity = userService.get(id)
            .orElseThrow(() -> new NotFoundException("user.not_found"));
        return ResponseEntity.ok(userEntity);
    }

    @PostMapping
    @Operation(summary = "사용자 생성")
    public ResponseEntity<UserEntity> create(@RequestBody UserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "사용자 수정")
    public ResponseEntity<UserEntity> update(
        @Parameter(description = "user id") @PathVariable Long id,
        @RequestBody UserRequest request
    ) {
        UserEntity userEntity = userService.update(id, request)
            .orElseThrow(() -> new NotFoundException("user.not_found"));
        return ResponseEntity.ok(userEntity);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "사용자 삭제")
    public ResponseEntity<Void> delete(@Parameter(description = "user id") @PathVariable Long id) {
        if (!userService.delete(id)) {
            throw new NotFoundException("user.not_found");
        }
        return ResponseEntity.noContent().build();
    }
}
