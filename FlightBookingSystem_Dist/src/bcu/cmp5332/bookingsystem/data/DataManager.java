package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import java.io.IOException;

/**
 * The {@code DataManager} interface defines methods to load and store data
 * for the Flight Booking System.
 * 
 * Implementing classes provide specific implementations to handle loading and
 * storing data for various components of the system.
 * 
 * This interface ensures consistency in data management operations across different
 * data sources and formats.

 * @author Bibek
 * @author Solomon
 * @version 1.0
 * @since 2024-06-10
 */

public interface DataManager {
    
    public static final String SEPARATOR = "::";
    
    /**
     * Loads data into the Flight Booking System from an external source.
     *
     * @param fbs The Flight Booking System instance.
     * @throws IOException If there is an error reading from the data source.
     * @throws FlightBookingSystemException If there is an error parsing the data or interacting with the system.
     */
    
    public void loadData(FlightBookingSystem fbs) throws IOException, FlightBookingSystemException;
    
    /**
     * Stores data from the Flight Booking System into an external data source.
     *
     * @param fbs The Flight Booking System instance.
     * @throws IOException If there is an error writing to the data source.
     */
    
    public void storeData(FlightBookingSystem fbs) throws IOException;
    
}
