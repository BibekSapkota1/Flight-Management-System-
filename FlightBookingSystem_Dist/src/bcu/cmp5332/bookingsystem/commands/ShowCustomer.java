package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

/**
 * The {@code ShowCustomer} class represents a command to display details of a specific customer
 * in the Flight Booking System.
 * 
 * It implements the {@code Command} interface and is used to retrieve and print information about
 * a customer identified by their unique ID.
 * 
 * Upon execution, it retrieves the customer details, including ID, name, phone number, email, and
 * any bookings associated with the customer, and prints them to the console.
 * 
 * @author Bibek
 * @author Solomon
 * @version 1.0
 * @since 2024-06-10
 * @see Command
 */

public class ShowCustomer implements Command {

    private final int customerId;

    /**
     * Constructs a {@code ShowCustomer} command with the specified customer ID.
     *
     * @param customerId The ID of the customer whose details are to be displayed.
     */
    
    public ShowCustomer(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Executes the command by retrieving and displaying the details of the customer with the specified ID.
     *
     * @param flightBookingSystem The Flight Booking System instance that provides access to customer data.
     * @throws FlightBookingSystemException If the customer with the specified ID is not found or if there is
     *                                      an error retrieving customer details.
     */

    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        Customer customer = flightBookingSystem.getCustomerByID(customerId);

        if (customer == null) {
            throw new FlightBookingSystemException("Customer with ID " + customerId + " not found.");
        }

        System.out.println("Customer ID: " + customer.getId());
        System.out.println("Name: " + customer.getName());
        System.out.println("Phone: " + customer.getPhone());
        System.out.println("Email: " + customer.getEmail());

        System.out.println("Bookings:");

        if (customer.getBookings().isEmpty()) {
            System.out.println("No bookings for this customer.");
        } else {
            System.out.println(customer.getDetails());
        }
    }

}
 
