package com.erms.ERMS_Application.Authentication.user;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public enum Role {

    ORGANIZATION(
            Set.of(
                    Permission.ORGANIZATION_READ,
                    Permission.ORGANIZATION_UPDATE,
                    Permission.ORGANIZATION_CREATE,
                    Permission.ORGANIZATION_DELETE
            )
    ),

    ADMIN(
            Set.of(
                    Permission.ADMIN_READ,
                    Permission.ADMIN_UPDATE,
                    Permission.ADMIN_CREATE,
                    Permission.ADMIN_DELETE
            )
    ),

    MANAGER(
            Set.of(
                    Permission.MANAGER_READ,
                    Permission.MANAGER_UPDATE,
                    Permission.MANAGER_CREATE,
                    Permission.MANAGER_DELETE
            )
    ),
    SALE_MANAGER(
            Set.of(
                    Permission.SALE_MANAGER_READ,
                    Permission.SALE_MANAGER_UPDATE,
                    Permission.SALE_MANAGER_CREATE,
                    Permission.SALE_MANAGER_DELETE
            )
    ),
    TECHNICIAN(
            Set.of(
                    Permission.TECHNICIAN_READ,
                    Permission.TECHNICIAN_UPDATE,
                    Permission.TECHNICIAN_DELETE,
                    Permission.TECHNICIAN_CREATE
            )
    ), SALES(
            Set.of(
                    Permission.SALES_READ,
                    Permission.SALES_UPDATE,
                    Permission.SALES_DELETE,
                    Permission.SALES_CREATE
            )
    );




    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
