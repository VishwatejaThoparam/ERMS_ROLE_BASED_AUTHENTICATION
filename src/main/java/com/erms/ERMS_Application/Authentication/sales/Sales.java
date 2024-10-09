package com.erms.ERMS_Application.Authentication.sales;


import com.erms.ERMS_Application.Authentication.saleManager.SaleManager;
import com.erms.ERMS_Application.Authentication.user.Role;
import jakarta.persistence.*;

@Entity
@Table(name = "sales")
public class Sales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    private String firstname;
//    private String lastname;
//    private String password;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private long mobileNumber;

//    private String manager;
//    private String reportingManager;
//    private String officialMailId;

    @Enumerated(EnumType.STRING)
    private Role role;  // SALES, other roles if applicable

    @Column(nullable = false, unique = true)
    private String salesId;  // e.g., SALES-001

    @ManyToOne(fetch = FetchType.LAZY)  // Lazy fetch to improve performance
    @JoinColumn(name = "salesManager_id", nullable = false)  // Foreign key constraint
    private SaleManager saleManager;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }






    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getSalesId() {
        return salesId;
    }

    public void setSalesId(String salesId) {
        this.salesId = salesId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public SaleManager getSaleManager() {
        return saleManager;
    }

    public void setSaleManager(SaleManager saleManager) {
        this.saleManager = saleManager;
    }
}
