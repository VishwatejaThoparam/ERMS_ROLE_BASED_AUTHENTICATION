package com.erms.ERMS_Application.Authentication.security;

import com.erms.ERMS_Application.Authentication.Orgnization.Organization;
import com.erms.ERMS_Application.Authentication.Orgnization.OrganizationRepository;
import com.erms.ERMS_Application.Authentication.admin.Admin;
import com.erms.ERMS_Application.Authentication.admin.AdminRepository;
import com.erms.ERMS_Application.Authentication.managers.ManagerRepository;
import com.erms.ERMS_Application.Authentication.managers.Managers;
import com.erms.ERMS_Application.Authentication.saleManager.SaleManager;
import com.erms.ERMS_Application.Authentication.saleManager.SaleManagerRepository;
import com.erms.ERMS_Application.Authentication.sales.Sales;
import com.erms.ERMS_Application.Authentication.sales.SalesRepository;
import com.erms.ERMS_Application.Authentication.technician.Technician;
import com.erms.ERMS_Application.Authentication.technician.TechnicianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    private  final OrganizationRepository organizationRepository;
    private final AdminRepository adminRepository;
    private final ManagerRepository managerRepository;
    private final SaleManagerRepository saleManagerRepository;
    private final TechnicianRepository technicianRepository;
    private final SalesRepository salesRepository;

    @Autowired
    public CustomUserDetailsService(OrganizationRepository organizationRepository, AdminRepository adminRepository,
                                    ManagerRepository managerRepository,
                                    SaleManagerRepository saleManagerRepository,
                                    TechnicianRepository technicianRepository,
                                    SalesRepository salesRepository) {
        this.organizationRepository = organizationRepository;
        this.adminRepository = adminRepository;
        this.managerRepository = managerRepository;
        this.saleManagerRepository = saleManagerRepository;
        this.technicianRepository = technicianRepository;
        this.salesRepository = salesRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Organization> orgOpt = organizationRepository.findByEmail(email);
        if (orgOpt.isPresent()) {
            return new CustomUserDetails(orgOpt.get());
        }


        Optional<Admin> adminOpt = adminRepository.findByEmail(email);
        if (adminOpt.isPresent()) {
            return new CustomUserDetails(adminOpt.get());
        }

        Optional<Managers> managerOpt = managerRepository.findByEmail(email);
        if (managerOpt.isPresent()) {
            return new CustomUserDetails(managerOpt.get());
        }

        Optional<SaleManager> saleManagerOpt = saleManagerRepository.findByEmail(email);
        if (saleManagerOpt.isPresent()) {
            return new CustomUserDetails(saleManagerOpt.get());
        }

        Optional<Technician> technicianOpt = technicianRepository.findByEmail(email);
        if (technicianOpt.isPresent()) {
            return new CustomUserDetails(technicianOpt.get());
        }

        Optional<Sales> salesOpt = salesRepository.findByEmail(email);
        if (salesOpt.isPresent()) {
            return new CustomUserDetails(salesOpt.get());
        }

        throw new UsernameNotFoundException("User not found with email: " + email);
    }
}
