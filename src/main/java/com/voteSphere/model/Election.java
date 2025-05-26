package com.voteSphere.model;

import com.voteSphere.dao.UserDao;
import com.voteSphere.service.UserService;
import com.voteSphere.service.VoteService;
import com.voteSphere.util.SessionUtil;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class Election {
    private Integer electionId;
    private String name;
    private String type;
    private String coverImage;
    private java.sql.Date date;
    private java.sql.Time startTime;
    private java.sql.Time endTime;
    
    
    
    
	public Election(String name, String type, String coverImage, Date date, Time startTime, Time endTime) {
		super();
		this.name = name;
		this.type = type;
		this.coverImage = coverImage;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	
	public Election(Integer electionId, String name, String type, String coverImage, Date date, Time startTime,
			Time endTime) {
		super();
		this.electionId = electionId;
		this.name = name;
		this.type = type;
		this.coverImage = coverImage;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public Election() { }


	
	public Integer getId(){return electionId;}
	public String getImageUrl(){return coverImage;}
	public Integer getElectionId() {
		return electionId;
	}
	public void setElectionId(Integer electionId) {
		this.electionId = electionId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCoverImage() {
		return coverImage;
	}
	public void setCoverImage(String coverImage) {
		this.coverImage = coverImage;
	}
	public java.sql.Date getDate() {
		return date;
	}
	public void setDate(java.sql.Date date) {
		this.date = date;
	}
	public java.sql.Time getStartTime() {
		return startTime;
	}
	public void setStartTime(java.sql.Time startTime) {
		this.startTime = startTime;
	}
	public java.sql.Time getEndTime() {
		return endTime;
	}
	public void setEndTime(java.sql.Time endTime) {
		this.endTime = endTime;
	}

	public Integer getEligibleVoters()
	{
		return UserService.getAllUsers().size();
	}


	public String getFormattedEndDateTime() {
		if (date != null && endTime != null) {
			LocalDateTime dateTime = LocalDateTime.of(
					date.toLocalDate(),
					endTime.toLocalTime()
			);
			return dateTime.toString(); // returns ISO format like "2025-05-22T18:00"
		}
		return "";
	}

	@Override
	public String toString() {
		return "Election [electionId=" + electionId + ", name=" + name + ", type=" + type + ", coverImage=" + coverImage
				+ ", date=" + date + ", startTime=" + startTime + ", endTime=" + endTime + "]";
	}


	public String getStatus() {
		LocalDate today = LocalDate.now();
		LocalTime now = LocalTime.now();

		if (date.toLocalDate().isAfter(today)) {
			return "Upcoming";
		} else if (date.toLocalDate().isBefore(today)) {
			return "Past";
		} else {
			// Election is today
			if (now.isBefore(startTime.toLocalTime())) {
				return "Upcoming";
			} else if (now.isAfter(endTime.toLocalTime())) {
				return "Past";
			} else {
				return "Ongoing";
			}
		}
	}

	public static List<Election> searchElections(List<Election> allElections, String keyword) {
		if (keyword == null || keyword.trim().isEmpty()) {
			return allElections;
		}

		String[] words = keyword.toLowerCase().trim().split("\\s+");

		return allElections.stream()
				.filter(election -> {
					String data = (
							(election.getName() != null ? election.getName() : "") + " " +
									(election.getType() != null ? election.getType() : "")
					).toLowerCase();

					for (String word : words) {
						if (!data.contains(word)) {
							return false;
						}
					}
					return true;
				})
				.toList();
	}

	public String getElectionToken() {
		if (electionId == null || name == null || date == null) {
			throw new IllegalStateException("Cannot generate token: Some required fields are null");
		}

		// Compose a raw string with key fields, including date's timestamp
		String raw = electionId + "-" + name + "-" + date.getTime();

		// Generate hash code, then convert to base36 (alphanumeric) for shorter token
		int hash = Math.abs(raw.hashCode());
		String encoded = Integer.toString(hash, 36).toUpperCase();

		return "ELEC-" + encoded;
	}


}
