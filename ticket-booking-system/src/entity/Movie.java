package entity;

public class Movie extends Event {
	private String genre;
	private String actor_name;
	private String actress_name;
	
	//default constructor
	public Movie() {
		
	}
	
	//parameterized constructor
	public Movie(String genre, String actor_name, String actress_name) {
		this.actor_name = actor_name;
		this.actress_name = actress_name;
		this.genre = genre;
	}
	
	//getter and setter methods
	public String getGenre() {
		return genre;
	}
	
}
