package main;

import dao.BookingSystemServiceProviderImpl;
import dao.EventServiceProviderImpl;
import entity.Event;
import exception.EventNotFoundException;
import exception.InvalidBookingIDException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class TicketBookingSystem {

    public static void main(String[] args) throws EventNotFoundException {
        Scanner scanner = new Scanner(System.in);
        EventServiceProviderImpl eventService = new EventServiceProviderImpl();
        BookingSystemServiceProviderImpl bookingService = new BookingSystemServiceProviderImpl();

        while (true) {
            System.out.println("\n***** TICKET BOOKING SYSTEM *****");
            System.out.println("1. Create Event");
            System.out.println("2. Get Event Details by Name");
            System.out.println("3. Get Available Tickets");
            System.out.println("4. Calculate Booking Cost");
            System.out.println("5. Book Tickets");
            System.out.println("6. Cancel Booking");
            System.out.println("7. Get Booking Details");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            try {
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1 -> createEvent(scanner, eventService);
                    case 2 -> getEventDetails(scanner, eventService);
                    case 3 -> getAvailableTickets(scanner, bookingService);
                    case 4 -> calculateBookingCost(scanner, bookingService);
                    case 5 -> bookTickets(scanner, bookingService);
                    case 6 -> cancelBooking(scanner, bookingService);
                    case 7 -> getBookingDetails(scanner, bookingService);
                    case 8 -> {
                        System.out.println("Exiting...");
                        scanner.close();
                        return;
                    }
                    default -> System.out.println("Invalid choice! Try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input type! Please enter numbers only.");
                scanner.nextLine(); // Clear the buffer
            } catch (InvalidBookingIDException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void createEvent(Scanner scanner, EventServiceProviderImpl eventService) {
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Event Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Event Date (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(scanner.nextLine());
        System.out.print("Enter Event Time (HH:MM): ");
        LocalTime time = LocalTime.parse(scanner.nextLine());
        System.out.print("Enter Venue ID: ");
        int venueId = scanner.nextInt();
        System.out.print("Enter Total Seats: ");
        int totalSeats = scanner.nextInt();
        System.out.print("Enter Available Seats: ");
        int availableSeats = scanner.nextInt();
        System.out.print("Enter Ticket Price: ");
        double price = scanner.nextDouble();
        int bookingId = 0; // Can be null or 0 depending on schema
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Event Type (concert/movie/sports): ");
        String type = scanner.nextLine();

        Event event = new Event(0, name, date, time, venueId, availableSeats, totalSeats, price, bookingId, type);
        boolean created = eventService.createEvent(event);
        System.out.println(created ? "Event created successfully!" : "Failed to create event.");
    }

    private static void getEventDetails(Scanner scanner, EventServiceProviderImpl eventService) {
        scanner.nextLine();
        System.out.print("Enter Event Name to search: ");
        String name = scanner.nextLine();
        List<Event> events = eventService.getEventDetails(name);
        if (events.isEmpty()) {
            System.out.println("No events found with the given name.");
        } else {
            for (Event e : events) {
                System.out.println(e);
            }
        }
    }

    private static void getAvailableTickets(Scanner scanner, BookingSystemServiceProviderImpl bookingService) {
        scanner.nextLine();
        System.out.print("Enter Event Name: ");
        String name = scanner.nextLine();
        List<Event> events = bookingService.getAvailableNoOfTickets(name);
        if (events.isEmpty()) {
            System.out.println("No tickets available for the given event.");
        } else {
            for (Event e : events) {
                System.out.println("Event ID: " + e.getEventId() + " | Name: " + e.getEventName() + " | Available Tickets: " + e.getVenueId());
            }
        }
    }

    private static void calculateBookingCost(Scanner scanner, BookingSystemServiceProviderImpl bookingService) {
        scanner.nextLine();
        System.out.print("Enter Event Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Number of Tickets: ");
        int numTickets = scanner.nextInt();

        try {
            bookingService.calculateBookingCost(numTickets, name);
        } catch (Exception e) {
            System.out.println("Error calculating booking cost: " + e.getMessage());
        }
    }

    private static void bookTickets(Scanner scanner, BookingSystemServiceProviderImpl bookingService) {
        scanner.nextLine();
        System.out.print("Enter Event Name: ");
        String eventName = scanner.nextLine();
        System.out.print("Enter Number of Tickets: ");
        int tickets = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Booking Date (YYYY-MM-DD): ");
        LocalDate bookingDate = LocalDate.parse(scanner.nextLine());
        System.out.print("Enter Customer Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Phone Number: ");
        String phone = scanner.nextLine();

        boolean booked = bookingService.bookTickets(eventName, tickets, bookingDate, name, email, phone);
        System.out.println(booked ? "Tickets booked successfully!" : "Failed to book tickets.");
    }

    private static void cancelBooking(Scanner scanner, BookingSystemServiceProviderImpl bookingService) throws InvalidBookingIDException {
        System.out.print("Enter Booking ID to cancel: ");
        int bookingId = scanner.nextInt();
        boolean cancelled = bookingService.cancelBooking(bookingId);
        if (!cancelled) {
            throw new InvalidBookingIDException("Could not cancel booking. Please check the booking ID.");
        }
    }

    private static void getBookingDetails(Scanner scanner, BookingSystemServiceProviderImpl bookingService) throws InvalidBookingIDException {
        System.out.print("Enter Booking ID: ");
        int bookingId = scanner.nextInt();
        boolean found = bookingService.getBookingDetails(bookingId);
        if (!found) {
            throw new InvalidBookingIDException("No booking found with ID: " + bookingId);
        }
    }
}
