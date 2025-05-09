package com.voteSphere.esewa;

public class EsewaCallbackData {
    private String transaction_code;
    private String status;
    private String total_amount;
    private String transaction_uuid;
    private String product_code;
    private String signed_field_names;  // Add this field
    private String signature;          // Add this field if present
    
	public String getTransaction_code() {
		return transaction_code;
	}
	public void setTransaction_code(String transaction_code) {
		this.transaction_code = transaction_code;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(String total_amount) {
		this.total_amount = total_amount;
	}
	public String getTransaction_uuid() {
		return transaction_uuid;
	}
	public void setTransaction_uuid(String transaction_uuid) {
		this.transaction_uuid = transaction_uuid;
	}
	public String getProduct_code() {
		return product_code;
	}
	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}
	public String getSigned_field_names() {
		return signed_field_names;
	}
	public void setSigned_field_names(String signed_field_names) {
		this.signed_field_names = signed_field_names;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public EsewaCallbackData(String transaction_code, String status, String total_amount, String transaction_uuid,
			String product_code, String signed_field_names, String signature) {
		super();
		this.transaction_code = transaction_code;
		this.status = status;
		this.total_amount = total_amount;
		this.transaction_uuid = transaction_uuid;
		this.product_code = product_code;
		this.signed_field_names = signed_field_names;
		this.signature = signature;
	}
	public EsewaCallbackData(String status, String total_amount, String transaction_uuid, String product_code,
			String signed_field_names, String signature) {
		super();
		this.status = status;
		this.total_amount = total_amount;
		this.transaction_uuid = transaction_uuid;
		this.product_code = product_code;
		this.signed_field_names = signed_field_names;
		this.signature = signature;
	}
    
   public EsewaCallbackData() {}
@Override
public String toString() {
	return "EsewaCallbackData [transaction_code=" + transaction_code + ", status=" + status + ", total_amount="
			+ total_amount + ", transaction_uuid=" + transaction_uuid + ", product_code=" + product_code
			+ ", signed_field_names=" + signed_field_names + ", signature=" + signature + "]";
}


public boolean isSignatureValid() {
    try {
    	
        String dataToSign = String.format(
            "total_amount=%s,transaction_uuid=%s,product_code=%s",
            EsewaSecurityUtil.truncateToTwoDecimals(this.total_amount), this.transaction_uuid, this.product_code
        );

        System.out.println("In Esewa Callback :"+ dataToSign+"//") ;

        
        String generatedSignature =  EsewaSecurityUtil.generateSignature(dataToSign);
        System.out.print(" Signature :"  +generatedSignature);
        System.out.print(" Received Signature :"  +this.signature);

        System.out.print("Results  : " +generatedSignature.equals(this.signature));
        // Compare with received signature
        return generatedSignature.equals(this.signature);
    } catch (Exception e) {
        throw new RuntimeException("Error verifying signature", e);
    }
}
}