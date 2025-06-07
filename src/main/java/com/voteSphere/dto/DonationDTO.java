package com.voteSphere.dto;

import com.voteSphere.model.Donation;
import com.voteSphere.model.User;

public class DonationDTO {

    private User user;
    private Donation donationEntry;

    public DonationDTO(User user, Donation donationEntry) {
        this.user = user;
        this.donationEntry = donationEntry;
    }

    public Donation getDonationEntry() {
        return donationEntry;
    }

    public void setDonationEntry(Donation donationEntry) {
        this.donationEntry = donationEntry;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
