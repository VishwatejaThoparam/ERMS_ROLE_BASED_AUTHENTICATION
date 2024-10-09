package com.erms.ERMS_Application.Quotation.Form;


import com.erms.ERMS_Application.Quotation.AddParty.AddPartyEntity;
import com.erms.ERMS_Application.Quotation.ItemsTable.ItemsTableEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "form", uniqueConstraints = @UniqueConstraint(columnNames = "quotationNumber"))
public class FormEntity { 
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long fId;
	@Column(nullable = false, unique = true)
	private String quotationNumber;
	private LocalDate quotationDate;
	private	Long paymentTerms;
	private LocalDate	dueDate;
	private String poNo;
	private String lut;
	
	@ManyToOne(fetch = FetchType.EAGER)
   
	private AddPartyEntity addParty;
	
	@OneToMany(mappedBy = "formEntity", fetch = FetchType.EAGER,cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<ItemsTableEntity> itemsTable;

	public long getfId() {
		return fId;
	}

	public void setfId(long fId) {
		this.fId = fId;
	}

	public String getQuotationNumber() {
		return quotationNumber;
	}

	public void setQuotationNumber(String quotationNumber) {
		this.quotationNumber = quotationNumber;
	}

	public LocalDate getQuotationDate() {
		return quotationDate;
	}

	public void setQuotationDate(LocalDate quotationDate) {
		this.quotationDate = quotationDate;
	}

	public Long getPaymentTerms() {
		return paymentTerms;
	}

	public void setPaymentTerms(Long paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public String getPoNo() {
		return poNo;
	}

	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}

	public String getLut() {
		return lut;
	}

	public void setLut(String lut) {
		this.lut = lut;
	}

	public AddPartyEntity getAddParty() {
		return addParty;
	}

	public void setAddParty(AddPartyEntity addParty) {
		this.addParty = addParty;
	}

	public List<ItemsTableEntity> getItemsTable() {
		return itemsTable;
	}

	public void setItemsTable(List<ItemsTableEntity> itemsTable) {
		this.itemsTable = itemsTable;
	}

	public FormEntity(long fId, String quotationNumber, LocalDate quotationDate, Long paymentTerms, LocalDate dueDate,
			String poNo, String lut, AddPartyEntity addParty, List<ItemsTableEntity> itemsTable) {
		super();
		this.fId = fId;
		this.quotationNumber = quotationNumber;
		this.quotationDate = quotationDate;
		this.paymentTerms = paymentTerms;
		this.dueDate = dueDate;
		this.poNo = poNo;
		this.lut = lut;
		this.addParty = addParty;
		this.itemsTable = itemsTable;
	}

	public FormEntity() {
		super();
	}

		
	
	
	
}
