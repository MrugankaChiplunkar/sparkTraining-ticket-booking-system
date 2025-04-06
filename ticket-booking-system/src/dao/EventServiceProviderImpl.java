package dao;
import entity.Booking;
import entity.Concert;
import entity.Customer;
import entity.Event;
import entity.Movie;
import entity.Sports;
import entity.Venue;
import java.sql.*;
import java.time.*;
import java.util.*;
import exception.EventNotFoundException;
import exception.InvalidBookingIDException;
import exception.NullPointerException;
import util.DBConnUtil;

public class EventServiceProviderImpl implements IEventServiceProvider {

    private Connection conn = DBConnUtil.getConnection();

    @Override
    public boolean createEvent(Event event) {
        String sql = "insert into event(event_name, event_date, event_time, venue_id, total_seats, "
                   + "available_seats, ticket_price, booking_id, event_type) values (?,?,?,?,?,?,?,?,?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, event.getEventName());
            stmt.setDate(2, java.sql.Date.valueOf(event.getEventDate()));
            stmt.setTime(3, java.sql.Time.valueOf(event.getEventTime()));
            stmt.setInt(4, event.getVenueId());
            stmt.setInt(5, event.getTotalSeats());
            stmt.setInt(6, event.getAvailableSeats());
            stmt.setDouble(7, event.getTicketPrice());
            stmt.setInt(8, event.getBookingId());
            stmt.setString(9, event.getEventType());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<Event> getEventDetails(String event_name) {
        List<Event> eventList = new ArrayList<>();
        String sql = "select * from event where event_name = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, event_name);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                eventList.add(new Event(
                    rs.getInt("event_id"),
                    rs.getString("event_name"),
                    rs.getDate("event_date").toLocalDate(),
                    rs.getTime("event_time").toLocalTime(),
                    rs.getInt("venue_id"),
                    rs.getInt("available_seats"),    
                    rs.getInt("total_seats"),        
                    rs.getDouble("ticket_price"),
                    rs.getInt("booking_id"),
                    rs.getString("event_type")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return eventList;
    }
}
