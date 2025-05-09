package com.voteSphere.model;

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
