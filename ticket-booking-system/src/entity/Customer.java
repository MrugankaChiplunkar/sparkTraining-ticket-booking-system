package entity;

public class Customer {
	private int customer_id;
	private String customer_name;
	private String email;
	private String phone_number;
	private int booking_id;
	
	//default constructor
	public Customer() {
		
	}
	
	//parameterized constructor
	public Customer(int customer_id, String customer_name, String email, String phone_number, int booking_id) {
		this.customer_id = customer_id;
		this.customer_name = customer_name;
		this.email = email;
		this.phone_number = phone_number;
		this.booking_id = booking_id;
	}
	
	//getter and setter methods
	public int getCustomerId() {
		return customer_id;
	}
	public void setCustomerId(int customer_id) {
		this.customer_id = customer_id;
	}
	
	public String getCustomerName() {
		return customer_name;
	}
	public void setCustomerName(String customer_name) {
		this.customer_name = customer_name;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String setPhoneNumber() {
		return phone_number;
	}
	public void setPhoneNumber(String phone_number) {
		this.phone_number = phone_number;
	}
	
	public int getBookingId() {
		return booking_id;
	}
	public void setBookingId(int booking_id) {
		this.booking_id = booking_id;
	}
}
