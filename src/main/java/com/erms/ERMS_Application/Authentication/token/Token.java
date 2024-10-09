package com.erms.ERMS_Application.Authentication.token;



import com.erms.ERMS_Application.Authentication.admin.Admin;
import com.erms.ERMS_Application.Authentication.managers.Managers;
import com.erms.ERMS_Application.Authentication.saleManager.SaleManager;
import com.erms.ERMS_Application.Authentication.sales.Sales;
import com.erms.ERMS_Application.Authentication.technician.Technician;
import jakarta.persistence.*;

@Entity
public class Token {

    @Id
    @GeneratedValue
    private Integer id;


    @Column(columnDefinition = "LONGBLOB",unique = true)
    private String token;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType = TokenType.BEARER;

    private boolean revoked;

    private boolean expired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", nullable = true) // Made nullable for flexibility
    private Admin admin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "managers_id", nullable = true) // Made nullable for flexibility
    private Managers managers;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_manager_id", nullable = true) // Made nullable for flexibility
    private SaleManager saleManager;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "technician_id", nullable = true) // Made nullable for flexibility
    private Technician technician;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sales_id", nullable = true) // Made nullable for flexibility
    private Sales sales;

    // Default constructor
    public Token() {}

    // All-args constructor
    public Token(Integer id, String token, TokenType tokenType, boolean revoked, boolean expired,
                 Admin admin, Managers managers, SaleManager saleManager, Technician technician, Sales sales) {
        this.id = id;
        this.token = token;
        this.tokenType = tokenType;
        this.revoked = revoked;
        this.expired = expired;
        this.admin = admin;
        this.managers = managers;
        this.saleManager = saleManager;
        this.technician = technician;
        this.sales = sales;
    }

    // Getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    public boolean isRevoked() {
        return revoked;
    }

    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Managers getManagers() {
        return managers;
    }

    public void setManagers(Managers managers) {
        this.managers = managers;
    }

    public SaleManager getSaleManager() {
        return saleManager;
    }

    public void setSaleManager(SaleManager saleManager) {
        this.saleManager = saleManager;
    }

    public Technician getTechnician() {
        return technician;
    }

    public void setTechnician(Technician technician) {
        this.technician = technician;
    }

    public Sales getSales() {
        return sales;
    }

    public void setSales(Sales sales) {
        this.sales = sales;
    }

    // Updated setUser method
    public void setUser(Object user) {
        if (user instanceof Admin) {
            this.admin = (Admin) user;
        } else if (user instanceof Managers) {
            this.managers = (Managers) user;
        } else if (user instanceof SaleManager) {
            this.saleManager = (SaleManager) user;
        } else if (user instanceof Technician) {
            this.technician = (Technician) user;
        } else if (user instanceof Sales) {
            this.sales = (Sales) user;
        } else {
            throw new IllegalArgumentException("Unsupported user type");
        }
    }
}
