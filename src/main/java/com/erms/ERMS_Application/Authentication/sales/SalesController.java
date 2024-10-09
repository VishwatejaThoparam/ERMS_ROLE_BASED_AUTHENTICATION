package com.erms.ERMS_Application.Authentication.sales;

import com.erms.ERMS_Application.Authentication.auth.AuthenticationResponse;
import com.erms.ERMS_Application.Authentication.auth.AuthenticationService;
import com.erms.ERMS_Application.Authentication.auth.RegisterRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sales")
@Tag(name = "Sales")
public class SalesController {

    private final AuthenticationService authenticationService;

    public SalesController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


//    @PostMapping("/register/sales/{saleManagerId}")
//    public ResponseEntity<AuthenticationResponse> registerSales(
//            @PathVariable String saleManagerId,
//            @RequestBody RegisterRequest request) {
//        try {
//            // Call the service to handle sales registration
//            AuthenticationResponse response = authenticationService.registerSales(request, saleManagerId);
//            return ResponseEntity.ok(response);
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(new AuthenticationResponse(e.getMessage()));
//        }
//    }
}

