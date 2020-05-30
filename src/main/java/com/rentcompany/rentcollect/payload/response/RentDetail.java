package com.rentcompany.rentcollect.payload.response;

import java.util.Date;

public class RentDetail {

	private Double rentAmount;
	private Date startDate;
	private String propertyName;
	private String propertyDesc;
	private String email;
	private String password;
	private String ownerEmail;
	private String renteeName;
	public Double getRentAmount() {
		return rentAmount;
	}

	public void setRentAmount(Double rentAmount) {
		this.rentAmount = rentAmount;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getPropertyDesc() {
		return propertyDesc;
	}

	public void setPropertyDesc(String propertyDesc) {
		this.propertyDesc = propertyDesc;
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

	public String getOwnerEmail() {
		return ownerEmail;
	}

	public void setOwnerEmail(String ownerEmail) {
		this.ownerEmail = ownerEmail;
	}

	public String getRenteeName() {
		return renteeName;
	}

	public void setRenteeName(String renteeName) {
		this.renteeName = renteeName;
	}
	

	}
