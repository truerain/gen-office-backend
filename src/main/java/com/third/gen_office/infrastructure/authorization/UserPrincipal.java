package com.third.gen_office.infrastructure.authorization;

import com.third.gen_office.domain.user.UserEntity;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails {
    private final UserEntity userEntity;
    private final List<GrantedAuthority> authorities;

    public UserPrincipal(UserEntity userEntity, List<String> roles) {
        this.userEntity = userEntity;
        this.authorities = roles.stream()
            .map(role -> role.startsWith("ROLE_") ? role : "ROLE_" + role)
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
    }

    public Long getUserId() {
        return userEntity.getUserId();
    }

    public String getEmpNo() {
        return userEntity.getEmpNo();
    }

    public String getEmpName() {
        return userEntity.getEmpName();
    }

    public String getOrgId() {
        return userEntity.getOrgId();
    }

    public String getOrgName() {
        return userEntity.getOrgName();
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
        return "1234";
    }

    @Override
    public String getUsername() {
        return userEntity.getEmpNo();
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
