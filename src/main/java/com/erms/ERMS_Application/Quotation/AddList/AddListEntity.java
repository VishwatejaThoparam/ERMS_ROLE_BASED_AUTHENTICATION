package com.erms.ERMS_Application.Quotation.AddList;

import jakarta.persistence.*;

@Entity
@Table(name="AddList")
public class AddListEntity {
	
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private long alId;
	private String category;
	private String name;
	private Float  price;
	private String gstIn;
	private String stock;
	
	
	public long getAlId() {
		return alId;
	}
	public void setAlId(long alId) {
		this.alId = alId;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public String getGstIn() {
		return gstIn;
	}
	public void setGstIn(String gstIn) {
		this.gstIn = gstIn;
	}
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	
	
	public AddListEntity(long alId, String category, String name, Float price, String gstIn, String stock) {
		super();
		this.alId = alId;
		this.category = category;
		this.name = name;
		this.price = price;
		this.gstIn = gstIn;
		this.stock = stock;
	}
	
	public AddListEntity() {
		super();
	}

}
