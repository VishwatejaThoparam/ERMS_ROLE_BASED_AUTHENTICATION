package com.erms.ERMS_Application.Authentication.Orgnization;

import com.erms.ERMS_Application.Authentication.user.Role;

public class OrganizationResponse {
    private String message;
    private Role role; // or whatever types you have for other fields
    private String email;
    private String name;
    private String address;
    private String organizationId;

    // Constructor for successful response
    public OrganizationResponse(String email, Role role, String name, String address, String organizationId) {
        this.email = email;
        this.role = role;
        this.name = name;
        this.address = address;
        this.organizationId = organizationId;
        this.message = "Success"; // Optional: Default message
    }

    // Constructor for error response
    public OrganizationResponse(String message) {
        this.message = message;
        this.role = null; // or set a default value
        this.email = null; // or set a default value
        this.name = null; // or set a default value
        this.address = null; // or set a default value
        this.organizationId = null; // or set a default value
    }

    // Getters and setters


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }
}
