package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * The {@code BookingDataManager} class manages the loading and storing of booking data
 * for the Flight Booking System from/to a text file.
 * 
 * It implements the {@code DataManager} interface and provides methods to parse booking data from
 * text format, format booking data for storage, load booking data into the system, and store booking data
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

public class BookingDataManager implements DataManager {

    public final String RESOURCE = "./resources/data/bookings.txt";
    private final String SEPARATOR = "::";
    
    /**
     * Parses a line of text from the bookings data file into a {@code Booking} object.
     *
     * @param fbs The Flight Booking System instance.
     * @param line The line of text containing booking data in the specified format.
     * @return The parsed {@code Booking} object.
     * @throws FlightBookingSystemException If there is an error parsing the booking data or if customer or flight is not found.
     */
    
    private Booking parseBooking(FlightBookingSystem fbs, String line) throws FlightBookingSystemException {
    	String[] parts = line.split(SEPARATOR);
        int customerId = Integer.parseInt(parts[0]);
        int flightId = Integer.parseInt(parts[1]);
        LocalDate bookingDate = LocalDate.parse(parts[2]);

        Customer customer = fbs.getCustomerByID(customerId);
        Flight flight = fbs.getFlightByID(flightId);

        if (customer == null || flight == null) {
            throw new FlightBookingSystemException("Invalid booking data. Customer or Flight not found.");
        }
        
        Flight.FlightClass flightClass = Flight.FlightClass.valueOf(parts[3]);
        boolean deleted = Boolean.parseBoolean(parts[4]);

        Booking booking = new Booking(customer, flight, bookingDate, deleted,flightClass);
        booking.setDeleted(deleted);

        return booking;
    }

    /**
     * Formats a {@code Booking} object into a string representation for storage.
     *
     * @param booking The {@code Booking} object to be formatted.
     * @return The formatted string representation of the booking.
     */
    
    private String formatBooking(Booking booking) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return booking.getCustomer().getId() + SEPARATOR +
               booking.getFlight().getId() + SEPARATOR +
               booking.getBookingDate().format(formatter) + SEPARATOR +
               booking.getFlightClass() + SEPARATOR +
               booking.isDeleted() + SEPARATOR;
    }
    
    /**
     * Loads booking data from the bookings data file into the Flight Booking System.
     *
     * @param fbs The Flight Booking System instance.
     * @throws IOException If there is an error reading from the file.
     * @throws FlightBookingSystemException If there is an error parsing the booking data or if customer or flight is not found.
     */


    public void loadData(FlightBookingSystem fbs) throws IOException, FlightBookingSystemException {
        try (BufferedReader reader = new BufferedReader(new FileReader(RESOURCE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Booking booking = parseBooking(fbs, line);
                if (booking != null) {
                    fbs.addBooking(booking);
                }
            }
        }
    }

    /**
     * Stores booking data from the Flight Booking System into the bookings data file.
     *
     * @param fbs The Flight Booking System instance.
     * @throws IOException If there is an error writing to the file.
     */

    @Override
    public void storeData(FlightBookingSystem fbs) throws IOException {
    	try (BufferedWriter writer = new BufferedWriter(new FileWriter(RESOURCE))) {
            for (Booking booking : fbs.getBookings()) {
                String line = formatBooking(booking);
                writer.write(line);
                writer.newLine();

            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    	
    }



}
