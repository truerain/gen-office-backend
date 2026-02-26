package com.third.gen_office.infrastructure.authorization;

import com.third.gen_office.domain.user.UserEntity;
import com.third.gen_office.domain.user.UserRepository;
import com.third.gen_office.domain.user.UserRoleRepository;

import java.util.List;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    public UserDetailsServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmpNo(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        List<String> roles = userRoleRepository.findRoleCdsByUserId(userEntity.getUserId(), "Y");
        if (roles == null || roles.isEmpty()) {
            roles = Role.defaultRoles();
        }
        return new UserPrincipal(userEntity, roles);
    }
}
