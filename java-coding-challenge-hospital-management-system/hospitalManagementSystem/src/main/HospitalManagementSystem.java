package main;

import java.util.List;
import java.util.Scanner;
import dao.HospitalServiceImpl;
import dao.IHospitalService;
import entity.Appointment;
import exception.PatientNumberNotFoundException;

public class HospitalManagementSystem {

    private static final Scanner sc = new Scanner(System.in);
    private static final IHospitalService service = new HospitalServiceImpl();

    public static void main(String[] args) {
        int choice;

        System.out.println("----- Welcome to Hospital Management System -----");

        do {
            System.out.println("\nMenu:");
            System.out.println("1. View Appointment by ID");
            System.out.println("2. View Appointments for Patient");
            System.out.println("3. View Appointments for Doctor");
            System.out.println("4. Schedule Appointment");
            System.out.println("5. Update Appointment");
            System.out.println("6. Cancel Appointment");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1 -> viewAppointmentById();
                case 2 -> viewAppointmentsForPatient();
                case 3 -> viewAppointmentsForDoctor();
                case 4 -> scheduleAppointment();
                case 5 -> updateAppointment();
                case 6 -> cancelAppointment();
                case 0 -> System.out.println("Thank you for using the system. Exiting...");
                default -> System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 0);

        sc.close();
    }

    // ------------ Separated functions for each case ------------

    private static void viewAppointmentById() {
        System.out.print("Enter Appointment ID: ");
        int id = sc.nextInt();
        Appointment appt = service.getAppointmentById(id);
        System.out.println(appt != null ? appt : "Appointment not found.");
    }

    private static void viewAppointmentsForPatient() {
        System.out.print("Enter Patient ID: ");
        int pid = sc.nextInt();
        try {
            List<Appointment> list = service.getAppointmentsForPatient(pid);
            if (list.isEmpty()) {
                System.out.println("No appointments found for this patient.");
            } else {
                list.forEach(System.out::println);
            }
        } catch (PatientNumberNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void viewAppointmentsForDoctor() {
        System.out.print("Enter Doctor ID: ");
        int did = sc.nextInt();
        List<Appointment> list = service.getAppointmentsForDoctor(did);
        if (list.isEmpty()) {
            System.out.println("No appointments found for this doctor.");
        } else {
            list.forEach(System.out::println);
        }
    }

    private static void scheduleAppointment() {
        System.out.print("Enter Appointment ID: ");
        int id = sc.nextInt();
        System.out.print("Enter Patient ID: ");
        int pid = sc.nextInt();
        System.out.print("Enter Doctor ID: ");
        int did = sc.nextInt();
        sc.nextLine();  // consume newline
        System.out.print("Enter Appointment Date (YYYY-MM-DD): ");
        String date = sc.nextLine();
        System.out.print("Enter Description: ");
        String desc = sc.nextLine();

        Appointment appt = new Appointment(id, pid, did, date, desc);
        boolean success = service.scheduleAppointment(appt);
        System.out.println(success ? "Appointment scheduled successfully." : "Failed to schedule appointment.");
    }

    private static void updateAppointment() {
        System.out.print("Enter Appointment ID to update: ");
        int id = sc.nextInt();
        System.out.print("Enter New Patient ID: ");
        int pid = sc.nextInt();
        System.out.print("Enter New Doctor ID: ");
        int did = sc.nextInt();
        sc.nextLine();  
        System.out.print("Enter New Appointment Date (YYYY-MM-DD): ");
        String date = sc.nextLine();
        System.out.print("Enter New Description: ");
        String desc = sc.nextLine();

        Appointment appt = new Appointment(id, pid, did, date, desc);
        boolean success = service.updateAppointment(appt);
        System.out.println(success ? "Appointment updated successfully." : "Update failed.");
    }

    private static void cancelAppointment() {
        System.out.print("Enter Appointment ID to cancel: ");
        int id = sc.nextInt();
        boolean success = service.cancelAppointment(id);
        System.out.println(success ? "Appointment cancelled successfully." : "Cancellation failed.");
    }
}
