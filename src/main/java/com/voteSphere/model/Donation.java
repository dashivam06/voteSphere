package com.voteSphere.model;

import java.sql.Timestamp;
import java.time.Instant;

public class Donation 
{

	private Integer donationId;
    private Integer userId;
    private double  amount;
    private String productCode;
    private String transactionUuid;
    private String status;
    private java.sql.Timestamp donationTime;
   
    
    
    
	public Donation() { }
	
	



	public Donation(Integer donationId, Integer userId, double amount, String productCode, String transactionUuid,
			String status,  Timestamp donationTime) {
		super();
		this.donationId = donationId;
		this.userId = userId;
		this.amount = amount;
		this.productCode = productCode;
		this.transactionUuid = transactionUuid;
		this.status = status;
		this.donationTime = donationTime;
	}








	public Donation(Integer userId, double amount, String productCode, String transactionUuid, String status) {
		super();
		this.userId = userId;
		this.amount = amount;
		this.productCode = productCode;
		this.transactionUuid = transactionUuid;
		this.status = status;
		this.donationTime = Timestamp.from(Instant.now());
	}








	public Integer getDonationId() {
		return donationId;
	}
	public void setDonationId(Integer donationId) {
		this.donationId = donationId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public java.sql.Timestamp getDonationTime() {
		return donationTime;
	}
	public void setDonationTime(java.sql.Timestamp newdonationTime) {
		this.donationTime = newdonationTime;
	}

	public String getTransactionUuid() {
		return transactionUuid;
	}

	public void setTransactionUuid(String transactionUuid) {
		this.transactionUuid = transactionUuid;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	

	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}

	
    

    
    
}
