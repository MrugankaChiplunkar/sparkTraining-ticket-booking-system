package dao;
import entity.Booking;
import entity.Concert;
import entity.Customer;
import entity.Event;
import entity.Movie;
import entity.Sports;
import entity.Venue;

import java.sql.SQLException;
import java.time.*;
import java.util.List;

import exception.EventNotFoundException;
import exception.InvalidBookingIDException;
import exception.NullPointerException;

public interface IBookingSystemServiceProvider {
	public List<Event> getAvailableNoOfTickets(String event_name);
	public boolean calculateBookingCost(int num_tickets, String event_name) throws SQLException;
	public boolean bookTickets(String event_name, int num_tickets, LocalDate booking_date, String customer_name, 
			String email, String phone_number);
	public boolean cancelBooking(int booking_id);
	public boolean getBookingDetails(int booking_id);
}
