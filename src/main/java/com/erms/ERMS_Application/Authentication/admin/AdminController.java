package com.erms.ERMS_Application.Authentication.admin;


import com.erms.ERMS_Application.Authentication.auth.AuthenticationResponse;
import com.erms.ERMS_Application.Authentication.auth.AuthenticationService;
import com.erms.ERMS_Application.Authentication.auth.RegisterRequest;
import com.erms.ERMS_Application.Authentication.user.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final AuthenticationService authenticationService;

    public AdminController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


//    @PostMapping("/register/admin/{organizationId}")
//    public ResponseEntity<AuthenticationResponse> registerAdmin(
//            @PathVariable String organizationId,
//            @RequestBody RegisterRequest request) {
//        try {
//            AuthenticationResponse response = authenticationService.registerAdmin(request, organizationId);
//            return ResponseEntity.ok(response);
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(new AuthenticationResponse(e.getMessage()));
//        }
//    }


    @PostMapping("/register/manager/{adminId}")
    public ResponseEntity<AuthenticationResponse> registerManager(
            @PathVariable String adminId,
            @RequestParam String firstname,@RequestParam String lastname, @RequestParam String email,
            @RequestParam Role role,@RequestParam String password) {
        try {
            // Call the service to handle manager registration
            AuthenticationResponse response = authenticationService.registerManager(adminId,firstname, lastname, email, role,password);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new AuthenticationResponse(e.getMessage()));
        }
    }




}
