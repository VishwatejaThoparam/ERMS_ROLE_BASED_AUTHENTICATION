package com.erms.ERMS_Application.Authentication.userUtil;

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
import com.erms.ERMS_Application.Authentication.user.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UserUtilService {

    private static final int PASSWORD_LENGTH = 8;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private SaleManagerRepository saleManagerRepository;
    @Autowired
    private TechnicianRepository technicianRepository;
    @Autowired
    private SalesRepository salesRepository;
    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private JavaMailSender mailSender;



    // Generate a random password
    public String generateRandomPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVXYZabcdefghijklmnopqrstuvwxyz1234567890";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(PASSWORD_LENGTH);
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    // Verify if the provided old password matches the stored (hashed) password for the user
    public boolean verifyOldPassword(String email, String oldPassword) {

        // Organization
        Optional<Organization> organizationOpt = organizationRepository.findByEmail(email);
        if (organizationOpt.isPresent() && passwordEncoder.matches(oldPassword, organizationOpt.get().getPassword())) {
            return true;
        }


        // Admin
        Optional<Admin> adminOpt = adminRepository.findByEmail(email);
        if (adminOpt.isPresent() && passwordEncoder.matches(oldPassword, adminOpt.get().getPassword())) {
            return true;
        }

        // Manager
        Optional<Managers> managerOpt = managerRepository.findByEmail(email);
        if (managerOpt.isPresent() && passwordEncoder.matches(oldPassword, managerOpt.get().getPassword())) {
            return true;
        }

        // Sale Manager
        Optional<SaleManager> saleManagerOpt = saleManagerRepository.findByEmail(email);
        if (saleManagerOpt.isPresent() && passwordEncoder.matches(oldPassword, saleManagerOpt.get().getPassword())) {
            return true;
        }

        // Technician
        Optional<Technician> technicianOpt = technicianRepository.findByEmail(email);
        if (technicianOpt.isPresent() && passwordEncoder.matches(oldPassword, technicianOpt.get().getPassword())) {
            return true;
        }

        // Sales
        Optional<Sales> salesOpt = salesRepository.findByEmail(email);
        if (salesOpt.isPresent() && passwordEncoder.matches(oldPassword, salesOpt.get().getPassword())) {
            return true;
        }



        return false;
    }

    // Update the password in the corresponding repository based on the user role
    public void updatePasswordInRepository(String email, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);

        // Organization
        organizationRepository.findByEmail(email).ifPresent(organization -> {
            organization.setPassword(encodedPassword);
            organizationRepository.save(organization);
        });

        // Admin
        adminRepository.findByEmail(email).ifPresent(admin -> {
            admin.setPassword(encodedPassword);
            adminRepository.save(admin);
        });

        // Manager
        managerRepository.findByEmail(email).ifPresent(manager -> {
            manager.setPassword(encodedPassword);
            managerRepository.save(manager);
        });

        // Sale Manager
        saleManagerRepository.findByEmail(email).ifPresent(saleManager -> {
            saleManager.setPassword(encodedPassword);
            saleManagerRepository.save(saleManager);
        });

        // Technician
        technicianRepository.findByEmail(email).ifPresent(technician -> {
            technician.setPassword(encodedPassword);
            technicianRepository.save(technician);
        });

        // Sales
        salesRepository.findByEmail(email).ifPresent(sales -> {
            sales.setPassword(encodedPassword);
            salesRepository.save(sales);
        });

    }


