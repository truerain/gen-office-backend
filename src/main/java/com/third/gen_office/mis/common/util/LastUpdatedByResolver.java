package com.third.gen_office.mis.common.util;

import com.third.gen_office.domain.base.BaseEntity;
import com.third.gen_office.domain.user.UserEntity;
import com.third.gen_office.domain.user.UserRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class LastUpdatedByResolver {
    private final UserRepository userRepository;

    public LastUpdatedByResolver(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Map<String, String> loadLastUpdatedByNames(List<? extends BaseEntity> entities) {
        List<String> empNos = new ArrayList<>();
        Map<String, Boolean> seen = new HashMap<>();
        for (BaseEntity entity : entities) {
            String lastUpdatedBy = entity.getLastUpdatedBy();
            if (StringUtils.hasText(lastUpdatedBy) && !seen.containsKey(lastUpdatedBy)) {
                seen.put(lastUpdatedBy, Boolean.TRUE);
                empNos.add(lastUpdatedBy);
            }
        }
        if (empNos.isEmpty()) {
            return java.util.Collections.emptyMap();
        }
        Map<String, String> result = new HashMap<>();
        List<UserEntity> users = userRepository.findByEmpNoIn(empNos);
        for (UserEntity user : users) {
            result.put(user.getEmpNo(), user.getEmpName());
        }
        return result;
    }

    public String resolveLastUpdatedByName(String lastUpdatedBy) {
        if (!StringUtils.hasText(lastUpdatedBy)) {
            return null;
        }
        return userRepository.findByEmpNo(lastUpdatedBy)
            .map(UserEntity::getEmpName)
            .orElse(null);
    }
}
