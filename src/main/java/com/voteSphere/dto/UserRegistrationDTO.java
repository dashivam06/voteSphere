package com.voteSphere.dto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import java.io.IOException;
import jakarta.servlet.ServletException;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


public class UserRegistrationDTO {

    // Simple form fields
    private String firstName;
    private String lastName;
    private String voterId;
    private String email;
    private String phoneNumber;
    private String password;
    private String confirmPassword;
    private String dob;
    private String gender;
    private String permanentAddress;
    private String temporaryAddress;

    // File upload parts
    private Part profileImage;
    private Part imageHoldingCitizenship;
    private Part voterCardFront;
    private Part citizenshipFront;
    private Part citizenshipBack;
    private Part thumbPrint;

    // Constructor that extracts from HttpServletRequest
    public UserRegistrationDTO(HttpServletRequest request) throws IOException, ServletException {
        // Extract simple parameters
        this.firstName = request.getParameter("first_name");
        this.lastName = request.getParameter("last_name");
        this.voterId = request.getParameter("voter_id");
        this.email = request.getParameter("notification_email");
        this.phoneNumber = request.getParameter("phone_number");
        this.password = request.getParameter("password");
        this.confirmPassword = request.getParameter("confirm_password");
        this.dob = request.getParameter("dob");
        this.gender = request.getParameter("gender");
        this.permanentAddress = request.getParameter("permanent_address");
        this.temporaryAddress = request.getParameter("temporary_address");

        // Extract Parts (file uploads)
        this.profileImage = request.getPart("profile_image");
        this.imageHoldingCitizenship = request.getPart("image_holding_citizenship");
        this.voterCardFront = request.getPart("voter_card_front");
        this.citizenshipFront = request.getPart("citizenship_front");
        this.citizenshipBack = request.getPart("citizenship_back");
        this.thumbPrint = request.getPart("thumb_print");
    }

    public String getVoterId() {
        return voterId;
    }

    public void setVoterId(String voterId) {
        this.voterId = voterId;
    }

    public Part getVoterCardFront() {
        return voterCardFront;
    }

    public void setVoterCardFront(Part voterCardFront) {
        this.voterCardFront = voterCardFront;
    }

    public Part getThumbPrint() {
        return thumbPrint;
    }

    public void setThumbPrint(Part thumbPrint) {
        this.thumbPrint = thumbPrint;
    }

    public String getTemporaryAddress() {
        return temporaryAddress;
    }

    public void setTemporaryAddress(String temporaryAddress) {
        this.temporaryAddress = temporaryAddress;
    }

    public Part getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(Part profileImage) {
        this.profileImage = profileImage;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Part getImageHoldingCitizenship() {
        return imageHoldingCitizenship;
    }

    public void setImageHoldingCitizenship(Part imageHoldingCitizenship) {
        this.imageHoldingCitizenship = imageHoldingCitizenship;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Part getCitizenshipFront() {
        return citizenshipFront;
    }

    public void setCitizenshipFront(Part citizenshipFront) {
        this.citizenshipFront = citizenshipFront;
    }

    public Part getCitizenshipBack() {
        return citizenshipBack;
    }

    public void setCitizenshipBack(Part citizenshipBack) {
        this.citizenshipBack = citizenshipBack;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
