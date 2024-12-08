package bcu.cmp5332.bookingsystem.commands;

import java.util.List;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.Flight.FlightClass;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

/**
 * The {@code ShowFlight} class represents a command to display details of a specific flight
 * in the Flight Booking System.
 * 
 * It implements the {@code Command} interface and is used to retrieve and print information about
 * a flight identified by its unique ID.
 * 
 * Upon execution, it retrieves the flight details including ID, flight number, origin, destination,
 * departure date, and the list of passengers with their booking details, and prints them to the console.
 * 
 * The passenger list includes their name, phone number, email, and class of travel (e.g., economy, business).
 * If no passengers are booked on the flight, it indicates so.
 * 
 * @author Bibek
 * @author Solomon
 * @version 1.0
 * @since 2024-06-10
 * @see Command
 */

public class ShowFlight implements Command {

    private final int flightId;

    /**
     * Constructs a {@code ShowFlight} command with the specified flight ID.
     *
     * @param flightId The ID of the flight whose details are to be displayed.
     */
    
    public ShowFlight(int flightId) {
        this.flightId = flightId;
    }

    /**
     * Executes the command by retrieving and displaying the details of the flight with the specified ID.
     *
     * @param flightBookingSystem The Flight Booking System instance that provides access to flight data.
     * @throws FlightBookingSystemException If the flight with the specified ID is not found or if there is
     *                                      an error retrieving flight details.
     */
    
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        Flight flight = flightBookingSystem.getFlightByID(flightId);

        if (flight == null || flight.isDeleted()) {
            throw new FlightBookingSystemException("Flight with ID " + flightId + " not found.");
        }

        System.out.println("Flight ID: " + flight.getId());
        System.out.println("Flight Number: " + flight.getFlightNumber());
        System.out.println("Origin: " + flight.getOrigin());
        System.out.println("Destination: " + flight.getDestination());
        System.out.println("Departure Date: " + flight.getDepartureDate());

        if (flight.getPassengers().isEmpty()) {
            System.out.println("No passengers for this flight.");
        } else {
            System.out.println("Passenger List:");
            System.out.printf("%-5s %-20s %-15s %-30s %-15s\n", "No.", "Name", "Phone", "Email", "Class");
            System.out.println("------------------------------------------------------------------------------------------");

            List<Customer> passengers = flight.getPassengers();
            for (int i = 0; i < passengers.size(); i++) {
                Customer passenger = passengers.get(i);
                Booking booking = flightBookingSystem.getBookingByCustomerAndFlight(passenger, flight);
                FlightClass flightClass = booking.getFlightClass();
                System.out.printf("%-5d %-20s %-15s %-30s %-30s\n", (i + 1), passenger.getName(), passenger.getPhone(), passenger.getEmail(), flightClass);
            }
        }
    }
}
