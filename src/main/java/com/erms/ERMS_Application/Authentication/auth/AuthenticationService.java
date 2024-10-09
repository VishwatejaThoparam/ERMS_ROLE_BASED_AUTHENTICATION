package com.erms.ERMS_Application.Authentication.auth;

import com.erms.ERMS_Application.Authentication.Orgnization.Organization;
import com.erms.ERMS_Application.Authentication.Orgnization.OrganizationRepository;
import com.erms.ERMS_Application.Authentication.admin.Admin;
import com.erms.ERMS_Application.Authentication.admin.AdminRepository;
import com.erms.ERMS_Application.Authentication.config.JwtService;
import com.erms.ERMS_Application.Authentication.managers.ManagerRepository;
import com.erms.ERMS_Application.Authentication.managers.Managers;
import com.erms.ERMS_Application.Authentication.saleManager.SaleManager;
import com.erms.ERMS_Application.Authentication.saleManager.SaleManagerRepository;
import com.erms.ERMS_Application.Authentication.sales.Sales;
import com.erms.ERMS_Application.Authentication.sales.SalesRepository;
import com.erms.ERMS_Application.Authentication.security.CustomUserDetails;
import com.erms.ERMS_Application.Authentication.security.CustomUserDetailsService;
import com.erms.ERMS_Application.Authentication.technician.Technician;
import com.erms.ERMS_Application.Authentication.technician.TechnicianRepository;
import com.erms.ERMS_Application.Authentication.token.Token;
import com.erms.ERMS_Application.Authentication.token.TokenRepository;
import com.erms.ERMS_Application.Authentication.token.TokenType;
import com.erms.ERMS_Application.Authentication.user.Role;
import com.erms.ERMS_Application.Authentication.userUtil.UserUtilService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;



@Service
public class AuthenticationService {

	private final AdminRepository adminRepository;
    private final ManagerRepository managerRepository;
    private final SaleManagerRepository saleManagerRepository;
    private final TechnicianRepository technicianRepository;
    private final SalesRepository salesRepository;
    private final TokenRepository tokenRepository;
    private final OrganizationRepository organizationRepository; // Add this
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    @Autowired
    private UserUtilService userUtilService;

    public AuthenticationService(AdminRepository adminRepository,
                                 ManagerRepository managerRepository,
                                 SaleManagerRepository saleManagerRepository,
                                 TechnicianRepository technicianRepository,
                                 SalesRepository salesRepository,
                                 TokenRepository tokenRepository,
                                 OrganizationRepository organizationRepository, // Add this
                                 PasswordEncoder passwordEncoder,
                                 JwtService jwtService,
                                 AuthenticationManager authenticationManager, CustomUserDetailsService customUserDetailsService) {
        this.adminRepository = adminRepository;
        this.managerRepository = managerRepository;
        this.saleManagerRepository = saleManagerRepository;
        this.technicianRepository = technicianRepository;
        this.salesRepository = salesRepository;
        this.tokenRepository = tokenRepository;
        this.organizationRepository = organizationRepository; // Add this
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
    }

    /////////////////////  Register Method  //////////////////////////


