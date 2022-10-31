package com.example.prkt2UPsev.Model;
import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER, ADMIN, ROLE_BIG_BONE;

    @Override
    public String getAuthority() { return name(); }
}