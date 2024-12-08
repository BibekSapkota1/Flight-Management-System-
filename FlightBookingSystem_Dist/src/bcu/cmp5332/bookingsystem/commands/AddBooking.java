package bcu.cmp5332.bookingsystem.commands;

import java.io.IOException;
import java.time.LocalDate;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.Flight.FlightClass;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;


/**
 * The {@code AddBooking} class adds a booking for a customer on a specific flight.
 * It implements the {@code Command} interface within the Flight Booking System.
 * The booking includes customer ID, flight ID, booking date, and flight class.
 * 
 * @author Bibek
 * @author Solomon
 * @version 1.0
 * @since 2024-06-15
 * @see Command
 */

public class AddBooking implements Command {

    private final int customerId;
    private final int flightId;
    private final LocalDate bookingDate;
    private final FlightClass flightClass;

    
    /**
     * Constructs an {@code AddBooking} command with the specified customer ID, flight ID, booking date, and flight class.
     *
     * @param customerId   ID of the customer.
     * @param flightId     ID of the flight.
     * @param bookingDate  The booking date.
     * @param flightClass  The class of the flight.
     */
    
    public AddBooking(int customerId, int flightId, LocalDate bookingDate, FlightClass flightClass) {
        this.customerId = customerId;
        this.flightId = flightId;
        this.bookingDate = bookingDate;
        this.flightClass = flightClass;
    }


    /**
     * Executes the command by adding a booking for the specified customer on the specified flight.
     *
     * @param flightBookingSystem The flight booking system.
     * @throws FlightBookingSystemException If an error occurs during booking.
     */

    
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
    	if (flightClass == null) {
            throw new FlightBookingSystemException("Flight class cannot be null.");
        }
    	
        try {
            Customer customer = flightBookingSystem.getCustomerByID(customerId);
            Flight flight = flightBookingSystem.getFlightByID(flightId);

            if (customer == null) {
                throw new FlightBookingSystemException("Customer with ID " + customerId + " not found.");
            }

            if (flight == null) {
                throw new FlightBookingSystemException("Flight with ID " + flightId + " not found.");
            }

            if (flight.isDeleted()) {
                System.out.println("Flight " + flightId + " is deleted. Booking not allowed.");
                return;
            }

            if (flight.isFullyBooked() || flight.getPassengers().size() >= flight.getCapacity()) {
                System.out.println("Flight " + flightId + " is fully booked or at full capacity. Booking not allowed.");
                return;
            }

            Booking booking = new Booking(customer, flight, bookingDate, false, flightClass);
            flightBookingSystem.addBooking(booking);
            System.out.println("Booking for customer " + customer.getName() + " on flight " + flight.getFlightNumber() + " in class " + flightClass + " added.");

            FlightBookingSystemData.store(flightBookingSystem);

        } catch (IOException ex) {
            throw new FlightBookingSystemException("Error storing data to file: " + ex.getMessage());
        }
    }
}
