package com.erms.ERMS_Application.Authentication.security;


import com.erms.ERMS_Application.Authentication.Orgnization.Organization;
import com.erms.ERMS_Application.Authentication.admin.Admin;
import com.erms.ERMS_Application.Authentication.managers.Managers;
import com.erms.ERMS_Application.Authentication.saleManager.SaleManager;
import com.erms.ERMS_Application.Authentication.sales.Sales;
import com.erms.ERMS_Application.Authentication.technician.Technician;
import com.erms.ERMS_Application.Authentication.user.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


public class CustomUserDetails implements UserDetails {

    private final String email;
    private String password;
    private final Role role;
    private final Object user;

    public CustomUserDetails(Admin admin) {
        this.email = admin.getEmail();
        this.password = admin.getPassword();
        this.role = admin.getRole();
        this.user = admin; // Store the user object
    }

    public CustomUserDetails(Managers manager) {
        this.email = manager.getEmail();
        this.password = manager.getPassword();
        this.role = manager.getRole();
        this.user = manager; // Store the user object
    }

    public CustomUserDetails(SaleManager saleManager) {
        this.email = saleManager.getEmail();
        this.password = saleManager.getPassword();
        this.role = saleManager.getRole();
        this.user = saleManager; // Store the user object
    }

    public CustomUserDetails(Technician technician) {
        this.email = technician.getEmail();
        this.password = technician.getPassword();
        this.role = technician.getRole();
        this.user = technician; // Store the user object
    }

    public CustomUserDetails(Sales sales) {
        this.email = sales.getEmail();
        this.password = sales.getPassword();
        this.role = sales.getRole();
        this.user = sales; // Store the user object
    }

    public CustomUserDetails(Organization organization) {
        this.email = organization.getEmail();
        this.role = organization.getRole();
        this.user = organization; // Store the user object
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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

    public Object getUser() {
        return user; // Return the stored user object
    }
}

