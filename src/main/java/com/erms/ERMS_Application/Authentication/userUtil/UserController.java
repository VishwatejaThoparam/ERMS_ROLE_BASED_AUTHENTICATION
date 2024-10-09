package com.erms.ERMS_Application.Authentication.userUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserUtilService userUtilService;

    // Endpoint to generate a random password for a user
    @GetMapping("/generate-password")
    public ResponseEntity<String> generatePassword() {
        String randomPassword = userUtilService.generateRandomPassword();
        return ResponseEntity.ok(randomPassword);
    }

    // Endpoint to change the password for a given user
    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestParam String email,
                                                 @RequestParam String oldPassword,
                                                 @RequestParam String newPassword) {
        try {
            String responseMessage = userUtilService.changePassword(email, oldPassword, newPassword);
            return ResponseEntity.ok(responseMessage);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    // Endpoint to verify the old password before changing
    @PostMapping("/verify-password")
    public ResponseEntity<String> verifyPassword(
            @RequestParam String email,
            @RequestParam String oldPassword) {
        boolean isValid = userUtilService.verifyOldPassword(email, oldPassword);
        if (isValid) {
            return ResponseEntity.ok("Old password is valid.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Old password is invalid.");
        }
    }


    @PutMapping("/reset-password/{email}")
    public ResponseEntity<String> resetPassword(@PathVariable String email) {
        String responseMessage = userUtilService.resetPassword(email);
        if (responseMessage.equals("Email not found!")) {
            return new ResponseEntity<>(responseMessage, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }




}
