package entity;
import java.time.LocalDate;

public class Booking {
	private int booking_id;
	private int customer_id;
	private int event_id;
	private int num_tickets;
	private double total_cost;
	private LocalDate booking_date;
	
	//default constructor
	public Booking() {
		
	}
	
	//parameterized constructor
	public Booking(int booking_id, int customer_id, int event_id, int num_tickets, double total_cost, LocalDate booking_date) 
	{
		this.booking_id = booking_id;
		this.customer_id = customer_id;
		this.event_id = event_id;
		this.num_tickets = num_tickets;
		this.total_cost = total_cost;
		this.booking_date = booking_date;
	}
	
	//getter and setter methods
	public int getBookingId() {
		return booking_id;
	}
	public void setBookingId(int booking_id) {
		this.booking_id = booking_id;
	}
	
	public int getCustomerId() {
		return customer_id;
	}
	public void setCustomerId(int customer_id) {
		this.customer_id = customer_id;
	}
	
	public int getEventId() {
		return event_id;
	}
	public void setEventId(int event_id) {
		this.event_id = event_id;
	}
	
	public int getNumberOfTickets() {
		return num_tickets;
	}
	public void setNumberOfTickets(int num_tickets) {
		this.num_tickets = num_tickets;
	}
	
	public double getTotalCost() {
		return total_cost;
	}
	public void setTotalcost(int total_cost) {
		this.total_cost = total_cost;
	}
	
	public LocalDate getBookingDate() {
		return booking_date;
	}
	public void setBookingDate(LocalDate booking_date) {
		this.booking_date = booking_date;
	}
	
	
}
