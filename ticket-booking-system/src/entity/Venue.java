package entity;

public class Venue {
	private int venue_id;
	private String venue_name;
	private String venue_address;
	
	//default constructor
	public Venue() {
		
	}
	
	//parameterized constructor
	public Venue(int venue_id, String venue_name, String venue_address) {
		this.venue_id = venue_id;
		this.venue_address = venue_address;
		this.venue_name = venue_name;
	}
	
	//getter and setter methods
	public int getVenueId() {
		return venue_id;
	}
	public void setVenueId(int venue_id) {
		this.venue_id = venue_id;
	}
	
	public String getVenueName() {
		return venue_name;
	}
	public void setVenuename(String venue_name) {
		this.venue_name = venue_name;
	}
	
	public String getVenueAddress() {
		return venue_address;
	}
	public void setVenueAddress(String venue_address) {
		this.venue_address = venue_address;
	}
}
