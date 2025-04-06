package dao;
import entity.Booking;
import entity.Concert;
import entity.Customer;
import entity.Event;
import entity.Movie;
import entity.Sports;
import entity.Venue;
import java.time.*;
import java.util.List;
import exception.EventNotFoundException;
import exception.InvalidBookingIDException;
import exception.NullPointerException;

public interface IEventServiceProvider {
	public boolean createEvent(Event event);
	public List<Event> getEventDetails(String event_name);
}
