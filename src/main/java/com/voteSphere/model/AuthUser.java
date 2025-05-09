package com.voteSphere.model;

import com.voteSphere.util.ValidationUtil;

public class AuthUser {

	private final Integer userId;
	private final String lname;
	private final String fname;
	private final String roles;
	private final String profileImage ;

	public AuthUser(Integer userId, String lname, String fname, String roles, String profileImage) {
		super();
		this.userId = userId;
		this.lname = lname;
		this.fname = fname;
		this.roles = roles;
		this.profileImage = profileImage;
	}
	
	
	public AuthUser(User user) {
		this.userId = user.getUserId();
		this.lname = user.getLastName();
		this.fname = user.getFirstName();
		this.profileImage = user.getProfileImage();
		this.roles = user.getRole();
		
	}




	public Integer getUserId() {
		return userId;
	}

	public String getLname() {
		return lname;
	}

	

	public String getRole() {
		return roles;
	}

	public String getRoles() {
		return roles;
	}



	public String getProfileImage() {
		return profileImage;
	}



	public String getFname() {
		return fname;
	}

	public static boolean validateAuthUser(AuthUser authUser) {
	    if (authUser == null) {
	        return false;
	    }

	  
	    boolean areFieldsValid = !ValidationUtil.isNullOrEmpty(
	        authUser.getFname(), 
	        authUser.getLname(), 
	        authUser.getRole(), 
	        authUser.getProfileImage()
	    );

	    return  areFieldsValid;
	}


}
