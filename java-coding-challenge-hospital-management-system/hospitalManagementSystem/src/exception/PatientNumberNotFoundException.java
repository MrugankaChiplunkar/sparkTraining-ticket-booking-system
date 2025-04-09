package exception;

public class PatientNumberNotFoundException extends Exception {
    
    public PatientNumberNotFoundException() {
        super("Patient with the specified ID was not found in the database.");
    }

    public PatientNumberNotFoundException(String message) {
        super(message);
    }
}
