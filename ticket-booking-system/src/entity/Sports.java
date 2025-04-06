package entity;

public class Sports extends Event {
	private String sport_name;
	private String sport_team;
	
	//default constructor
	public Sports() {
		
	}
	
	//parameterized constructor
	public Sports(String sport_name, String sport_team) {
		this.sport_name = sport_name;
		this.sport_team = sport_team;
	}
	
	//getter and setter methods
	public String getSportName() {
		return sport_name;
	}
	public void setSportName(String sport_name) {
		this.sport_name = sport_name;
	}
	
	public String getSportTeam() {
		return sport_team;
	}
	public void setSportTeam(String sport_team) {
		this.sport_team = sport_team;
	}
}
