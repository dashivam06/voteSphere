package com.voteSphere.model;

import java.sql.Timestamp;
import java.time.Instant;

import com.voteSphere.util.ClientInfoUtil;

import jakarta.servlet.http.HttpServletRequest;

public class Token {
	
	private Integer token_id;
	private Integer userID;
	private String token;
    private Timestamp createdAt;
    private String type;
    private String ipAddress;
    private String device;
    private boolean isUsed;
    
   

	public Token(Integer userID, String token,  String type, String ipAddress, String device,
			boolean isUsed) {
		super();
		this.userID = userID;
		this.token = token;
		this.createdAt = Timestamp.from(Instant.now());
		this.type = type;
		this.ipAddress = ipAddress;
		this.device = device;
		this.isUsed = isUsed;
	}






	public Token(Integer token_id, Integer userID, String token, Timestamp createdAt, String type, String ipAddress,
			String device, boolean isUsed) {
		super();
		this.token_id = token_id;
		this.userID = userID;
		this.token = token;
		this.createdAt = createdAt;
		this.type = type;
		this.ipAddress = ipAddress;
		this.device = device;
		this.isUsed = isUsed;
	}

	public Token() {
		super();
	}



	public Integer getToken_id() {
		return token_id;
	}



	public void setToken_id(Integer token_id) {
		this.token_id = token_id;
	}



	public boolean isUsed() {
		return isUsed;
	}



	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}



	public Integer getUserID() {
		return userID;
	}


	public void setUserID(Integer userID) {
		this.userID = userID;
	}


	public Token(HttpServletRequest request, Integer userID, String token, String type ) {
		super();
		this.userID = userID;
		this.token = token;
		this.type = type;
		this.ipAddress = ClientInfoUtil.getClientIpAddress(request);
		this.createdAt = Timestamp.from(Instant.now());
		this.device = ClientInfoUtil.getDeviceType(request);
		this.isUsed = false;
		
	}


	public Integer getTokenId() {
		return token_id;
	}
	
	
	public void setTokenId(Integer token_id) {
		this.token_id = token_id;
	}


	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	


	@Override
	public String toString() {
		return "Token [token_id=" + token_id + ", userID=" + userID + ", token=" + token + ", createdAt=" + createdAt
				+ ", type=" + type + ", ipAddress=" + ipAddress + ", device=" + device + "]";
	}
    

}
