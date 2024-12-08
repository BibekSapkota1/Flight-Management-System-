package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code FlightBookingSystemData} class manages the loading and storing of
 * data for the Flight Booking System. It utilizes multiple {@link DataManager} objects
 * to handle specific data entities such as flights, customers, and bookings.
 * 
 * This class ensures that the Flight Booking System can be initialized from
 * persistent storage and changes can be saved back to the storage.
 * 
 * The {@code FlightBookingSystemData} class uses static methods to provide
 * access to data loading and storing functionality without requiring an instance
 * of the class to be created.
 * 
 * It includes functionality to load all necessary data on system startup and
 * to store updated data on system shutdown or when changes are made.
 * 
 * @author Bibek
 * @author Solomon
 * @version 1.0
 * @since 2024-06-10
 */

public class FlightBookingSystemData {
    
    private static final List<DataManager> dataManagers = new ArrayList<>();
    
    // runs only once when the object gets loaded to memory
    static {
        dataManagers.add(new FlightDataManager());
        
        /* Uncomment the two lines below when the implementation of their 
        loadData() and storeData() methods is complete */
         dataManagers.add(new CustomerDataManager());
         dataManagers.add(new BookingDataManager());
    }
    
    /**
     * Loads the complete Flight Booking System from persistent storage.
     *
     * @return An instance of {@link FlightBookingSystem} populated with data.
     * @throws FlightBookingSystemException If there is an error loading the data.
     * @throws IOException If there is an error reading from the data source.
     */
    
    public static FlightBookingSystem load() throws FlightBookingSystemException, IOException {

        FlightBookingSystem fbs = new FlightBookingSystem();
        for (DataManager dm : dataManagers) {
            dm.loadData(fbs);
        }
        return fbs;
    }
    
    /**
     * Stores the current state of the Flight Booking System into persistent storage.
     *
     * @param fbs The instance of {@link FlightBookingSystem} to be stored.
     * @throws IOException If there is an error writing to the data source.
     */

    public static void store(FlightBookingSystem fbs) throws IOException {

        for (DataManager dm : dataManagers) {
            dm.storeData(fbs);
        }
    }
    
    
    
}
