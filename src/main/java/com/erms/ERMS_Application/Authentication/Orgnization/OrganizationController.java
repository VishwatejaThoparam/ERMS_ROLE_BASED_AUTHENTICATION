package com.erms.ERMS_Application.Authentication.Orgnization;

import com.erms.ERMS_Application.Authentication.auth.AuthenticationResponse;
import com.erms.ERMS_Application.Authentication.auth.AuthenticationService;
import com.erms.ERMS_Application.Authentication.user.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth/organize")
public class OrganizationController {

    private  final AuthenticationService authenticationService;
    private final OrganizationService organizationService;

    public OrganizationController(AuthenticationService authenticationService, OrganizationService organizationService) {
        this.authenticationService = authenticationService;
        this.organizationService = organizationService;
    }



    @PostMapping("/register/organization")
    public ResponseEntity<Organization> registerOrganization(@RequestParam String name , @RequestParam String email , @RequestParam String  address, @RequestParam Role role, @RequestParam String password) {
        Organization organization = organizationService.registerOrganization(name, email,address, role, password);
        return ResponseEntity.ok(organization);
    }

    @PostMapping("/register/admin/{organizationId}")
    public ResponseEntity<AuthenticationResponse> registerAdmin(
            @PathVariable String organizationId,
            @RequestParam String firstname,@RequestParam String lastname, @RequestParam String email,
            @RequestParam Role role,@RequestParam String password) {
        try {
            AuthenticationResponse response = authenticationService.registerAdmin(organizationId,firstname,lastname,email,role,password);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new AuthenticationResponse(e.getMessage()));
        }
    }




    @PostMapping("/authenticate")
    public ResponseEntity<OrganizationResponse> authenticateOrganization(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam Role role) {
        try {
            // Call the authentication service
            OrganizationResponse response = organizationService.authenticate(email, password, role);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            // Return a bad request with the error message
            return ResponseEntity.badRequest().body(new OrganizationResponse(e.getMessage(), null,null,null,null));
        }
    }



}
