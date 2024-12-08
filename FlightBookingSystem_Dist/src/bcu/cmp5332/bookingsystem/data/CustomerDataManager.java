package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Manages the loading and storing of customer data for the Flight Booking System from/to a text file.
 * 
 * Implements the {@code DataManager} interface and provides methods to parse customer data from
 * text format, format customer data for storage, load customer data into the system, and store customer data
 * back to the file.
 * 
 * This class ensures data integrity and handles exceptions that may occur during data operations.
 * 
 * @author Bibek
 * @author Solomon
 * @version 1.0
 * @since 2024-06-10
 * @see DataManager
 */

public class CustomerDataManager implements DataManager {

    private final String RESOURCE = "./resources/data/customers.txt";
    private final String SEPARATOR = "::";
    
    /**
     * Parses a line of text from the customers data file into a {@code Customer} object.
     *
     * @param line The line of text containing customer data in the specified format.
     * @return The parsed {@code Customer} object.
     */
    
    private Customer parseCustomer(String line) {
        String[] parts = line.split(SEPARATOR);
        int id = Integer.parseInt(parts[0]);
        String name = parts[1];
        String phone = parts[2];
        String email = parts[3];
        boolean deleted = Boolean.parseBoolean(parts[4]);  


        return new Customer(id, name, phone, email, deleted);
    }
    
    /**
     * Formats a {@code Customer} object into a string representation for storage.
     *
     * @param customer The {@code Customer} object to be formatted.
     * @return The formatted string representation of the customer.
     */

    private String formatCustomer(Customer customer) {
        return customer.getId() + SEPARATOR + customer.getName() + SEPARATOR
                + customer.getPhone() + SEPARATOR + customer.getEmail() + SEPARATOR
                + customer.isDeleted() + SEPARATOR;  
    }
    
    /**
     * Loads customer data from the customers data file into the Flight Booking System.
     *
     * @param fbs The Flight Booking System instance.
     * @throws IOException If there is an error reading from the file.
     * @throws FlightBookingSystemException If there is an error parsing the customer data.
     */

    @Override
    public void loadData(FlightBookingSystem fbs) throws IOException, FlightBookingSystemException {
        try (BufferedReader reader = new BufferedReader(new FileReader(RESOURCE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Customer customer = parseCustomer(line);
                fbs.addCustomer(customer);
            }
        }
    }

    /**
     * Stores customer data from the Flight Booking System into the customers data file.
     *
     * @param fbs The Flight Booking System instance.
     * @throws IOException If there is an error writing to the file.
     */

    @Override
    public void storeData(FlightBookingSystem fbs) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RESOURCE))) {
            for (Customer customer : fbs.getCustomers()) {
                String line = formatCustomer(customer);
                writer.write(line);
                writer.newLine();
            }
        }
    }

}
