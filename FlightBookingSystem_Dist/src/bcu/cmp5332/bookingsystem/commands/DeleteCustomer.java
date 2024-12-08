package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

/**
 * The {@code DeleteCustomer} class represents a command to delete a customer from the Flight Booking System.
 * It implements the {@code Command} interface and is used to remove a customer based on their unique ID.
 * 
 * Upon execution, if the customer with the specified ID exists in the system, the command removes the customer
 * from the system. If the customer does not exist, it throws a {@code FlightBookingSystemException}.
 * 
 * @author Bibek
 * @author Solomon
 * @version 1.0
 * @since 2024-06-10
 * @see Command
 */

public class DeleteCustomer implements Command {

    private final int customerId;

    /**
     * Constructs a {@code DeleteCustomer} command with the specified customer ID.
     * 
     * @param customerId The ID of the customer to be deleted from the system.
     */
    
    public DeleteCustomer(int customerId) {
        this.customerId = customerId;
    }


    /**
     * Executes the command by deleting the customer from the Flight Booking System.
     * 
     * @param flightBookingSystem The Flight Booking System from which the customer is to be deleted.
     * @throws FlightBookingSystemException If the customer with the specified ID is not found in the system.
     */
    
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        if (!flightBookingSystem.customerExists(customerId)) {
            throw new FlightBookingSystemException("Customer with ID " + customerId + " not found.");
        } 
        
        flightBookingSystem.removeCustomerById(customerId);

        System.out.println("Customer with ID " + customerId + " has been deleted successfully.");
    }
}

