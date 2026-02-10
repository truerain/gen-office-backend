package com.third.gen_office.infrastructure.authorization;

import java.util.List;

public enum Role {
    USER,
    ADMIN;

    public static final String PREFIX = "ROLE_";

    public String authority() {
        return PREFIX + name();
    }

    public static List<String> defaultRoles() {
        return List.of(USER.name());
    }
}
