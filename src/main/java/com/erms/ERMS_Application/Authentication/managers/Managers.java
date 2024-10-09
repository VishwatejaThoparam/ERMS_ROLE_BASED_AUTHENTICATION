package com.erms.ERMS_Application.Authentication.managers;


import com.erms.ERMS_Application.Authentication.admin.Admin;
import com.erms.ERMS_Application.Authentication.saleManager.SaleManager;
import com.erms.ERMS_Application.Authentication.user.Role;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "manager")
public class Managers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;
    private String lastname;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;  // ADMIN, MANAGER, SALE_MANAGER


    private String managersId;  // e.g., MAN-001

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", nullable = false)  // Foreign key to Admin
    private Admin admin;

    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<SaleManager> saleManagers;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getManagersId() {
        return managersId;
    }

    public void setManagersId(String managersId) {
        this.managersId = managersId;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public List<SaleManager> getSaleManagers() {
        return saleManagers;
    }

    public void setSaleManagers(List<SaleManager> saleManagers) {
        this.saleManagers = saleManagers;
    }
}

