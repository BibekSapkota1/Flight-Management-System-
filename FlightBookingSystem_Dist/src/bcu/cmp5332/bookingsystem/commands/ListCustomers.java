package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.util.List;

/**
 * The {@code ListCustomers} class represents a command to list all customers in the Flight Booking System.
 * It implements the {@code Command} interface and is used to retrieve and display details of all customers
 * stored in the system.
 * 
 * Upon execution, the command retrieves the list of all customers from the {@code FlightBookingSystem} instance,
 * checks if the list is empty, and either prints "No customers found." if the list is empty or prints the list
 * of customers using the {@code listAllCustomers()} method.
 * 
 * @author Bibek
 * @author Solomon
 * @version 1.0
 * @since 2024-06-10
 * @see Command
 */

public class ListCustomers implements Command {

	  /**
     * Executes the command by retrieving the list of all customers from the Flight Booking System,
     * checking if the list is empty, and printing either "No customers found." or the list of customers.
     *
     * @param flightBookingSystem The Flight Booking System on which the command operates.
     * @throws FlightBookingSystemException If there is an error executing the command.
     */
	
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        List<Customer> customers = flightBookingSystem.getCustomers();

        if (customers.isEmpty()) {
            System.out.println("No customers found.");
        } else {
            System.out.println("List of Customers:");
            flightBookingSystem.listAllCustomers();
        }
    }
}
