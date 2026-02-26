package com.third.gen_office.mis.admin.userrole;

import com.third.gen_office.domain.role.RoleRepository;
import com.third.gen_office.domain.role.RoleEntity;
import com.third.gen_office.domain.user.UserRepository;
import com.third.gen_office.domain.user.UserEntity;
import com.third.gen_office.domain.user.UserRoleEntity;
import com.third.gen_office.domain.user.UserRoleId;
import com.third.gen_office.domain.user.UserRoleRepository;
import com.third.gen_office.global.error.BadRequestException;
import com.third.gen_office.global.error.ConflictException;
import com.third.gen_office.global.error.NotFoundException;
import com.third.gen_office.mis.admin.userrole.dto.UserRoleRequest;
import com.third.gen_office.mis.admin.userrole.dto.UserRoleResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class UserRoleService {
    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserRoleService(
        UserRoleRepository userRoleRepository,
        UserRepository userRepository,
        RoleRepository roleRepository
    ) {
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional(readOnly = true)
    public List<UserRoleResponse> list(
        Long userId,
        Long roleId,
        String useYn,
        String sort
    ) {
        Sort sortSpec = toSort(sort);
        return toResponses(userId, roleId, useYn, sortSpec);
    }

    @Transactional(readOnly = true)
    public UserRoleResponse get(Long userId, Long roleId) {
        validateKey(userId, roleId);
        UserRoleEntity entity = userRoleRepository.findById(new UserRoleId(userId, roleId))
            .orElseThrow(() -> new NotFoundException("user_role.not_found"));
        return toResponse(entity, loadUser(entity.getUserId()), loadRole(entity.getRoleId()));
    }

    @Transactional
    public UserRoleResponse create(UserRoleRequest request) {
        validateCreateRequest(request);
        assertUserExists(request.userId());
        assertRoleExists(request.roleId());

        UserRoleId id = new UserRoleId(request.userId(), request.roleId());
        if (userRoleRepository.existsById(id)) {
            throw new ConflictException("user_role.duplicate");
        }

        boolean primaryRequested = "Y".equalsIgnoreCase(request.primaryYn());
        if (primaryRequested) {
            userRoleRepository.clearPrimaryForUser(request.userId(), request.roleId());
        }

        UserRoleEntity entity = new UserRoleEntity();
        entity.setUserId(request.userId());
        entity.setRoleId(request.roleId());
        entity.setPrimaryYn(normalizePrimaryYn(request.primaryYn()));
        entity.setUseYn(normalizeUseYn(request.useYn()));
        userRoleRepository.save(entity);

        return get(request.userId(), request.roleId());
    }

    @Transactional
    public UserRoleResponse update(Long userId, Long roleId, UserRoleRequest request) {
        validateKey(userId, roleId);
        validateUpdateRequest(request);

        UserRoleEntity entity = userRoleRepository.findById(new UserRoleId(userId, roleId))
            .orElseThrow(() -> new NotFoundException("user_role.not_found"));

        if ("Y".equalsIgnoreCase(request.primaryYn())) {
            userRoleRepository.clearPrimaryForUser(userId, roleId);
        }

        if (StringUtils.hasText(request.useYn())) {
            entity.setUseYn(normalizeUseYn(request.useYn()));
        }
        if (StringUtils.hasText(request.primaryYn())) {
            entity.setPrimaryYn(normalizePrimaryYn(request.primaryYn()));
        }
        userRoleRepository.save(entity);

        return toResponse(entity, loadUser(entity.getUserId()), loadRole(entity.getRoleId()));
    }

    @Transactional
    public void delete(Long userId, Long roleId) {
        validateKey(userId, roleId);
        UserRoleEntity entity = userRoleRepository.findById(new UserRoleId(userId, roleId))
            .orElseThrow(() -> new NotFoundException("user_role.not_found"));
        entity.setUseYn("N");
        userRoleRepository.save(entity);
    }

    private Sort toSort(String sort) {
        Map<String, String> allowed = new HashMap<>();
        allowed.put("user_id", "userId");
        allowed.put("userId", "userId");
        allowed.put("role_id", "roleId");
        allowed.put("roleId", "roleId");
        allowed.put("creation_date", "creationDate");
        allowed.put("createdAt", "creationDate");
        allowed.put("last_updated_date", "lastUpdatedDate");
        allowed.put("updatedAt", "lastUpdatedDate");

        if (!StringUtils.hasText(sort)) {
            return Sort.by("userId").ascending().and(Sort.by("roleId").ascending());
        }

        List<Sort.Order> orders = new ArrayList<>();
        String[] tokens = sort.split(",");
        for (String raw : tokens) {
            String token = raw.trim();
            if (token.isEmpty()) {
                continue;
            }
            String[] parts = token.split("\\s+");
            String property = allowed.get(parts[0]);
            if (property == null) {
                continue;
            }
            Sort.Direction direction = parts.length > 1 && "desc".equalsIgnoreCase(parts[1])
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;
            orders.add(new Sort.Order(direction, property));
        }

        if (orders.isEmpty()) {
            return Sort.by("userId").ascending().and(Sort.by("roleId").ascending());
        }
        return Sort.by(orders);
    }

    private List<UserRoleResponse> toResponses(Long userId, Long roleId, String useYn, Sort sortSpec) {
        return userRoleRepository.findDetailed(userId, roleId, useYn, sortSpec);
    }

    private UserRoleResponse toResponse(UserRoleEntity entity, UserEntity user, RoleEntity role) {
        return new UserRoleResponse(
            entity.getUserId(),
            entity.getRoleId(),
            entity.getPrimaryYn(),
            entity.getUseYn(),
            user == null ? null : user.getEmpNo(),
            user == null ? null : user.getEmpName(),
            user == null ? null : user.getOrgName(),
            role == null ? null : role.getRoleName()
        );
    }

    private UserEntity loadUser(Long userId) {
        if (userId == null) {
            return null;
        }
        return userRepository.findById(userId).orElse(null);
    }

    private RoleEntity loadRole(Long roleId) {
        if (roleId == null) {
            return null;
        }
        return roleRepository.findById(roleId).orElse(null);
    }

    private void validateCreateRequest(UserRoleRequest request) {
        if (request == null) {
            throw new BadRequestException("user_role.invalid_request");
        }
        validateKey(request.userId(), request.roleId());
        if (StringUtils.hasText(request.useYn()) && !isValidUseYn(request.useYn())) {
            throw new BadRequestException("user_role.invalid_request");
        }
        if (StringUtils.hasText(request.primaryYn()) && !isValidUseYn(request.primaryYn())) {
            throw new BadRequestException("user_role.invalid_request");
        }
    }

    private void validateUpdateRequest(UserRoleRequest request) {
        if (request == null) {
            throw new BadRequestException("user_role.invalid_request");
        }
        if (StringUtils.hasText(request.useYn()) && !isValidUseYn(request.useYn())) {
            throw new BadRequestException("user_role.invalid_request");
        }
        if (StringUtils.hasText(request.primaryYn()) && !isValidUseYn(request.primaryYn())) {
            throw new BadRequestException("user_role.invalid_request");
        }
    }

    private void validateKey(Long userId, Long roleId) {
        if (userId == null || roleId == null) {
            throw new BadRequestException("user_role.invalid_request");
        }
    }

    private boolean isValidUseYn(String useYn) {
        return "Y".equalsIgnoreCase(useYn) || "N".equalsIgnoreCase(useYn);
    }

    private String normalizeUseYn(String useYn) {
        if (!StringUtils.hasText(useYn)) {
            return "Y";
        }
        if (!isValidUseYn(useYn)) {
            throw new BadRequestException("user_role.invalid_request");
        }
        return useYn.toUpperCase();
    }

    private String normalizePrimaryYn(String primaryYn) {
        if (!StringUtils.hasText(primaryYn)) {
            return "N";
        }
        if (!isValidUseYn(primaryYn)) {
            throw new BadRequestException("user_role.invalid_request");
        }
        return primaryYn.toUpperCase();
    }

    private void assertUserExists(Long userId) {
        if (userId == null || !userRepository.existsById(userId)) {
            throw new NotFoundException("user.not_found");
        }
    }

    private void assertRoleExists(Long roleId) {
        if (roleId == null || !roleRepository.existsById(roleId)) {
            throw new NotFoundException("role.not_found");
        }
    }
}
