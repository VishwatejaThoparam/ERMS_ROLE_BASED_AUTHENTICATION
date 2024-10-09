package com.erms.ERMS_Application.Quotation.ItemsTable;


import com.erms.ERMS_Application.Quotation.Form.FormEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name="items_table")
public class ItemsTableEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long itId;
	private String items;
	private String hsn;
	private long  qty;
	private float priceItem;
	private float discount;
	private float tax;
	private float amount;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonIgnore
	private FormEntity formEntity;


	public long getItId() {
		return itId;
	}


	public void setItId(long itId) {
		this.itId = itId;
	}


	public String getItems() {
		return items;
	}


	public void setItems(String items) {
		this.items = items;
	}


	public String getHsn() {
		return hsn;
	}


	public void setHsn(String hsn) {
		this.hsn = hsn;
	}


	public long getQty() {
		return qty;
	}


	public void setQty(long qty) {
		this.qty = qty;
	}


	public float getPriceItem() {
		return priceItem;
	}


	public void setPriceItem(float priceItem) {
		this.priceItem = priceItem;
	}


	public float getDiscount() {
		return discount;
	}


	public void setDiscount(float discount) {
		this.discount = discount;
	}


	public float getTax() {
		return tax;
	}


	public void setTax(float tax) {
		this.tax = tax;
	}


	public float getAmount() {
		return amount;
	}


	public void setAmount(float amount) {
		this.amount = amount;
	}


	public FormEntity getFormEntity() {
		return formEntity;
	}


	public void setFormEntity(FormEntity formEntity) {
		this.formEntity = formEntity;
	}


	public ItemsTableEntity(long itId, String items, String hsn, long qty, float priceItem, float discount, float tax,
			float amount, FormEntity formEntity) {
		super();
		this.itId = itId;
		this.items = items;
		this.hsn = hsn;
		this.qty = qty;
		this.priceItem = priceItem;
		this.discount = discount;
		this.tax = tax;
		this.amount = amount;
		this.formEntity = formEntity;
	}


	public ItemsTableEntity() {
		super();
	}

	
	

}
