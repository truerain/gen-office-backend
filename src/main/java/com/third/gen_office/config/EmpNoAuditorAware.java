package com.third.gen_office.config;

import com.third.gen_office.infrastructure.authorization.UserPrincipal;
import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("empNoAuditorAware")
public class EmpNoAuditorAware implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserPrincipal userPrincipal) {
            return Optional.ofNullable(userPrincipal.getEmpNo());
        }

        if (principal instanceof String principalName && !"anonymousUser".equals(principalName)) {
            return Optional.of(principalName);
        }

        return Optional.empty();
    }
}
