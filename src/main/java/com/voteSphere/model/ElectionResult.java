package com.voteSphere.model;


public class ElectionResult  {
    private int candidateId;
    private String candidateName;
    private String partyName;
    private Integer partyId;
    private int voteCount;
    private double percentage;

    // Getters and Setters
    public int getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(int candidateId) {
        this.candidateId = candidateId;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public Integer getPartyId() {
		return partyId;
	}

	public void setPartyId(Integer partyId) {
		this.partyId = partyId;
	}
	
	
	
	
	@Override
	public String toString() {
		return "ElectionResult [candidateId=" + candidateId + ", candidateName=" + candidateName + ", partyName="
				+ partyName + ", partyId=" + partyId + ", voteCount=" + voteCount + ", percentage=" + percentage + "]";
	}

	public void calculatePercentage(int totalVotes) {
        if (totalVotes > 0) {
            this.percentage = (voteCount * 100.0) / totalVotes;
        } else {
            this.percentage = 0;
        }
    }
}