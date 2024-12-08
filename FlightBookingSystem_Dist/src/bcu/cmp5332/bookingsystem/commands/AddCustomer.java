package bcu.cmp5332.bookingsystem.commands;

import java.io.IOException;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

/**
 * The {@code AddCustomer} class represents a command to add a new customer to the Flight Booking System.
 * It implements the {@code Command} interface and is used to facilitate adding customers with their name,
 * phone number, and email address.
 * 
 * Upon execution, this command creates a new customer object, assigns it a unique ID, and adds it to the 
 * Flight Booking System's list of customers.
 * 
 * @author Bibek
 * @author Solomon
 * @version 1.0
 * @since 2024-06-15
 * @see Command
 */
public class AddCustomer implements Command {

    private final String name;
    private final String phone;
    private final String email;

    /**
     * Constructs an {@code AddCustomer} command with the specified customer details.
     * 
     * @param name the name of the customer
     * @param phone the phone number of the customer
     * @param email the email address of the customer
     */
    public AddCustomer(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    /**
     * Executes the command to add a new customer to the Flight Booking System.
     * 
     * @param flightBookingSystem the Flight Booking System to which the customer will be added
     * @throws FlightBookingSystemException if there is an error adding the customer
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        try {
            // Determine the next available customer ID
            int maxCustomerId = 0;
            if (!flightBookingSystem.getCustomers().isEmpty()) {
                maxCustomerId = flightBookingSystem.getCustomers().stream()
                        .mapToInt(Customer::getId)
                        .max()
                        .orElse(0);
            }
            int customerId = maxCustomerId + 1;

            // Create a new Customer object with the provided details
            Customer customer = new Customer(customerId, name, phone, email, false);
            
            // Add the customer to the Flight Booking System
            flightBookingSystem.addCustomer(customer);
            
            // Output confirmation message
            System.out.println("Customer #" + customer.getId() + " added.");

            // Store the updated Flight Booking System data
            FlightBookingSystemData.store(flightBookingSystem);

        } catch (IOException ex) {
            throw new FlightBookingSystemException("Error storing data to file: " + ex.getMessage());
        }
    }
}
