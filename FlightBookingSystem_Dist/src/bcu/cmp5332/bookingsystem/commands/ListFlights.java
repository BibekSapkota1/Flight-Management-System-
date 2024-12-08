package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

/**
 * The {@code ListFlights} class represents a command to list all flights in the Flight Booking System.
 * It implements the {@code Command} interface and is used to retrieve and display details of all flights
 * stored in the system.
 * 
 * Upon execution, the command retrieves the list of all flights from the {@code FlightBookingSystem} instance
 * and prints the details of each flight using the {@code listAllFlights()} method.
 * 
 * @author Bibek
 * @author Solomon
 * @version 1.0
 * @since 2024-06-10
 * @see Command
 */

public class ListFlights implements Command {

	 /**
     * Executes the command by retrieving the list of all flights from the Flight Booking System
     * and printing the details of each flight.
     *
     * @param flightBookingSystem The Flight Booking System on which the command operates.
     * @throws FlightBookingSystemException If there is an error executing the command.
     */
	
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
    	flightBookingSystem.listAllFlights();
    }
}
