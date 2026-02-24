package com.third.gen_office.mis.admin.userrole;

import com.third.gen_office.domain.role.RoleRepository;
import com.third.gen_office.domain.user.UserRepository;
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
import org.springframework.data.jpa.domain.Specification;
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
        Specification<UserRoleEntity> spec = buildSpecification(userId, roleId, useYn);
        Sort sortSpec = toSort(sort);
        List<UserRoleResponse> items = userRoleRepository.findAll(spec, sortSpec).stream()
            .map(this::toResponse)
            .toList();
        return items;
    }

    @Transactional(readOnly = true)
    public UserRoleResponse get(Long userId, Long roleId) {
        validateKey(userId, roleId);
        UserRoleEntity entity = userRoleRepository.findById(new UserRoleId(userId, roleId))
            .orElseThrow(() -> new NotFoundException("user_role.not_found"));
        return toResponse(entity);
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

        UserRoleEntity entity = new UserRoleEntity();
        entity.setUserId(request.userId());
        entity.setRoleId(request.roleId());
        entity.setUseYn(normalizeUseYn(request.useYn()));
        applyAttributes(entity, request.attribute1(), request.attribute2(), request.attribute3(), request.attribute4(),
            request.attribute5(), request.attribute6(), request.attribute7(), request.attribute8(), request.attribute9(),
            request.attribute10());
        userRoleRepository.save(entity);

        return get(request.userId(), request.roleId());
    }

    @Transactional
    public UserRoleResponse update(Long userId, Long roleId, UserRoleRequest request) {
        validateKey(userId, roleId);
        validateUpdateRequest(request);

        UserRoleEntity entity = userRoleRepository.findById(new UserRoleId(userId, roleId))
            .orElseThrow(() -> new NotFoundException("user_role.not_found"));

        if (StringUtils.hasText(request.useYn())) {
            entity.setUseYn(normalizeUseYn(request.useYn()));
        }
        applyAttributes(entity, request.attribute1(), request.attribute2(), request.attribute3(), request.attribute4(),
            request.attribute5(), request.attribute6(), request.attribute7(), request.attribute8(), request.attribute9(),
            request.attribute10());
        userRoleRepository.save(entity);

        return toResponse(entity);
    }

    @Transactional
    public void delete(Long userId, Long roleId) {
        validateKey(userId, roleId);
        UserRoleEntity entity = userRoleRepository.findById(new UserRoleId(userId, roleId))
            .orElseThrow(() -> new NotFoundException("user_role.not_found"));
        entity.setUseYn("N");
        userRoleRepository.save(entity);
    }

    private Specification<UserRoleEntity> buildSpecification(Long userId, Long roleId, String useYn) {
        Specification<UserRoleEntity> spec = Specification.where(null);
        if (userId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("userId"), userId));
        }
        if (roleId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("roleId"), roleId));
        }
        if (StringUtils.hasText(useYn)) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("useYn"), useYn));
        }
        return spec;
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

    private UserRoleResponse toResponse(UserRoleEntity entity) {
        return new UserRoleResponse(
            entity.getUserId(),
            entity.getRoleId(),
            entity.getUseYn(),
            entity.getAttribute1(),
            entity.getAttribute2(),
            entity.getAttribute3(),
            entity.getAttribute4(),
            entity.getAttribute5(),
            entity.getAttribute6(),
            entity.getAttribute7(),
            entity.getAttribute8(),
            entity.getAttribute9(),
            entity.getAttribute10(),
            entity.getCreationDate(),
            entity.getLastUpdatedDate()
        );
    }

    private void applyAttributes(
        UserRoleEntity entity,
        String attribute1,
        String attribute2,
        String attribute3,
        String attribute4,
        String attribute5,
        String attribute6,
        String attribute7,
        String attribute8,
        String attribute9,
        String attribute10
    ) {
        entity.setAttribute1(attribute1);
        entity.setAttribute2(attribute2);
        entity.setAttribute3(attribute3);
        entity.setAttribute4(attribute4);
        entity.setAttribute5(attribute5);
        entity.setAttribute6(attribute6);
        entity.setAttribute7(attribute7);
        entity.setAttribute8(attribute8);
        entity.setAttribute9(attribute9);
        entity.setAttribute10(attribute10);
    }

    private void validateCreateRequest(UserRoleRequest request) {
        if (request == null) {
            throw new BadRequestException("user_role.invalid_request");
        }
        validateKey(request.userId(), request.roleId());
        if (StringUtils.hasText(request.useYn()) && !isValidUseYn(request.useYn())) {
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
