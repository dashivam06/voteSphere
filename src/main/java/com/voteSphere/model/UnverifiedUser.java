package com.voteSphere.model;

import java.sql.Timestamp;
import java.time.Instant;

public class UnverifiedUser {

	private int unverifiedUserId;
	private String firstName;
	private String lastName;
	private String voterId;
	private String notificationEmail;
	private String profileImage;
	private String phoneNumber;
	private String imageHoldingCitizenship;
	private String voterCardFront;
	private String voterCardBack;
	private String citizenshipFront;
	private String citizenshipBack;
	private String thumbPrint;
	private String password;
	private java.sql.Timestamp dob;
	private String gender;
	private String permanentAddress;
	private String temporaryAddress;
	private String role;
	private Boolean isVerified;
	private boolean isEmailVerified;
	private java.sql.Timestamp createdAt;

	
	public UnverifiedUser(int unverifiedUserId, String firstName, String lastName, String voterId,
			String notificationEmail, String profileImage, String phoneNumber, String imageHoldingCitizenship,
			String voterCardFront, String voterCardBack, String citizenshipFront, String citizenshipBack,
			String thumbPrint, String password, Timestamp dob, String gender, String permanentAddress,
			String temporaryAddress, String role, Boolean isVerified, boolean isEmailVerified, Timestamp createdAt) {
		super();
		this.unverifiedUserId = unverifiedUserId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.voterId = voterId;
		this.notificationEmail = notificationEmail;
		this.profileImage = profileImage;
		this.phoneNumber = phoneNumber;
		this.imageHoldingCitizenship = imageHoldingCitizenship;
		this.voterCardFront = voterCardFront;
		this.voterCardBack = voterCardBack;
		this.citizenshipFront = citizenshipFront;
		this.citizenshipBack = citizenshipBack;
		this.thumbPrint = thumbPrint;
		this.password = password;
		this.dob = dob;
		this.gender = gender;
		this.permanentAddress = permanentAddress;
		this.temporaryAddress = temporaryAddress;
		this.role = role;
		this.isVerified = isVerified;
		this.isEmailVerified = isEmailVerified;
		this.createdAt = createdAt;
	}
	
	
	

	public UnverifiedUser(String firstName, String lastName, String voterId, String notificationEmail,
			String profileImage, String phoneNumber, String imageHoldingCitizenship, String voterCardFront,
			String voterCardBack, String citizenshipFront, String citizenshipBack, String thumbPrint, String password,
			Timestamp dob, String gender, String permanentAddress, String temporaryAddress) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.voterId = voterId;
		this.notificationEmail = notificationEmail;
		this.profileImage = profileImage;
		this.phoneNumber = phoneNumber;
		this.imageHoldingCitizenship = imageHoldingCitizenship;
		this.voterCardFront = voterCardFront;
		this.voterCardBack = voterCardBack;
		this.citizenshipFront = citizenshipFront;
		this.citizenshipBack = citizenshipBack;
		this.thumbPrint = thumbPrint;
		this.password = password;
		this.dob = dob;
		this.gender = gender;
		this.permanentAddress = permanentAddress;
		this.temporaryAddress = temporaryAddress;
		this.role = "voter";
		this.isEmailVerified = false;
		this.isVerified = false;
		this.createdAt = Timestamp.from(Instant.now());
	}

	
	
	public int getUnverifiedUserId() {
		return unverifiedUserId;
	}

	public void setUnverifiedUserId(int unverifiedUserId) {
		this.unverifiedUserId = unverifiedUserId;
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

	public String getVoterId() {
		return voterId;
	}

	public void setVoterId(String voterId) {
		this.voterId = voterId;
	}

	public String getNotificationEmail() {
		return notificationEmail;
	}

	public void setNotificationEmail(String email) {
		this.notificationEmail = email;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	public String getImageHoldingCitizenship() {
		return imageHoldingCitizenship;
	}

	public void setImageHoldingCitizenship(String imageHoldingCitizenship) {
		this.imageHoldingCitizenship = imageHoldingCitizenship;
	}

	public String getVoterCardFront() {
		return voterCardFront;
	}

	public void setVoterCardFront(String voterCardFront) {
		this.voterCardFront = voterCardFront;
	}

	public String getVoterCardBack() {
		return voterCardBack;
	}

	public void setVoterCardBack(String voterCardBack) {
		this.voterCardBack = voterCardBack;
	}

	public String getCitizenshipFront() {
		return citizenshipFront;
	}

	public void setCitizenshipFront(String citizenshipFront) {
		this.citizenshipFront = citizenshipFront;
	}

	public String getCitizenshipBack() {
		return citizenshipBack;
	}

	public void setCitizenshipBack(String citizenshipBack) {
		this.citizenshipBack = citizenshipBack;
	}

	public String getThumbPrint() {
		return thumbPrint;
	}

	public void setThumbPrint(String thumbPrint) {
		this.thumbPrint = thumbPrint;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPermanentAddress() {
		return permanentAddress;
	}

	public void setPermanent(String permanentAddress) {
		this.permanentAddress = permanentAddress;
	}

	public java.sql.Timestamp getDob() {
		return dob;
	}

	public void setDob(java.sql.Timestamp dob) {
		this.dob = dob;
	}

	public String getTemporaryAddress() {
		return temporaryAddress;
	}

	public void setTemporaryAddress(String temporaryAddress) {
		this.temporaryAddress = temporaryAddress;
	}

	public void setPermanentAddress(String permanentAddress) {
		this.permanentAddress = permanentAddress;
	}

	public java.sql.Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(java.sql.Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Boolean getIsVerified() {
		return isVerified;
	}

	public void setIsVerified(Boolean isVerified) {
		this.isVerified = isVerified;
	}

	public boolean isEmailVerified() {
		return isEmailVerified;
	}

	public void setEmailVerified(boolean isEmailVerified) {
		this.isEmailVerified = isEmailVerified;
	}


	
	public UnverifiedUser() {
		super();
	}
	
	
	

	@Override
	public String toString() {
		
		return "UnverifiedUser [unverifiedUserId=" + unverifiedUserId + ", firstName=" + firstName + ", lastName="
				+ lastName + ", voterId=" + voterId + ", notificationEmail=" + notificationEmail + ", profileImage="
				+ profileImage + ", phoneNumber=" + phoneNumber + ", imageHoldingCitizenship=" + imageHoldingCitizenship
				+ ", voterCardFront=" + voterCardFront + ", voterCardBack=" + voterCardBack + ", citizenshipFront="
				+ citizenshipFront + ", citizenshipBack=" + citizenshipBack + ", thumbPrint=" + thumbPrint
				+ ", password=" + password + ", dob=" + dob + ", gender=" + gender + ", permanentAddress="
				+ permanentAddress + ", temporaryAddress=" + temporaryAddress + ", role=" + role + ", isVerified="
				+ isVerified + ", isEmailVerified=" + isEmailVerified + ", createdAt=" + createdAt + "]";
	}

}