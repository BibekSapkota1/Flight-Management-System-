package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;

import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

/**
 * The {@code ListBooking} class represents a command to list all bookings in the Flight Booking System.
 * It implements the {@code Command} interface and is used to retrieve and display details of all bookings
 * stored in the system.
 * 
 * Upon execution, the command retrieves the list of all bookings from the {@code FlightBookingSystem} instance,
 * displays the details of each booking, and prints the total number of bookings to the console.
 * 
 * @author Bibek
 * @author Solomon
 * @version 1.0
 * @since 2024-06-10
 * @see Command
 */

public class ListBooking implements Command {

	 /**
     * Executes the command by retrieving the list of all bookings from the Flight Booking System,
     * displaying the details of each booking, and printing the total number of bookings to the console.
     *
     * @param flightBookingSystem The Flight Booking System on which the command operates.
     * @throws FlightBookingSystemException If there is an error executing the command.
     */
	
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        flightBookingSystem.listAllBookings();
    }
}
