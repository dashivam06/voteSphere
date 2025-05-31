package com.voteSphere.dto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import java.io.IOException;
import jakarta.servlet.ServletException;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
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


}
