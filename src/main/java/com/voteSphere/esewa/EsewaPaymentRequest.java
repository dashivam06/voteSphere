package com.voteSphere.esewa;
public class EsewaPaymentRequest {
    private String amount;
    private String taxAmount;
    private String productServiceCharge;
    private String productDeliveryCharge;
    private String transactionUuid;
    private String productCode;
    private String successUrl;
    private String failureUrl;
    private String signedFieldNames;
    private String signature;
    private String totalAmount;
    
    // Constructor, getters, and setters
    public EsewaPaymentRequest(String amount) {
        this.amount = String.valueOf(Double.parseDouble(amount));
        this.taxAmount = calculateTax(amount);
        this.productServiceCharge = "0";
        this.productDeliveryCharge = "0";
        this.transactionUuid = generateTransactionId();
        this.productCode = "EPAYTEST";
        this.successUrl = "http://localhost:8080/voteSphere/esewa-callback";
        this.failureUrl = "https://developer.esewa.com.np/failure";
        this.signedFieldNames = "total_amount,transaction_uuid,product_code";
        this.totalAmount = calculateTotalAmount();
        this.signature = generateSignature();
    }
    
    
    
    public String getAmount() {
		return amount;
	}



	public void setAmount(String amount) {
		this.amount = amount;
	}



	public String getTaxAmount() {
		return taxAmount;
	}



	public void setTaxAmount(String taxAmount) {
		this.taxAmount = taxAmount;
	}



	public String getProductServiceCharge() {
		return productServiceCharge;
	}



	public void setProductServiceCharge(String productServiceCharge) {
		this.productServiceCharge = productServiceCharge;
	}



	public String getProductDeliveryCharge() {
		return productDeliveryCharge;
	}



	public void setProductDeliveryCharge(String productDeliveryCharge) {
		this.productDeliveryCharge = productDeliveryCharge;
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



	public String getSuccessUrl() {
		return successUrl;
	}



	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}



	public String getFailureUrl() {
		return failureUrl;
	}



	public void setFailureUrl(String failureUrl) {
		this.failureUrl = failureUrl;
	}



	public String getSignedFieldNames() {
		return signedFieldNames;
	}



	public void setSignedFieldNames(String signedFieldNames) {
		this.signedFieldNames = signedFieldNames;
	}



	public String getSignature() {
		return signature;
	}



	public void setSignature(String signature) {
		this.signature = signature;
	}



	public String getTotalAmount() {
		return totalAmount;
	}



	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}



	private String calculateTax(String amount) {
        // Example: 10% tax
        double tax = Double.parseDouble(amount) * 0.10;
        return String.format("%.2f", tax);
    }
    
    private String calculateTotalAmount() {
        double total = Double.parseDouble(amount) + 
                      Double.parseDouble(taxAmount) + 
                      Double.parseDouble(productServiceCharge) + 
                      Double.parseDouble(productDeliveryCharge);
        return EsewaSecurityUtil.truncateToTwoDecimals(String.valueOf(total));
    }
    
    private String generateTransactionId() {
        return "TX-" + System.currentTimeMillis();
    }
    
    private String generateSignature() {
        String message = String.format("total_amount=%s,transaction_uuid=%s,product_code=%s",
                totalAmount, transactionUuid, productCode);
        System.out.println("In Esewa Payment :"+ message+"//") ;

        return EsewaSecurityUtil.generateSignature(message);
    }



	@Override
	public String toString() {
		return "EsewaPaymentRequest [amount=" + amount + ", taxAmount=" + taxAmount + ", productServiceCharge="
				+ productServiceCharge + ", productDeliveryCharge=" + productDeliveryCharge + ", transactionUuid="
				+ transactionUuid + ", productCode=" + productCode + ", successUrl=" + successUrl + ", failureUrl="
				+ failureUrl + ", signedFieldNames=" + signedFieldNames + ", signature=" + signature + ", totalAmount="
				+ totalAmount + "]";
	}
    
    
    
}