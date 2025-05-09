package com.voteSphere.model;

import java.util.List;
import java.util.Comparator;

public class Party 
{
	
	    private Integer partyId;
	    private String name;
	    private String leaderName;
	    private String founderName;
	    private String symbolImage;
	    private String coverImage;
	    private String description;
	    
	    
	    
		public Party(String name, String leaderName, String founderName, String symbolImage, String coverImage,
				String description) {
			super();
			this.name = name;
			this.leaderName = leaderName;
			this.founderName = founderName;
			this.symbolImage = symbolImage;
			this.coverImage = coverImage;
			this.description = description;
		}
		
		
		public Party(Integer partyId, String name, String leaderName, String founderName, String symbolImage,
				String coverImage, String description) {
			super();
			this.partyId = partyId;
			this.name = name;
			this.leaderName = leaderName;
			this.founderName = founderName;
			this.symbolImage = symbolImage;
			this.coverImage = coverImage;
			this.description = description;
		}


		public Integer getPartyId() {
			return partyId;
		}
		public void setPartyId(Integer partyId) {
			this.partyId = partyId;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getLeaderName() {
			return leaderName;
		}
		public void setLeaderName(String leaderName) {
			this.leaderName = leaderName;
		}
		public String getFounderName() {
			return founderName;
		}
		public void setFounderName(String founderName) {
			this.founderName = founderName;
		}
		public String getSymbolImage() {
			return symbolImage;
		}
		public void setSymbolImage(String symbolImage) {
			this.symbolImage = symbolImage;
		}
		public String getCoverImage() {
			return coverImage;
		}
		public void setCoverImage(String coverImage) {
			this.coverImage = coverImage;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}


	@Override
	public String toString() {
		return "Party{" +
				"partyId=" + partyId +
				", name='" + name + '\'' +
				", leaderName='" + leaderName + '\'' +
				", founderName='" + founderName + '\'' +
				", symbolImage='" + symbolImage + '\'' +
				", coverImage='" + coverImage + '\'' +
				", description='" + description + '\'' +
				'}';
	}



	public static Comparator<Party> byNameAsc = Comparator.comparing(Party::getName, String.CASE_INSENSITIVE_ORDER);

	public static Comparator<Party> byLeaderNameAsc = Comparator.comparing(Party::getLeaderName, String.CASE_INSENSITIVE_ORDER);

	public static Comparator<Party> byFounderNameAsc = Comparator.comparing(Party::getFounderName, String.CASE_INSENSITIVE_ORDER);


	public static List<Party> searchParties(List<Party> allParties,  String keyword) {
		if (keyword == null || keyword.isBlank()) return allParties;

		String finalKeyword =  keyword.toLowerCase();
		System.out.println("Final : " + finalKeyword);

		return allParties.stream()
				.filter(p ->
						(p.getName() != null && p.getName().toLowerCase().contains(finalKeyword)) ||
								(p.getLeaderName() != null && p.getLeaderName().toLowerCase().contains(finalKeyword)) ||
								(p.getFounderName() != null && p.getFounderName().toLowerCase().contains(finalKeyword))
				)
				.toList();
	}



}
