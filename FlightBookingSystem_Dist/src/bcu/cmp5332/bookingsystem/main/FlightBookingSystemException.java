package bcu.cmp5332.bookingsystem.main;

/**
 * FlightBookingSystemException extends {@link Exception} class and is a custom exception
 * that is used to notify the user about errors or invalid commands within the flight booking system.
 */
public class FlightBookingSystemException extends Exception {

    /**
     * Constructs a new FlightBookingSystemException with the specified detail message.
     * 
     * @param message The detail message (which is saved for later retrieval by the getMessage() method).
     */
    public FlightBookingSystemException(String message) {
        super(message);
    }
}
