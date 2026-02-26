package com.third.gen_office.mis.common.user;

import com.third.gen_office.domain.user.UserEntity;
import com.third.gen_office.domain.user.UserRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class UserSearchService {
    private final UserRepository userRepository;

    public UserSearchService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<UserSearchResponse> search(String q) {
        List<UserEntity> users;
        if (StringUtils.hasText(q)) {
            users = userRepository.findByEmpNameContainingIgnoreCase(q.trim());
        } else {
            users = userRepository.findAll();
        }
        return users.stream()
            .map(this::toResponse)
            .toList();
    }

    private UserSearchResponse toResponse(UserEntity entity) {
        return new UserSearchResponse(
            entity.getUserId(),
            entity.getEmpNo(),
            entity.getEmpName(),
            entity.getTitleCd(),
            entity.getTitleName(),
            entity.getOrgId(),
            entity.getOrgName()
        );
    }
}
