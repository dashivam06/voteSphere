package com.voteSphere.model;

import com.voteSphere.service.ElectionService;
import com.voteSphere.service.PartyService;
import com.voteSphere.service.UserService;

import java.sql.Timestamp;
import java.time.Instant;


public class Vote {
    private Integer voteId;
    private Integer userId;
    private Integer electionId;
    private Integer partyId;
    private java.sql.Timestamp votedAt;
    private String ip;
    
    public Vote()
    {
    	super();
    }
    
	public Vote(Integer userId, Integer electionId, Integer partyId, Timestamp votedAt, String ip) {
		super();
		this.userId = userId;
		this.electionId = electionId;
		this.partyId = partyId;
		this.votedAt = votedAt;
		this.ip = ip;
	}
	
	
	
	
	public Vote(Integer voteId, Integer userId, Integer electionId, Integer partyId, Timestamp votedAt, String ip) {
		super();
		this.voteId = voteId;
		this.userId = userId;
		this.electionId = electionId;
		this.partyId = partyId;
		this.votedAt = votedAt;
		this.ip = ip;
	}

	public Vote(Integer userId, Integer electionId, Integer partyId, String ip) {
		super();
		this.userId = userId;
		this.electionId = electionId;
		this.partyId = partyId;
		this.ip = ip;
		this.votedAt = Timestamp.from(Instant.now());
	}
	

	public Vote(Integer voteId, Integer userId, Integer electionId, Integer partyId, String ip) {
		super();
		this.voteId = voteId;
		this.userId = userId;
		this.electionId = electionId;
		this.partyId = partyId;
		this.votedAt = Timestamp.from(Instant.now());
		this.ip = ip;
	}


	public String getElectionImageUrl()
	{
		return ElectionService.getElectionById(electionId).getCoverImage();
	}

	public String getElectionName()
	{
		return ElectionService.getElectionById(electionId).getName();
	}


	public String getVoteToken() {
		if (voteId == null || userId == null || electionId == null || partyId == null || votedAt == null || ip == null) {
			throw new IllegalStateException("Cannot generate token: Some required fields are null");
		}

		String raw = voteId + "-" + userId + "-" + electionId + "-" + partyId + "-" + votedAt.getTime() + "-" + ip;

		// Use hashCode and convert to base36 for short alphanumeric token
		int hash = Math.abs(raw.hashCode());
		String encoded = Integer.toString(hash, 36).toUpperCase(); // base36 encoding (digits + letters)

		return "VOTE-" + encoded;
	}


	public String getStatus()
	{
		return "VOTE_CAST";
	}

	public String getPartyName()
	{
		return PartyService.getPartyById(partyId).getName();
	}

	public User getVoter()
	{
		return UserService.getUserById(userId);
	}

	public Integer getVoteId() {
		return voteId;
	}
	public void setVoteId(Integer voteId) {
		this.voteId = voteId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getElectionId() {
		return electionId;
	}
	public void setElectionId(Integer electionId) {
		this.electionId = electionId;
	}
	public Integer getPartyId() {
		return partyId;
	}
	public void setPartyId(Integer partyId) {
		this.partyId = partyId;
	}
	public java.sql.Timestamp getVotedAt() {
		return votedAt;
	}
	public void setVotedAt(java.sql.Timestamp votedAt) {
		this.votedAt = votedAt;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}

    
}
