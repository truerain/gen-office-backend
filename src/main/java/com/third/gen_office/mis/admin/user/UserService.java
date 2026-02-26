package com.third.gen_office.mis.admin.user;

import com.third.gen_office.domain.user.UserEntity;
import com.third.gen_office.domain.user.UserRepository;
import com.third.gen_office.mis.admin.user.dto.UserRequest;
import com.third.gen_office.mis.admin.user.dto.UserResponse;
import com.third.gen_office.mis.common.util.LastUpdatedByResolver;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final LastUpdatedByResolver lastUpdatedByResolver;

    public UserService(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        LastUpdatedByResolver lastUpdatedByResolver
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.lastUpdatedByResolver = lastUpdatedByResolver;
    }

    public List<UserResponse> list() {
        List<UserEntity> entities = userRepository.findAll();
        Map<String, String> updatedByNames = lastUpdatedByResolver.loadLastUpdatedByNames(entities);
        return entities.stream()
            .map(entity -> toResponse(entity, updatedByNames.get(entity.getLastUpdatedBy())))
            .toList();
    }

    public List<UserResponse> listByEmpName(String empName) {
        List<UserEntity> entities = userRepository.findByEmpNameContainingIgnoreCase(empName);
        Map<String, String> updatedByNames = lastUpdatedByResolver.loadLastUpdatedByNames(entities);
        return entities.stream()
            .map(entity -> toResponse(entity, updatedByNames.get(entity.getLastUpdatedBy())))
            .toList();
    }

    public Optional<UserResponse> get(Long id) {
        return userRepository.findById(id)
            .map(entity -> toResponse(entity, lastUpdatedByResolver.resolveLastUpdatedByName(entity.getLastUpdatedBy())));
    }

    public UserResponse create(UserRequest request) {
        UserEntity userEntity = new UserEntity();
        applyRequest(userEntity, request);
        UserEntity saved = userRepository.save(userEntity);
        String updatedByName = lastUpdatedByResolver.resolveLastUpdatedByName(saved.getLastUpdatedBy());
        return toResponse(saved, updatedByName);
    }

    public Optional<UserResponse> update(Long id, UserRequest request) {
        return userRepository.findById(id)
            .map(user -> {
                applyRequest(user, request);
                UserEntity saved = userRepository.save(user);
                String updatedByName = lastUpdatedByResolver.resolveLastUpdatedByName(saved.getLastUpdatedBy());
                return toResponse(saved, updatedByName);
            });
    }

    public boolean delete(Long id) {
        if (!userRepository.existsById(id)) {
            return false;
        }
        userRepository.deleteById(id);
        return true;
    }

    private void applyRequest(UserEntity userEntity, UserRequest request) {
        userEntity.setEmpNo(request.empNo());
        userEntity.setEmpName(request.empName());
        userEntity.setEmpNameEng(request.empNameEng());
        if (request.password() != null && !request.password().isBlank()) {
            userEntity.setPassword(passwordEncoder.encode(request.password()));
        }
        userEntity.setEmail(request.email());
        userEntity.setOrgId(request.orgId());
        userEntity.setOrgName(request.orgName());
        userEntity.setTitleCd(request.titleCd());
        userEntity.setTitleName(request.titleName());
        userEntity.setWorkTel(request.workTel());
        userEntity.setMobileTel(request.mobileTel());
        userEntity.setLangCd(request.langCd());
    }

    private UserResponse toResponse(UserEntity entity, String updatedByName) {
        return new UserResponse(
            entity.getUserId(),
            entity.getEmpNo(),
            entity.getEmpName(),
            entity.getEmpNameEng(),
            entity.getEmail(),
            entity.getOrgId(),
            entity.getOrgName(),
            entity.getTitleCd(),
            entity.getTitleName(),
            entity.getWorkTel(),
            entity.getMobileTel(),
            entity.getLangCd(),
            entity.getLastUpdatedBy(),
            updatedByName,
            entity.getLastUpdatedDate() == null ? null : entity.getLastUpdatedDate().toString()
        );
    }
}