// Change password logic with new password validation
    public String changePassword(String email, String oldPassword, String newPassword) {
        if (newPassword.length() < PASSWORD_LENGTH) {
            throw new IllegalArgumentException("Password must be at least " + PASSWORD_LENGTH + " characters long.");
        }

        if (verifyOldPassword(email, oldPassword)) {
            updatePasswordInRepository(email, newPassword);
            return "Password successfully changed for email: " + email;
        } else {
            throw new IllegalArgumentException("Old password is incorrect.");
        }
    }


    public String generateUserRoleId(Role role) {
        String prefix;
        List<String> lastRoleIdList;
        Pageable pageable = PageRequest.of(0, 1);  // Fetch only the latest record

        // Determine the prefix and get the last role ID from the respective repository
        switch (role) {
            case ORGANIZATION:
                prefix = "ORG";
                lastRoleIdList = organizationRepository.findUserRoleIdByRole(pageable);
                break;
            case ADMIN:
                prefix = "ADM";
                lastRoleIdList = adminRepository.findUserRoleIdByRole(pageable);
                break;
            case MANAGER:
                prefix = "MAN";
                lastRoleIdList = managerRepository.findUserRoleIdByRole(pageable);
                break;
            case SALE_MANAGER:
                prefix = "SMAN";
                lastRoleIdList = saleManagerRepository.findUserRoleIdByRole(pageable);
                break;
            case TECHNICIAN:
                prefix = "TECH";
                lastRoleIdList = technicianRepository.findUserRoleIdByRole(pageable);
                break;
            case SALES:
                prefix = "SALES";
                lastRoleIdList = salesRepository.findUserRoleIdByRole(pageable);
                break;
            default:
                throw new IllegalArgumentException("Unknown role: " + role);
        }
        // Determine the next ID number (default is 1)
        int nextId = 1;
        if (!lastRoleIdList.isEmpty()) {
            String lastRoleId = lastRoleIdList.get(0);
            String numericPart = lastRoleId.replace(prefix + "-", "");  // Extract the numeric part

            // Safely parse the numeric part
            try {
                nextId = Integer.parseInt(numericPart) + 1;  // Increment the number
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid numeric part in the last role ID: " + lastRoleId);
            }
        }

        // Format and return the new userRoleId with leading zeros (e.g., ADM-001)
        return String.format("%s-%03d", prefix, nextId);
    }




    // reset password

    public  String resetPassword(String email ){

        String newRandomGenPassword = generateRandomPassword();

        String encodedPassword = passwordEncoder.encode(newRandomGenPassword);


        // Organization
        if(organizationRepository.findByEmail(email).isPresent()){
            Organization organization = organizationRepository.findByEmail(email).get();
            organization.setPassword(encodedPassword);
            organizationRepository.save(organization);
            sendEmail(organization.getEmail(),newRandomGenPassword);
            return "Password has been reset and sent to your email!";

        }

        // Admin
        if(adminRepository.findByEmail(email).isPresent()){
            Admin admin = adminRepository.findByEmail(email).get();
            admin.setPassword(encodedPassword);
            adminRepository.save(admin);
            sendEmail(admin.getEmail(),newRandomGenPassword);
            return "Password has been reset and sent to your email!";
        }

        // Reset for Manager
        if (managerRepository.findByEmail(email).isPresent()) {
            Managers manager = managerRepository.findByEmail(email).get();
            manager.setPassword(encodedPassword);
            managerRepository.save(manager);
            sendEmail(manager.getEmail(), newRandomGenPassword);
            return "Password has been reset and sent to your email!";
        }

        // Reset for Sale Manager
        if (saleManagerRepository.findByEmail(email).isPresent()) {
            SaleManager saleManager = saleManagerRepository.findByEmail(email).get();
            saleManager.setPassword(encodedPassword);
            saleManagerRepository.save(saleManager);
            sendEmail(saleManager.getEmail(), newRandomGenPassword);
            return "Password has been reset and sent to your email!";
        }

        // Reset for Technician
        if (technicianRepository.findByEmail(email).isPresent()) {
            Technician technician = technicianRepository.findByEmail(email).get();
            technician.setPassword(encodedPassword);
            technicianRepository.save(technician);
            sendEmail(technician.getEmail(), newRandomGenPassword);
            return "Password has been reset and sent to your email!";
        }

        // Reset for Sales
        if (salesRepository.findByEmail(email).isPresent()) {
            Sales sales = salesRepository.findByEmail(email).get();
            sales.setPassword(encodedPassword);
            salesRepository.save(sales);
            sendEmail(sales.getEmail(), newRandomGenPassword);
            return "Password has been reset and sent to your email!";
        }

        return "Email not found!";

    }


    // send email

    public void sendEmail(String email , String newRandomGenPassword){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset Request");
        message.setText("Your new password is: " + newRandomGenPassword);
        mailSender.send(message);

    }



}
