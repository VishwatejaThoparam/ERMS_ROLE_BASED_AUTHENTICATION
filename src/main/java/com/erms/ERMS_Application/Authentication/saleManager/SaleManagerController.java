package com.erms.ERMS_Application.Authentication.saleManager;


import com.erms.ERMS_Application.Authentication.auth.AuthenticationResponse;
import com.erms.ERMS_Application.Authentication.auth.AuthenticationService;
import com.erms.ERMS_Application.Authentication.auth.RegisterRequest;
import com.erms.ERMS_Application.Authentication.user.Role;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/saleManagement")
@Tag(name = "SaleManager")
public class SaleManagerController {

    private final AuthenticationService authenticationService;

    public SaleManagerController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


//    @PostMapping("/register/saleManager/{managersId}")
//    public ResponseEntity<AuthenticationResponse> registerSaleManager(
//            @PathVariable String managersId,
//            @RequestBody RegisterRequest request) {
//        try {
//            // Call the service to handle sale manager registration
//            AuthenticationResponse response = authenticationService.registerSaleManager(request, managersId);
//            return ResponseEntity.ok(response);
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(new AuthenticationResponse(e.getMessage()));
//        }
//    }


    @PostMapping("/register/technician/{salesManagerId}")
    public ResponseEntity<AuthenticationResponse> registerTechnician(
            @PathVariable String salesManagerId,
            @RequestParam String firstname,@RequestParam String lastname, @RequestParam String email,
            @RequestParam Role role, @RequestParam String password) {
        try {
            // Call the service to handle technician registration
            AuthenticationResponse response = authenticationService.registerTechnician(salesManagerId,firstname, lastname, email, role, password);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new AuthenticationResponse(e.getMessage()));
        }
    }


    @PostMapping("/register/sales/{salesManagerId}")
    public ResponseEntity<AuthenticationResponse> registerSales(
            @PathVariable String salesManagerId,
            @RequestParam String firstname,@RequestParam String lastname, @RequestParam String email,
            @RequestParam Role role, @RequestParam String password) {
        try {
            // Call the service to handle sales registration
            AuthenticationResponse response = authenticationService.registerSales(salesManagerId,firstname, lastname, email, role,password);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new AuthenticationResponse(e.getMessage()));
        }
    }



}

