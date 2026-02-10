package com.third.gen_office.infrastructure.authorization;

import com.third.gen_office.mis.admin.user.User;
import com.third.gen_office.mis.admin.user.dao.UserRepository;
import com.third.gen_office.infrastructure.authorization.UserPrincipal;
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
        User user = userRepository.findByEmpNo(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        List<String> roles = user.getRoles() == null || user.getRoles().isEmpty()
            ? Role.defaultRoles()
            : user.getRoles().stream()
                .map(RoleEntity::getRoleCd)
                .collect(Collectors.toList());
        return new UserPrincipal(user, roles);
    }
}
