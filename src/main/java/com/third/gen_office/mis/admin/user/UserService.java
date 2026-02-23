package com.third.gen_office.mis.admin.user;

import java.util.List;
import java.util.Optional;
import com.third.gen_office.domain.user.UserEntity;
import com.third.gen_office.domain.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserEntity> list() {
        return userRepository.findAll();
    }

    public List<UserEntity> listByEmpName(String empName) {
        return userRepository.findByEmpNameContainingIgnoreCase(empName);
    }

    public Optional<UserEntity> get(Long id) {
        return userRepository.findById(id);
    }

    public UserEntity create(UserRequest request) {
        UserEntity userEntity = new UserEntity();
        applyRequest(userEntity, request);
        return userRepository.save(userEntity);
    }

    public Optional<UserEntity> update(Long id, UserRequest request) {
        return userRepository.findById(id)
            .map(user -> {
                applyRequest(user, request);
                return userRepository.save(user);
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
        userEntity.setAttribute1(request.attribute1());
        userEntity.setAttribute2(request.attribute2());
        userEntity.setAttribute3(request.attribute3());
        userEntity.setAttribute4(request.attribute4());
        userEntity.setAttribute5(request.attribute5());
        userEntity.setAttribute6(request.attribute6());
        userEntity.setAttribute7(request.attribute7());
        userEntity.setAttribute8(request.attribute8());
        userEntity.setAttribute9(request.attribute9());
        userEntity.setAttribute10(request.attribute10());
        userEntity.setCreatedBy(request.createdBy());
        userEntity.setLastUpdatedBy(request.lastUpdatedBy());
    }
}
