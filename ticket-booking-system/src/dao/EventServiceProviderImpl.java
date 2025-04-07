package dao;

import entity.Event;
import exception.EventNotFoundException;
import util.DBConnUtil;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class EventServiceProviderImpl implements IEventServiceProvider {

    private Connection conn = DBConnUtil.getConnection();

    @Override
    public boolean createEvent(Event event) {
        String sql = "INSERT INTO event(event_name, event_date, event_time, venue_id, total_seats, " +
                     "available_seats, ticket_price, event_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            conn.setAutoCommit(true);  // Ensure auto-commit is enabled

            stmt.setString(1, event.getEventName());
            stmt.setDate(2, Date.valueOf(event.getEventDate()));
            stmt.setTime(3, Time.valueOf(event.getEventTime()));
            stmt.setInt(4, event.getVenueId());
            stmt.setInt(5, event.getTotalSeats());
            stmt.setInt(6, event.getAvailableSeats());
            stmt.setDouble(7, event.getTicketPrice());
            stmt.setString(8, event.getEventType());

            int rows = stmt.executeUpdate();
            System.out.println("Rows inserted: " + rows);

            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Error inserting event: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public List<Event> getEventDetails(String event_name) {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM event WHERE event_name = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, event_name);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                events.add(new Event(
                    rs.getInt("event_id"),
                    rs.getString("event_name"),
                    rs.getDate("event_date").toLocalDate(),
                    rs.getTime("event_time").toLocalTime(),
                    rs.getInt("venue_id"),
                    rs.getInt("total_seats"),
                    rs.getInt("available_seats"),
                    rs.getDouble("ticket_price"),
                    rs.getString("event_type")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return events;
    }
}
