package bcu.cmp5332.bookingsystem.commands;

import java.io.IOException;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

/**
 * The {@code CancelBooking} class represents a command to cancel a booking for a customer on a specific flight.
 * It implements the {@code Command} interface and is used in the context of the Flight Booking System.
 * 
 * The cancellation involves checking the existence of the customer and flight, retrieving the booking associated
 * with both, calculating the cancellation fee, and updating the booking status and associated entities accordingly.
 * 
 * @author Bibek
 * @author Solomon
 * @version 1.0
 * @since 2024-06-10
 * @see Command
 */

public class CancelBooking implements Command {

    private final int customerId;
    private final int flightId;

    /**
     * Constructs a {@code CancelBooking} command with the specified customer and flight IDs.
     *
     * @param customerId The ID of the customer for whom the booking is to be canceled.
     * @param flightId   The ID of the flight for which the booking is to be canceled.
     */
    
    public CancelBooking(int customerId, int flightId) {
        this.customerId = customerId;
        this.flightId = flightId;
    }

    /**
     * Executes the command by canceling the booking for the specified customer on the specified flight.
     *
     * @param flightBookingSystem The Flight Booking System on which the command operates.
     * @throws FlightBookingSystemException If an exception specific to the Flight Booking System occurs.
     */
    
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        Customer customer = flightBookingSystem.getCustomerByID(customerId);
		Flight flight = flightBookingSystem.getFlightByID(flightId);

		if (customer == null) {
		    throw new FlightBookingSystemException("Customer with ID " + customerId + " not found.");
		}

		if (flight == null) {
		    throw new FlightBookingSystemException("Flight with ID " + flightId + " not found.");
		}

		Booking booking = flightBookingSystem.getBookingByCustomerAndFlight(customer, flight);

		if (booking == null) {
		    throw new FlightBookingSystemException("Booking not found for customer " + customer.getName() + " on flight " + flight.getFlightNumber());
		}

        try {
            if (booking.isCompleted()) {
                throw new FlightBookingSystemException("Cannot cancel a completed booking.");
            }

            double cancellationFee = booking.calculateCancellationFee();
            flight.removePassenger(customer);
            booking.setCancellationFee(cancellationFee);
            booking.setDeleted(true);

            customer.removeBooking(booking);

            FlightBookingSystemData.store(flightBookingSystem);

            String message = "Booking for customer " + customer.getName() + " on flight " + flight.getFlightNumber() + " canceled.";
            System.out.println(message);

        } catch (IOException ex) {
            throw new FlightBookingSystemException("Error storing data to file: " + ex.getMessage());
        }
    }
}
