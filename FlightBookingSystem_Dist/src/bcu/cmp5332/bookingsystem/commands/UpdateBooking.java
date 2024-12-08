package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.Flight.FlightClass;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.IOException;
import java.time.LocalDate;

/**
 * The {@code UpdateBooking} class represents a command to update a booking for a customer on a specific flight
 * in the Flight Booking System.
 * 
 * It implements the {@code Command} interface and allows modifying the booking date and flight class of an existing booking.
 * 
 * Upon execution, it retrieves the customer, flight, and booking details, validates their existence, updates the booking
 * with new date and class (if provided), and stores the updated information back to the data storage.
 * 
 * This class ensures data integrity and handles exceptions that may occur during the update process.
 * 
 * @author Bibek
 * @author Solomon
 * @version 1.0
 * @since 2024-06-10
 * @see Command
 */

public class UpdateBooking implements Command {

    private final int customerId;
    private final int flightId;
    private final LocalDate newBookingDate;
    private final FlightClass newFlightClass;

    /**
     * Constructs an {@code UpdateBooking} command with the specified customer ID, flight ID, new booking date,
     * and new flight class.
     *
     * @param customerId    The ID of the customer whose booking is to be updated.
     * @param flightId      The ID of the flight for which the booking is to be updated.
     * @param newBookingDate The new booking date for the booking. Can be null if not updated.
     * @param newFlightClass The new flight class for the booking. Can be null if not updated.
     */
    
    public UpdateBooking(int customerId, int flightId, LocalDate newBookingDate, FlightClass newFlightClass) {
        this.customerId = customerId;
        this.flightId = flightId;
        this.newBookingDate = newBookingDate;
        this.newFlightClass = newFlightClass;
    }

    /**
     * Executes the command by updating the booking for the specified customer on the specified flight
     * with the new booking date and flight class (if provided).
     *
     * @param flightBookingSystem The Flight Booking System on which the command operates.
     * @throws FlightBookingSystemException If there is an error executing the command or updating the booking.
     */
    
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        try {
            Customer customer = flightBookingSystem.getCustomerByID(customerId);
            Flight oldFlight = flightBookingSystem.getFlightByID(flightId);

            if (customer == null) {
                throw new FlightBookingSystemException("Customer with ID " + customerId + " not found.");
            }

            if (oldFlight == null) {
                throw new FlightBookingSystemException("Flight with ID " + flightId + " not found.");
            }

            Booking booking = flightBookingSystem.getBookingByCustomerAndFlight(customer, oldFlight);

            if (booking == null) {
                throw new FlightBookingSystemException("Booking not found for customer " + customer.getName() + " on flight " + oldFlight.getFlightNumber());
            }

            if (newBookingDate != null) {
                booking.setBookingDate(newBookingDate);
            }

            if (newFlightClass != null) {
                booking.setFlightClass(newFlightClass);
            }

            System.out.println("Booking for customer " + customer.getName() + " on flight " + oldFlight.getFlightNumber() + " updated.");
            FlightBookingSystemData.store(flightBookingSystem);

        } catch (IOException ex) {
            throw new FlightBookingSystemException("Error storing data to file: " + ex.getMessage());
        }
    }
}