    public AuthenticationResponse registerAdmin(String organizationId, String firstname, String lastname, String email, Role role,String password) {
        // Find the organization by organization ID
        Organization organization = organizationRepository.findByOrganizationId(organizationId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid organization ID"));

        // Check if the email is already in use
        if(organizationRepository.existsByEmail(email)){
            throw new IllegalArgumentException("Email is already use in organization");
        }
        else if (adminRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already use in admin");
        }

        // Create new Admin and set the details
        Admin admin = new Admin();
        admin.setOrganization(organization); // Associate the admin with the organization
        admin.setFirstname(firstname);
        admin.setLastname(lastname);
        admin.setEmail(email);
        admin.setRole(role);
        //            String pass=userUtilService.generateRandomPassword();
        admin.setPassword(passwordEncoder.encode(password));
        admin.setAdminId(userUtilService.generateUserRoleId(role));

        adminRepository.save(admin);

        // Generate JWT response
        return generateJwtResponse(admin);

    }



    public AuthenticationResponse registerManager(String adminId, String firstname, String lastname, String email, Role role, String password) {
        // Verify the admin based on the provided adminId
        Admin admin = adminRepository.findByAdminId(adminId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid admin ID"));

        // Check if the email is already in use
        if(organizationRepository.existsByEmail(email)){
            throw new IllegalArgumentException("Email is already use in organization");
        }
        else if (adminRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already use in admin");
        }
        else if (managerRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already  use in manager");
        }

        // Create and populate the manager entity
        Managers manager = new Managers();
        manager.setAdmin(admin); // Associate manager with the respective admin
        manager.setFirstname(firstname);
        manager.setLastname(lastname);
        manager.setEmail(email);
        manager.setRole(role);
        //            String pass=userUtilService.generateRandomPassword();
        manager.setPassword(passwordEncoder.encode(password));
        manager.setManagersId(userUtilService.generateUserRoleId(role));

        managerRepository.save(manager);

        // Generate and return JWT token for the manager
        return generateJwtResponse(manager);
    }


    public AuthenticationResponse registerSaleManager(String managersId, String firstname, String lastname, String email, Role role, String password) {
        // Verify the manager by managersId from the request
        Managers manager = managerRepository.findByManagersId(managersId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid manager ID"));

        // Check if the email is already in use
        if(organizationRepository.existsByEmail(email)){
            throw new IllegalArgumentException("Email is already use in organization");
        }
        else if (adminRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already use in admin");
        }
        else if (managerRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already  use in manager");
        }
        else if (saleManagerRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already use in saleManager");
        }

        // Create and populate the sale manager entity
        SaleManager saleManager = new SaleManager();
        saleManager.setManager(manager); // Associate the sale manager with the respective manager
        saleManager.setFirstname(firstname);
        saleManager.setLastname(lastname);
        saleManager.setEmail(email);
        saleManager.setRole(role);
//              String pass=userUtilService.generateRandomPassword();
        saleManager.setPassword(passwordEncoder.encode(password));
        saleManager.setSalesManagerId(userUtilService.generateUserRoleId(role));
        saleManagerRepository.save(saleManager);

        // Generate and return JWT token for the sale manager
        return generateJwtResponse(saleManager);
    }




    public AuthenticationResponse registerTechnician(String salesManagerId, String firstname, String lastname, String email, Role role, String password) {
        // Verify the sale manager based on the provided saleManagerId
        SaleManager saleManager = saleManagerRepository.findBySalesManagerId(salesManagerId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid sale manager ID"));

        // Check if the email is already in use
        if(organizationRepository.existsByEmail(email)){
            throw new IllegalArgumentException("Email is already use in organization");
        }
        else if (adminRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already use in admin");
        }
        else if (managerRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already  use in manager");
        }
        else if (saleManagerRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already use in saleManager");
        }
        else if (salesRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already use in sales");
        }
        else if (technicianRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already use technician");
        }

        // Create and populate the technician entity
        Technician technician = new Technician();
        technician.setSaleManager(saleManager); // Associate the technician with the respective sale manager
        technician.setFirstname(firstname);
        technician.setLastname(lastname);
        technician.setEmail(email);
        technician.setRole(role);
//            String pass=userUtilService.generateRandomPassword();

        technician.setPassword(passwordEncoder.encode(password));
        technician.setTechnicianId(userUtilService.generateUserRoleId(role));
        technicianRepository.save(technician);

        // Generate and return JWT token for the technician
        return generateJwtResponse(technician);
    }



    public AuthenticationResponse registerSales(String salesManagerId, String firstname, String lastname, String email, Role role, String password) {
        // Verify the sale manager based on the provided saleManagerId
        SaleManager saleManager = saleManagerRepository.findBySalesManagerId(salesManagerId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid sale manager ID"));

        // Check if the email is already in use
        if(organizationRepository.existsByEmail(email)){
            throw new IllegalArgumentException("Email is already use in organization");
        }
        else if (adminRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already use in admin");
        }
        else if (managerRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already  use in manager");
        }
        else if (saleManagerRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already use in saleManager");
        }
        else if (technicianRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already use in technician ");
        }
        if (salesRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already use in sales");
        }

        // Create and populate the sales entity
        Sales sales = new Sales();
        sales.setSaleManager(saleManager); // Associate the sales with the respective sale manager
        sales.setFirstName(firstname);
        sales.setLastName(lastname);
        sales.setEmail(email);
//            String pass=userUtilService.generateRandomPassword();
        sales.setPassword(passwordEncoder.encode(password));
        sales.setRole(role);
        sales.setSalesId(userUtilService.generateUserRoleId(role));
        salesRepository.save(sales);

        // Generate and return JWT token for the sales
        return generateJwtResponse(sales);

    }



    ///////////////////  Generate JWT Response  ///////////////////////////

    private AuthenticationResponse generateJwtResponse(Object user) {
        UserDetails userDetails;
        Map<String, Object> extraClaims = new HashMap<>(); // Create a map for extra claims

        if (user instanceof Admin) {
            Admin admin = (Admin) user;
            userDetails = new CustomUserDetails(admin);

            // Add claims specific to Admin
            extraClaims.put("firstname", admin.getFirstname());
            extraClaims.put("lastname", admin.getLastname());
            extraClaims.put("role", admin.getRole().name());
            // Extract organization properties instead of the whole object
            if (admin.getOrganization() != null) {
                extraClaims.put("organizationId", admin.getOrganization().getId());
                // If you want to add more organization details, do so here, e.g.:
                // extraClaims.put("organizationName", admin.getOrganization().getName());
            }
            extraClaims.put("adminId", admin.getAdminId());
        } else if (user instanceof Managers) {
            Managers manager = (Managers) user;
            userDetails = new CustomUserDetails(manager);

            // Add claims specific to Manager
            extraClaims.put("firstname", manager.getFirstname());
            extraClaims.put("lastname", manager.getLastname());
            extraClaims.put("role", manager.getRole().name());
            extraClaims.put("adminId",manager.getAdmin().getAdminId());
            extraClaims.put("managerId", manager.getManagersId());
        } else if (user instanceof SaleManager) {
            SaleManager saleManager = (SaleManager) user;
            userDetails = new CustomUserDetails(saleManager);

            // Add claims specific to SaleManager
            extraClaims.put("firstname", saleManager.getFirstname());
            extraClaims.put("lastname", saleManager.getLastname());
            extraClaims.put("role", saleManager.getRole().name());
            extraClaims.put("managerId",saleManager.getManager().getManagersId());
            extraClaims.put("saleManagerId", saleManager.getSalesManagerId());
        } else if (user instanceof Technician) {
            Technician technician = (Technician) user;
            userDetails = new CustomUserDetails(technician);

            // Add claims specific to Technician
            extraClaims.put("firstname", technician.getFirstname());
            extraClaims.put("lastname", technician.getLastname());
            extraClaims.put("role", technician.getRole().name());
            extraClaims.put("saleManagerId",technician.getSaleManager().getSalesManagerId());
            extraClaims.put("technicianId", technician.getTechnicianId());
        } else if (user instanceof Sales) {
            Sales sales = (Sales) user;
            userDetails = new CustomUserDetails(sales);

            // Add claims specific to Sales
            extraClaims.put("firstname", sales.getFirstName());
            extraClaims.put("lastname", sales.getLastName());
            extraClaims.put("role", sales.getRole().name());
            extraClaims.put("mobileNumber", sales.getMobileNumber());
//            extraClaims.put("reportingManager", sales.getReportingManager());
//            extraClaims.put("officialMailId", sales.getOfficialMailId());
            extraClaims.put("saleManagerId",sales.getSaleManager().getSalesManagerId());
            extraClaims.put("salesId", sales.getSalesId());
        } else {
            throw new IllegalArgumentException("Invalid user type");
        }

        // Set the subject claim to the user's email
        extraClaims.put("email", userDetails.getUsername()); // This sets the sub to the email
        // Generate the JWT token with the extra claims
        String jwtToken = jwtService.generateToken(extraClaims, userDetails);
        String refreshToken = jwtService.generateRefreshToken(extraClaims,userDetails);
        saveUserToken(user, jwtToken);
        return new AuthenticationResponse(jwtToken, refreshToken);
    }




    /////////////////// Authenticate  Method /////////////////////////

    public AuthenticationResponse authenticate(String email, String password) {
        // Authenticate user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        // Load user details
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
        Object user = ((CustomUserDetails) userDetails).getUser(); // Assuming you can retrieve the user object

        // Create extra claims map
        Map<String, Object> extraClaims = new HashMap<>();

        // Populate extra claims based on user type
        if (user instanceof Admin) {
            Admin admin = (Admin) user;
            extraClaims.put("firstname", admin.getFirstname());
            extraClaims.put("lastname", admin.getLastname());
            extraClaims.put("role", admin.getRole().name());
            if (admin.getOrganization() != null) {
                extraClaims.put("organizationId", admin.getOrganization().getId());
            }
            extraClaims.put("adminId", admin.getAdminId());
        } else if (user instanceof Managers) {
            Managers manager = (Managers) user;
            extraClaims.put("firstname", manager.getFirstname());
            extraClaims.put("lastname", manager.getLastname());
            extraClaims.put("role", manager.getRole().name());
            extraClaims.put("adminId", manager.getAdmin().getAdminId());
            extraClaims.put("managerId", manager.getManagersId());
        } else if (user instanceof SaleManager) {
            SaleManager saleManager = (SaleManager) user;
            extraClaims.put("firstname", saleManager.getFirstname());
            extraClaims.put("lastname", saleManager.getLastname());
            extraClaims.put("role", saleManager.getRole().name());
            extraClaims.put("managerId", saleManager.getManager().getManagersId());
            extraClaims.put("saleManagerId", saleManager.getSalesManagerId());
        } else if (user instanceof Technician) {
            Technician technician = (Technician) user;
            extraClaims.put("firstname", technician.getFirstname());
            extraClaims.put("lastname", technician.getLastname());
            extraClaims.put("role", technician.getRole().name());
            extraClaims.put("saleManagerId", technician.getSaleManager().getSalesManagerId());
            extraClaims.put("technicianId", technician.getTechnicianId());
        } else if (user instanceof Sales) {
            Sales sales = (Sales) user;
            extraClaims.put("firstname", sales.getLastName());
            extraClaims.put("lastname", sales.getLastName());
            extraClaims.put("role", sales.getRole().name());
            extraClaims.put("saleManagerId", sales.getSaleManager().getSalesManagerId());
            extraClaims.put("salesId", sales.getSalesId());
        } else {
            throw new IllegalArgumentException("Invalid user type");
        }

        // Set the subject claim to the user's email
        extraClaims.put("email", userDetails.getUsername()); // This sets the sub to the email

        // Generate JWT token with extra claims
        String jwtToken = jwtService.generateToken(extraClaims, userDetails);
        String refreshToken = jwtService.generateRefreshToken(extraClaims, userDetails);

        // Revoke existing user tokens and save the new token
        revokeAllUserTokens(userDetails);
        saveUserToken(userDetails, jwtToken);

        // Return response with tokens and extra claims
        return new AuthenticationResponse(jwtToken, refreshToken); // Modify your AuthenticationResponse to accept extra claims
    }


    // Other helper methods like findUserByEmail, saveUserToken, revokeAllUserTokens,
    // refreshToken and generateUserRoleId etc.


    private Object findUserByEmail(String email) {
        Optional<Organization> organization = organizationRepository.findByEmail(email);
        if (organization.isPresent()) return organization.get();

        Optional<Admin> admin = adminRepository.findByEmail(email);
        if (admin.isPresent()) return admin.get();

        Optional<Managers> manager = managerRepository.findByEmail(email);
        if (manager.isPresent()) return manager.get();

        Optional<SaleManager> saleManager = saleManagerRepository.findByEmail(email);
        if (saleManager.isPresent()) return saleManager.get();

        Optional<Technician> technician = technicianRepository.findByEmail(email);
        if (technician.isPresent()) return technician.get();

        Optional<Sales> sales = salesRepository.findByEmail(email);
        if (sales.isPresent()) return sales.get();

        throw new IllegalArgumentException("User not found");
    }

    private void saveUserToken(Object user, String jwtToken) {
        Token token = new Token();
        token.setToken(jwtToken);
        token.setTokenType(TokenType.BEARER);
        token.setExpired(false);
        token.setRevoked(false);
        if (user instanceof CustomUserDetails) {
            token.setUser(((CustomUserDetails) user).getUser());
        }
        tokenRepository.save(token);
    }
    

    private void revokeAllUserTokens(UserDetails userDetails) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokensByUser(getUserId(userDetails));
        if (validUserTokens.isEmpty()) return;

        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }



    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Extract refresh token from request header
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Refresh token is missing or malformed");
        }

        final String refreshToken = authHeader.substring(7);
        String email = jwtService.extractUsername(refreshToken); // Assuming the refresh token contains the username

        if (email != null) {
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
            Object user = ((CustomUserDetails) userDetails).getUser(); // Retrieve user object

            // Validate the refresh token
            if (jwtService.isTokenValid(refreshToken, userDetails)) {
                // Create extra claims map
                Map<String, Object> extraClaims = new HashMap<>();

                // Populate extra claims based on user type
                if (user instanceof Admin) {
                    Admin admin = (Admin) user;
                    extraClaims.put("firstname", admin.getFirstname());
                    extraClaims.put("lastname", admin.getLastname());
                    extraClaims.put("role", admin.getRole().name());
                    if (admin.getOrganization() != null) {
                        extraClaims.put("organizationId", admin.getOrganization().getId());
                    }
                    extraClaims.put("adminId", admin.getAdminId());
                } else if (user instanceof Managers) {
                    Managers manager = (Managers) user;
                    extraClaims.put("firstname", manager.getFirstname());
                    extraClaims.put("lastname", manager.getLastname());
                    extraClaims.put("role", manager.getRole().name());
                    extraClaims.put("adminId", manager.getAdmin().getAdminId());
                    extraClaims.put("managerId", manager.getManagersId());
                } else if (user instanceof SaleManager) {
                    SaleManager saleManager = (SaleManager) user;
                    extraClaims.put("firstname", saleManager.getFirstname());
                    extraClaims.put("lastname", saleManager.getLastname());
                    extraClaims.put("role", saleManager.getRole().name());
                    extraClaims.put("managerId", saleManager.getManager().getManagersId());
                    extraClaims.put("saleManagerId", saleManager.getSalesManagerId());
                } else if (user instanceof Technician) {
                    Technician technician = (Technician) user;
                    extraClaims.put("firstname", technician.getFirstname());
                    extraClaims.put("lastname", technician.getLastname());
                    extraClaims.put("role", technician.getRole().name());
                    extraClaims.put("saleManagerId", technician.getSaleManager().getSalesManagerId());
                    extraClaims.put("technicianId", technician.getTechnicianId());
                } else if (user instanceof Sales) {
                    Sales sales = (Sales) user;
                    extraClaims.put("firstname", sales.getLastName());
                    extraClaims.put("lastname", sales.getLastName());
                    extraClaims.put("role", sales.getRole().name());
                    extraClaims.put("saleManagerId", sales.getSaleManager().getSalesManagerId());
                    extraClaims.put("salesId", sales.getSalesId());
                } else {
                    throw new IllegalArgumentException("Invalid user type");
                }

                // Set the subject claim to the user's email
                extraClaims.put("email", userDetails.getUsername()); // This sets the sub to the email

                // Generate new access token with extra claims
                String newAccessToken = jwtService.generateToken(extraClaims, userDetails);
                String newRefreshToken = jwtService.generateRefreshToken(extraClaims, userDetails);

                // Revoke all previous tokens and save the new tokens
                revokeAllUserTokens(userDetails);
                saveUserToken(userDetails, newAccessToken);

                // Send the new tokens back to the client in the response
                AuthenticationResponse authResponse = new AuthenticationResponse(newAccessToken, newRefreshToken);
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            } else {
                throw new IllegalArgumentException("Invalid refresh token");
            }
        }
    }




    private Integer getUserId(UserDetails userDetails) {
        String email = userDetails.getUsername();
        // Use the repositories to get the user ID based on email
        if (adminRepository.findByEmail(email).isPresent()) {
            return Math.toIntExact(adminRepository.findByEmail(email).get().getId());
        } else if (managerRepository.findByEmail(email).isPresent()) {
            return Math.toIntExact(managerRepository.findByEmail(email).get().getId());
        } else if (saleManagerRepository.findByEmail(email).isPresent()) {
            return Math.toIntExact(saleManagerRepository.findByEmail(email).get().getId());
        } else if (technicianRepository.findByEmail(email).isPresent()) {
            return Math.toIntExact(technicianRepository.findByEmail(email).get().getId());
        } else if (salesRepository.findByEmail(email).isPresent()) {
            return Math.toIntExact(salesRepository.findByEmail(email).get().getId());
        }
        throw new IllegalArgumentException("User not found");
    }



}
