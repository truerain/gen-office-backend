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
        user.setTitle(request.title());
        user.setLangCd(request.langCd());
        user.setCreatedBy(request.createdBy());
        user.setLastUpdatedBy(request.lastUpdatedBy());
    }
}
