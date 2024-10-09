package com.erms.ERMS_Application.Authentication.auditing;


import com.erms.ERMS_Application.Authentication.admin.Admin;
import com.erms.ERMS_Application.Authentication.managers.Managers;
import com.erms.ERMS_Application.Authentication.saleManager.SaleManager;
import com.erms.ERMS_Application.Authentication.sales.Sales;
import com.erms.ERMS_Application.Authentication.technician.Technician;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class ApplicationAuditAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = 
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (authentication == null || 
                !authentication.isAuthenticated() || 
                authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof Admin) {
            Admin admin = (Admin) principal;
            return Optional.ofNullable(String.valueOf(admin.getId()));  // Convert Long to String
        } else if (principal instanceof Managers) {
            Managers manager = (Managers) principal;
            return Optional.ofNullable(String.valueOf(manager.getId()));  // Convert Long to String
        } else if (principal instanceof SaleManager) {
            SaleManager saleManager = (SaleManager) principal;
            return Optional.ofNullable(String.valueOf(saleManager.getId()));  // Convert Long to String
        } else if (principal instanceof Technician) {
            Technician technician = (Technician) principal;
            return Optional.ofNullable(String.valueOf(technician.getId()));  // Convert Long to String
        } else if (principal instanceof Sales) {
            Sales sales = (Sales) principal;
            return Optional.ofNullable(String.valueOf(sales.getId()));  // Convert Long to String
        }

        return Optional.empty();
    }
}
