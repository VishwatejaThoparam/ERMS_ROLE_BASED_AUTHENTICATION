package com.erms.ERMS_Application.Quotation.BankDetails;

import jakarta.persistence.*;

@Entity
@Table(name="BankDetails")
public class BankDetailsEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private long   bdId;
	private String accountNumber;
	private String ifsccode;
	private String bankName;
	private String branchname;
	private String accountHolderName;
	
	
	public long getBdId() {
		return bdId;
	}

	public void setBdId(long bdId) {
		this.bdId = bdId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getIfsccode() {
		return ifsccode;
	}

	public void setIfsccode(String ifsccode) {
		this.ifsccode = ifsccode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBranchname() {
		return branchname;
	}

	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}

	public String getAccountHolderName() {
		return accountHolderName;
	}

	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}

	



	public BankDetailsEntity(long bdId, String accountNumber, String ifsccode, String bankName, String branchname,
			String accountHolderName) {
		super();
		this.bdId = bdId;
		this.accountNumber = accountNumber;
		this.ifsccode = ifsccode;
		this.bankName = bankName;
		this.branchname = branchname;
		this.accountHolderName = accountHolderName;
	}

	public BankDetailsEntity() {
		super();
		
	}
	
	
	
	

}
