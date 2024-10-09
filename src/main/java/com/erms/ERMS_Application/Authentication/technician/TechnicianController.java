package com.erms.ERMS_Application.Authentication.technician;

import com.erms.ERMS_Application.Authentication.auth.AuthenticationResponse;
import com.erms.ERMS_Application.Authentication.auth.AuthenticationService;
import com.erms.ERMS_Application.Authentication.auth.RegisterRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/technician")
@Tag(name = "Technician")
public class TechnicianController {

    private final AuthenticationService authenticationService;

    public TechnicianController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


//    @PostMapping("/register/technician/{saleManagerId}")
//    public ResponseEntity<AuthenticationResponse> registerTechnician(
//            @PathVariable String saleManagerId,
//            @RequestBody RegisterRequest request) {
//        try {
//            // Call the service to handle technician registration
//            AuthenticationResponse response = authenticationService.registerTechnician(request, saleManagerId);
//            return ResponseEntity.ok(response);
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(new AuthenticationResponse(e.getMessage()));
//        }
//    }

}
