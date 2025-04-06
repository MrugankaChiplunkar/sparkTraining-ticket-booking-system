package dao;

import entity.*;
import exception.EventNotFoundException;
import exception.InvalidBookingIDException;
import util.DBConnUtil;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookingSystemServiceProviderImpl implements IBookingSystemServiceProvider {

    private Connection conn = DBConnUtil.getConnection();

    @Override
    public List<Event> getAvailableNoOfTickets(String event_name) {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT event_id, event_name, available_seats FROM event WHERE event_name = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, event_name);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                events.add(new Event(
                        rs.getInt("event_id"),
                        rs.getString("event_name"),
                        null, // date
                        null, // time
                        rs.getInt("available_seats"),
                        0, 0, 0, 0, null
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }

    @Override
    public boolean calculateBookingCost(int num_tickets, String event_name) throws SQLException {
        String sql = "SELECT (ticket_price * ?) AS total_cost FROM event WHERE event_name = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, num_tickets);
            stmt.setString(2, event_name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int totalCost = rs.getInt("total_cost");
                System.out.println("Total cost for booking " + num_tickets + " tickets: ₹" + totalCost);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean bookTickets(String event_name, int num_tickets, LocalDate booking_date, String customer_name,
                               String email, String phone_number) {
        String getEventSQL = "SELECT event_id, available_seats FROM event WHERE event_name = ?";
        String insertCustomerSQL = "INSERT INTO customer (name, email, phone_number) VALUES (?, ?, ?)";
        String insertBookingSQL = "INSERT INTO booking (event_id, customer_id, num_tickets, booking_date) VALUES (?, ?, ?, ?)";
        String updateSeatsSQL = "UPDATE event SET available_seats = available_seats - ? WHERE event_id = ?";

        try {
            conn.setAutoCommit(false);

            int event_id = -1;
            int available_seats = 0;

            try (PreparedStatement stmt = conn.prepareStatement(getEventSQL)) {
                stmt.setString(1, event_name);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    event_id = rs.getInt("event_id");
                    available_seats = rs.getInt("available_seats");
                } else {
                    throw new EventNotFoundException("Event not found: " + event_name);
                }
            }

            if (available_seats < num_tickets) {
                System.out.println("Not enough seats available.");
                return false;
            }

            int customer_id = -1;
            try (PreparedStatement stmt = conn.prepareStatement(insertCustomerSQL, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, customer_name);
                stmt.setString(2, email);
                stmt.setString(3, phone_number);
                stmt.executeUpdate();
                ResultSet keys = stmt.getGeneratedKeys();
                if (keys.next()) {
                    customer_id = keys.getInt(1);
                }
            }

            try (PreparedStatement stmt = conn.prepareStatement(insertBookingSQL)) {
                stmt.setInt(1, event_id);
                stmt.setInt(2, customer_id);
                stmt.setInt(3, num_tickets);
                stmt.setDate(4, Date.valueOf(booking_date));
                stmt.executeUpdate();
            }

            try (PreparedStatement stmt = conn.prepareStatement(updateSeatsSQL)) {
                stmt.setInt(1, num_tickets);
                stmt.setInt(2, event_id);
                stmt.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean cancelBooking(int booking_id) {
        String getBookingSQL = "SELECT event_id, num_tickets FROM booking WHERE booking_id = ?";
        String deleteBookingSQL = "DELETE FROM booking WHERE booking_id = ?";
        String updateSeatsSQL = "UPDATE event SET available_seats = available_seats + ? WHERE event_id = ?";

        try {
            conn.setAutoCommit(false);

            int event_id = -1;
            int num_tickets = 0;

            try (PreparedStatement stmt = conn.prepareStatement(getBookingSQL)) {
                stmt.setInt(1, booking_id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    event_id = rs.getInt("event_id");
                    num_tickets = rs.getInt("num_tickets");
                } else {
                    throw new InvalidBookingIDException("Booking ID not found: " + booking_id);
                }
            }

            try (PreparedStatement stmt = conn.prepareStatement(deleteBookingSQL)) {
                stmt.setInt(1, booking_id);
                stmt.executeUpdate();
            }

            try (PreparedStatement stmt = conn.prepareStatement(updateSeatsSQL)) {
                stmt.setInt(1, num_tickets);
                stmt.setInt(2, event_id);
                stmt.executeUpdate();
            }

            conn.commit();
            System.out.println("Booking cancelled successfully.");
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean getBookingDetails(int booking_id) {
        String sql = "SELECT b.booking_id, c.name AS customer_name, e.event_name, b.num_tickets, b.booking_date " +
                "FROM booking b " +
                "JOIN customer c ON b.customer_id = c.customer_id " +
                "JOIN event e ON b.event_id = e.event_id " +
                "WHERE b.booking_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, booking_id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("Booking ID: " + rs.getInt("booking_id"));
                System.out.println("Customer Name: " + rs.getString("customer_name"));
                System.out.println("Event Name: " + rs.getString("event_name"));
                System.out.println("Number of Tickets: " + rs.getInt("num_tickets"));
                System.out.println("Booking Date: " + rs.getDate("booking_date"));
                return true;
            } else {
                throw new InvalidBookingIDException("No booking found for ID: " + booking_id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
