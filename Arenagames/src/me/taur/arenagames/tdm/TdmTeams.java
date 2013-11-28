package me.taur.arenagames.tdm;

public enum TdmTeams {
	RED(1, "Redd"),
	BLUE(2, "Blue"),
	TIED(0, "Nobody");
	
	private final int id;
	private final String name;
	
	TdmTeams(int id, String name) {
		this.id = id;
		this.name = name;
		
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
}
