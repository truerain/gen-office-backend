package com.third.gen_office.infrastructure.authorization;

import com.third.gen_office.mis.admin.user.User;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails {
    private final User user;
    private final List<GrantedAuthority> authorities;

    public UserPrincipal(User user, List<String> roles) {
        this.user = user;
        this.authorities = roles.stream()
            .map(role -> role.startsWith("ROLE_") ? role : "ROLE_" + role)
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
    }

    public Long getUserId() {
        return user.getUserId();
    }

    public String getEmpNo() {
        return user.getEmpNo();
    }

    public String getEmpName() {
        return user.getEmpName();
    }

    public String getOrgId() {
        return user.getOrgId();
    }

    public String getOrgName() {
        return user.getOrgName();
    }

    public List<String> getRoles() {
        return authorities.stream()
            .map(GrantedAuthority::getAuthority)
            .toList();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmpNo();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
