package com.third.gen_office.infrastructure.authorization;

import com.third.gen_office.domain.role.RoleEntity;
import com.third.gen_office.domain.user.UserEntity;
import com.third.gen_office.domain.user.UserRepository;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmpNo(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        List<String> roles = userEntity.getRoles() == null || userEntity.getRoles().isEmpty()
            ? Role.defaultRoles()
            : userEntity.getRoles().stream()
                .map(RoleEntity::getRoleCd)
                .collect(Collectors.toList());
        return new UserPrincipal(userEntity, roles);
    }
}
