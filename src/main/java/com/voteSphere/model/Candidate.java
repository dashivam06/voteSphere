package com.voteSphere.model;

import java.sql.Date;
import  java.util.*;

public class Candidate {
	private Integer candidateId;
	private String fname;
	private String lname;
	private String address;
	private String gender;
	private Date dob;
	private Boolean isIndependent;
	private String highestEducation;
	private String bio;
	private String profileImage;
	private Integer partyId;
	private Integer electionId;
	private String manifesto;

	public Candidate(String fname, String lname, String address, String gender, Date dob, Boolean isIndependent,
			String highestEducation, String bio, String profileImage, Integer partyId, Integer electionId,
			String manifesto) {
		super();
		this.fname = fname;
		this.lname = lname;
		this.address = address;
		this.gender = gender;
		this.dob = dob;
		this.isIndependent = isIndependent;
		this.highestEducation = highestEducation;
		this.bio = bio;
		this.profileImage = profileImage;
		this.partyId = partyId;
		this.electionId = electionId;
		this.manifesto = manifesto;
	}

	public Candidate(Integer candidateId, String fname, String lname, String address, String gender, Date dob,
			Boolean isIndependent, String highestEducation, String bio, String profileImage, Integer partyId,
			Integer electionId, String manifesto) {
		super();
		this.candidateId = candidateId;
		this.fname = fname;
		this.lname = lname;
		this.address = address;
		this.gender = gender;
		this.dob = dob;
		this.isIndependent = isIndependent;
		this.highestEducation = highestEducation;
		this.bio = bio;
		this.profileImage = profileImage;
		this.partyId = partyId;
		this.electionId = electionId;
		this.manifesto = manifesto;
	}

	public Candidate() {
		super();
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getHighestEducation() {
		return highestEducation;
	}

	public void setHighestEducation(String highestEducation) {
		this.highestEducation = highestEducation;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public Integer getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Integer candidateId) {
		this.candidateId = candidateId;
	}

	public Boolean getIsIndependent() {
		return isIndependent;
	}

	public void setIsIndependent(Boolean isIndependent) {
		this.isIndependent = isIndependent;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	public Integer getPartyId() {
		return partyId;
	}

	public void setPartyId(Integer partyId) {
		this.partyId = partyId;
	}

	public Integer getElectionId() {
		return electionId;
	}

	public void setElectionId(Integer electionId) {
		this.electionId = electionId;
	}

	public String getManifesto() {
		return manifesto;
	}

	public void setManifesto(String manifesto) {
		this.manifesto = manifesto;
	}




}
