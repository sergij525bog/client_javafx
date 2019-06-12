package com.javafx.habr_spring.model.server;

import org.springframework.security.core.GrantedAuthority;

public enum ServerRoles implements GrantedAuthority {
    ADMIN,
    AUTHOR,
    SUBAUTHOR,
    EDITOR,
    GUEST;

    @Override
    public String getAuthority() {
        return name();
    }
}
