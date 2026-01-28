package com.third.gen_office.mis.admin.user;

import java.util.List;
import java.util.Optional;
import com.third.gen_office.mis.admin.user.dao.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> list() {
        return userRepository.findAll();
    }

    public List<User> listByEmpName(String empName) {
        return userRepository.findByEmpNameContainingIgnoreCase(empName);
    }

    public Optional<User> get(Long id) {
        return userRepository.findById(id);
    }

    public User create(UserRequest request) {
        User user = new User();
        applyRequest(user, request);
        return userRepository.save(user);
    }

    public Optional<User> update(Long id, UserRequest request) {
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

    private void applyRequest(User user, UserRequest request) {
        user.setEmpNo(request.empNo());
        user.setEmpName(request.empName());
        user.setEmpNameEng(request.empNameEng());
        user.setPassword(request.password());
        user.setEmail(request.email());
        user.setOrgId(request.orgId());
        user.setOrgName(request.orgName());
        user.setTitleCd(request.titleCd());
        user.setTitleName(request.titleName());
        user.setWorkTel(request.workTel());
        user.setMobileTel(request.mobileTel());
        user.setLangCd(request.langCd());
        user.setAttribute1(request.attribute1());
        user.setAttribute2(request.attribute2());
        user.setAttribute3(request.attribute3());
        user.setAttribute4(request.attribute4());
        user.setAttribute5(request.attribute5());
        user.setAttribute6(request.attribute6());
        user.setAttribute7(request.attribute7());
        user.setAttribute8(request.attribute8());
        user.setAttribute9(request.attribute9());
        user.setAttribute10(request.attribute10());
        user.setCreatedBy(request.createdBy());
        user.setLastUpdatedBy(request.lastUpdatedBy());
    }
}
