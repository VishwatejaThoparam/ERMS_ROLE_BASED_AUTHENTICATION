package com.erms.ERMS_Application.Quotation.Salesman;

import com.erms.ERMS_Application.Quotation.AddParty.AddPartyEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="Sale")
public class SaleEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long sId;
	
	private String firstName;
    private String lastName;
    private long mobileNumber;
    private String emailId;
    private String role;
    private String manager;
    private String reportingManager;
    private String officialMailId;
    
    @OneToMany(mappedBy = "saleEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    
    private List<AddPartyEntity> addParty;

    
	public long getsId() {
		return sId;
	}

	public void setsId(long sId) {
		this.sId = sId;
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

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getReportingManager() {
		return reportingManager;
	}

	public void setReportingManager(String reportingManager) {
		this.reportingManager = reportingManager;
	}

	public String getOfficialMailId() {
		return officialMailId;
	}

	public void setOfficialMailId(String officialMailId) {
		this.officialMailId = officialMailId;
	}

	public List<AddPartyEntity> getAddParty() {
		return addParty;
	}



	public void setAddParty(List<AddPartyEntity> addParty) {
		this.addParty = addParty;
	}

	

	public SaleEntity(long sId, String firstName, String lastName, long mobileNumber, String emailId, String role,
			String manager, String reportingManager, String officialMailId, List<AddPartyEntity> addParty) {
		super();
		this.sId = sId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mobileNumber = mobileNumber;
		this.emailId = emailId;
		this.role = role;
		this.manager = manager;
		this.reportingManager = reportingManager;
		this.officialMailId = officialMailId;
		this.addParty = addParty;
	}



	public SaleEntity() {
		super();
		
	}
 
}
