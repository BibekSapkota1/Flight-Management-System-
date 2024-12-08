package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

/**
 * The {@code DeleteFlight} class represents a command to delete a flight from the Flight Booking System.
 * It implements the {@code Command} interface and is used to remove a flight based on its unique ID.
 * 
 * Upon execution, if the flight with the specified ID exists in the system, the command removes the flight
 * from the system. If the flight does not exist, it throws a {@code FlightBookingSystemException}.
 * 
 * @author Bibek
 * @author Solomon
 * @version 1.0
 * @since 2024-06-10
 * @see Command
 */

public class DeleteFlight implements Command {

    private final int flightId;

    /**
     * Constructs a {@code DeleteFlight} command with the specified flight ID.
     * 
     * @param flightId The ID of the flight to be deleted from the system.
     */
    
    public DeleteFlight(int flightId) {
        this.flightId = flightId;
    }

    /**
     * Executes the command by deleting the flight from the Flight Booking System.
     * 
     * @param flightBookingSystem The Flight Booking System from which the flight is to be deleted.
     * @throws FlightBookingSystemException If the flight with the specified ID is not found in the system.
     */
    
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        if (!flightBookingSystem.flightExists(flightId)) {
            throw new FlightBookingSystemException("Flight with ID " + flightId + " not found.");
        } 
        flightBookingSystem.removeFlightById(flightId);

        System.out.println("Flight with ID " + flightId + " has been deleted successfully.");
    }
}
