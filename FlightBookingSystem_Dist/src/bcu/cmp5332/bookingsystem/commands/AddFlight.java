package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import java.io.IOException;
import java.time.LocalDate;

/**
 * The {@code AddFlight} class represents a command to add a new flight to the Flight Booking System.
 * It implements the {@code Command} interface and is used to facilitate adding flights with specific details
 * such as flight number, origin, destination, departure date, capacity, and price.
 * 
 * Upon execution, this command creates a new flight object, assigns it a unique ID, and adds it to the 
 * Flight Booking System's list of flights.
 * 
 * @author Bibek
 * @author Solomon
 * @version 1.0
 * @since 2024-06-15
 * @see Command
 */

public class AddFlight implements  Command {

    private final String flightNumber;
    private final String origin;
    private final String destination;
    private final LocalDate departureDate;
    private final int capacity;
    private final double price;
    
    /**
     * Constructs an {@code AddFlight} command with the specified flight details.
     * 
     * @param flightNumber the flight number
     * @param origin the origin of the flight
     * @param destination the destination of the flight
     * @param departureDate the departure date of the flight
     * @param capacity the capacity of the flight
     * @param price the price of the flight
     */

    public AddFlight(String flightNumber, String origin, String destination, LocalDate departureDate,  int capacity, double price) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departureDate = departureDate;
        this.capacity = capacity;
        this.price = price;
    }
    
    /**
     * Executes the command to add a new flight to the Flight Booking System.
     * 
     * @param flightBookingSystem the Flight Booking System to which the flight will be added
     * @throws FlightBookingSystemException if there is an error adding the flight
     */
    
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        int maxId = 0;
        try {
            if (flightBookingSystem.getFlights().size() > 0) {
                int lastIndex = flightBookingSystem.getFlights().size() - 1;
                maxId = flightBookingSystem.getFlights().get(lastIndex).getId();
            }

            // Assuming isDeleted is false for a newly added flight
            Flight flight = new Flight(++maxId, flightNumber, origin, destination, departureDate, capacity, false, price);
            flightBookingSystem.addFlight(flight);
            System.out.println("Flight #" + flight.getId() + " added.");

            FlightBookingSystemData.store(flightBookingSystem);

        } catch (IOException ex) {
            if (maxId > 0) {
                flightBookingSystem.removeFlightById(maxId);
            }
            throw new FlightBookingSystemException("Error storing data to file: " + ex.getMessage());
        }
    }
}
