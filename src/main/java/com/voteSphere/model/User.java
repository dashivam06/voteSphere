package com.voteSphere.model;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Calendar;
import java.text.DateFormatSymbols;

import java.util.Random;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;

public class User 
{

	private Integer userId;
    private String firstName;
    private String lastName;
    private String voterId;
    private String email;
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
    private String gender ;
    private String permanentAddress;
    private String temporaryAddress;
    private String role;
    private Boolean isVerified;
    private boolean isEmailVerified;
    private java.sql.Timestamp createdAt;
    
    
    public User()
    {
    	super();
    	this.role = "voter";
    	this.createdAt = Timestamp.from(Instant.now());
    	this.isVerified = true;
    }
    
	public User(String firstName, String lastName, String voterId, String email, String phoneNumber, String profileImage,
			String imageHoldingCitizenship, String voterCardFront, String voterCardBack, String citizenshipFront,
			String citizenshipBack, String thumbPrint, String password, Timestamp dob, String permanentAddress, String temporaryAddress, String role,
			Boolean isVerified, Boolean isEmailVerified) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.voterId = voterId;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.profileImage = profileImage;
		this.imageHoldingCitizenship = imageHoldingCitizenship;
		this.voterCardFront = voterCardFront;
		this.voterCardBack = voterCardBack;
		this.citizenshipFront = citizenshipFront;
		this.citizenshipBack = citizenshipBack;
		this.thumbPrint = thumbPrint;
		this.password = password;
		this.dob = dob;
		this.permanentAddress = permanentAddress;
		this.temporaryAddress = temporaryAddress;
		this.role = role;
		this.isVerified = isVerified;
		this.createdAt = Timestamp.from(Instant.now());
	}
	
	



	public User(Integer userId, String firstName, String lastName, String voterId, String email, String phoneNumber, String profileImage,
			String imageHoldingCitizenship, String voterCardFront, String voterCardBack, String citizenshipFront,
			String citizenshipBack, String thumbPrint, String password, Timestamp dob, String permanent,String temporaryAddress, String role,
			Boolean isVerified,  Timestamp createdAt) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.voterId = voterId;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.profileImage = profileImage;
		this.imageHoldingCitizenship = imageHoldingCitizenship;
		this.voterCardFront = voterCardFront;
		this.voterCardBack = voterCardBack;
		this.citizenshipFront = citizenshipFront;
		this.citizenshipBack = citizenshipBack;
		this.thumbPrint = thumbPrint;
		this.password = password;
		this.dob = dob;
		this.permanentAddress = permanent;
		this.temporaryAddress = temporaryAddress;
		this.role = role;
		this.isVerified = isVerified;
		this.createdAt = createdAt;
	}

	public String getPermanentAddress() {
		return permanentAddress;
	}
	public void setPermanentAddress(String permanentAddress) {
		this.permanentAddress = permanentAddress;
	}



	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public java.sql.Timestamp getDob() {
		return dob;
	}
	public void setDob(java.sql.Timestamp dob) {
		this.dob = dob;
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

	public String getTemporaryAddress() {
		return temporaryAddress;
	}

	public void setTemporaryAddress(String temporaryAddress) {
		this.temporaryAddress = temporaryAddress;
	}

	public boolean isEmailVerified() {
		return isEmailVerified;
	}

	public void setEmailVerified(boolean isEmailVerified) {
		this.isEmailVerified = isEmailVerified;
	}

	public Integer getAge() {
		if (dob == null) return null;

		LocalDate birthDate = dob.toLocalDateTime().toLocalDate();
		LocalDate today = LocalDate.now();

		return Period.between(birthDate, today).getYears();
	}


	@Override
	public String toString() {
		return "User{" +
				"userId=" + userId +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", voterId='" + voterId + '\'' +
				", email='" + email + '\'' +
				", profileImage='" + profileImage + '\'' +
				", phoneNumber='" + phoneNumber + '\'' +
				", imageHoldingCitizenship='" + imageHoldingCitizenship + '\'' +
				", voterCardFront='" + voterCardFront + '\'' +
				", voterCardBack='" + voterCardBack + '\'' +
				", citizenshipFront='" + citizenshipFront + '\'' +
				", citizenshipBack='" + citizenshipBack + '\'' +
				", thumbPrint='" + thumbPrint + '\'' +
				", password='" + password + '\'' +
				", dob=" + dob +
				", gender='" + gender + '\'' +
				", permanentAddress='" + permanentAddress + '\'' +
				", temporaryAddress='" + temporaryAddress + '\'' +
				", role='" + role + '\'' +
				", isVerified=" + isVerified +
				", isEmailVerified=" + isEmailVerified +
				", createdAt=" + createdAt +
				'}';
	}

	public static List<User> searchUsers(List<User> allUsers, String keyword) {
		if (keyword == null || keyword.trim().isEmpty()) {
			return allUsers;
		}

		String[] words = keyword.toLowerCase().trim().split("\\s+");

		return allUsers.stream()
				.filter(user -> {
					// 1) Build the base searchable string
					String data = (
							user.getFirstName()        + " " +
									user.getLastName()         + " " +
									user.getVoterId()          + " " +
									user.getEmail()            + " " +
									user.getPhoneNumber()      + " " +
									user.getPermanentAddress() + " " +
									user.getTemporaryAddress() + " " +
									user.getGender()
					).toLowerCase();

					// 2) If createdAt exists, extract month variants
					Timestamp ts = user.getCreatedAt();
					if (ts != null) {
						Calendar cal = Calendar.getInstance();
						cal.setTimeInMillis(ts.getTime());
						int m = cal.get(Calendar.MONTH);    // 0–11
						int monthNum = m + 1;               // 1–12
						String monthNumStr       = String.valueOf(monthNum);
						String monthNumPadded    = String.format("%02d", monthNum);
						DateFormatSymbols dfs    = new DateFormatSymbols();
						String monthFullName     = dfs.getMonths()[m].toLowerCase();      // e.g. "may"
						String monthShortName    = dfs.getShortMonths()[m].toLowerCase(); // e.g. "may" or "jan"

						data += " "
								+ monthNumStr       + " "
								+ monthNumPadded    + " "
								+ monthFullName     + " "
								+ monthShortName;
					}

					// 3) Ensure every search word is present
					for (String w : words) {
						if (!data.contains(w)) {
							return false;
						}
					}
					return true;
				})
				.collect(Collectors.toList());
	}

}
