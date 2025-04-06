package entity;

import java.time.LocalDate;
import java.time.LocalTime;

public class Event {
    private int event_id;
    private String event_name;
    private LocalDate event_date;
    private LocalTime event_time;
    private int venue_id;
    private int available_seats;
    private int total_seats;
    private double ticket_price;
    private int booking_id;
    private String event_type;

    // Default constructor
    public Event() {
    }

    // Parameterized constructor
    public Event(int event_id, String event_name, LocalDate event_date, LocalTime event_time, int venue_id,
                 int available_seats, int total_seats, double ticket_price, int booking_id, String event_type) {
        this.event_id = event_id;
        this.event_name = event_name;
        this.event_date = event_date;
        this.event_time = event_time;
        this.venue_id = venue_id;
        this.available_seats = available_seats;
        this.total_seats = total_seats;
        this.ticket_price = ticket_price;
        this.booking_id = booking_id;
        setEventType(event_type); // validation logic applied here
    }

    // Getter and setter methods
    public int getEventId() {
        return event_id;
    }

    public void setEventId(int event_id) {
        this.event_id = event_id;
    }

    public String getEventName() {
        return event_name;
    }

    public void setEventName(String event_name) {
        this.event_name = event_name;
    }

    public LocalDate getEventDate() {
        return event_date;
    }

    public void setEventDate(LocalDate event_date) {
        this.event_date = event_date;
    }

    public LocalTime getEventTime() {
        return event_time;
    }

    public void setEventTime(LocalTime event_time) {  // Fixed incorrect method name
        this.event_time = event_time;
    }

    public int getVenueId() {
        return venue_id;
    }

    public void setVenueId(int venue_id) {
        this.venue_id = venue_id;
    }

    public int getAvailableSeats() {
        return available_seats;
    }

    public void setAvailableSeats(int available_seats) {
        this.available_seats = available_seats;
    }

    public int getTotalSeats() {
        return total_seats;
    }

    public void setTotalSeats(int total_seats) {
        this.total_seats = total_seats;
    }

    public double getTicketPrice() {
        return ticket_price;
    }

    public void setTicketPrice(double ticket_price) {
        this.ticket_price = ticket_price;
    }

    public int getBookingId() {
        return booking_id;
    }

    public void setBookingId(int booking_id) {
        this.booking_id = booking_id;
    }

    public String getEventType() {
        return event_type;
    }

    public void setEventType(String event_type) {
        if (event_type.equalsIgnoreCase("Movie") ||
            event_type.equalsIgnoreCase("Concert") ||
            event_type.equalsIgnoreCase("Sports")) {
            this.event_type = event_type;
        } else {
            throw new IllegalArgumentException("Invalid event type! Must be: Movie, Concert, or Sports.");
        }
    }
}
