package com.erms.ERMS_Application.Authentication.managers;


import com.erms.ERMS_Application.Authentication.auth.AuthenticationResponse;
import com.erms.ERMS_Application.Authentication.auth.AuthenticationService;
import com.erms.ERMS_Application.Authentication.auth.RegisterRequest;
import com.erms.ERMS_Application.Authentication.user.Role;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/management")
@Tag(name = "Management")
public class ManagementController {


    private final AuthenticationService authenticationService;

    public ManagementController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }



//    @PostMapping("/register/manager/{adminId}")
//    public ResponseEntity<AuthenticationResponse> registerManager(
//            @PathVariable String adminId,
//            @RequestBody RegisterRequest request) {
//        try {
//            // Call the service to handle manager registration
//            AuthenticationResponse response = authenticationService.registerManager(request, adminId);
//            return ResponseEntity.ok(response);
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(new AuthenticationResponse(e.getMessage()));
//        }
//    }

    @PostMapping("/register/saleManager/{managersId}")
    public ResponseEntity<AuthenticationResponse> registerSaleManager(
            @PathVariable String managersId,
            @RequestParam String firstname,@RequestParam String lastname, @RequestParam String email,
            @RequestParam Role role, @RequestParam String password) {
        try {
            // Call the service to handle sale manager registration
            AuthenticationResponse response = authenticationService.registerSaleManager(managersId,firstname, lastname, email, role, password);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new AuthenticationResponse(e.getMessage()));
        }
    }

}