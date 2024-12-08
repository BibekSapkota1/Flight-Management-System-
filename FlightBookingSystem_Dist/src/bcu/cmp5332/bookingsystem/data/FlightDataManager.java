package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * The {@code FlightDataManager} class implements {@link DataManager} to manage
 * loading and storing {@link Flight} data for the Flight Booking System.
 * 
 * It reads flight information from a text file and populates a {@link FlightBookingSystem}
 * instance during loading. During storing, it writes the current state of {@link Flight}
 * objects back to the file in a specified format.
 * 
 * This class ensures that flight data is persisted across system restarts and
 * changes made to flight information are saved back to the data source.
 * 
 * @author Bibek
 * @author Solomon
 * @version 1.0
 * @since 2024-06-10
 */

public class FlightDataManager implements DataManager {
    
    private final String RESOURCE = "./resources/data/flights.txt";
    private final String SEPARATOR = "::";
    
    /**
     * Loads flight data from the specified resource file into the given
     * Flight Booking System instance.
     * 
     * @param fbs The Flight Booking System instance to populate with loaded data.
     * @throws IOException If there is an error reading from the data source.
     * @throws FlightBookingSystemException If there is an error parsing the data or insufficient data.
     */
    
    @Override
    public void loadData(FlightBookingSystem fbs) throws IOException, FlightBookingSystemException {
        try (Scanner sc = new Scanner(new File(RESOURCE))) {
            int line_idx = 1;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] properties = line.split(SEPARATOR, -1);
                if (properties.length < 7) {
                    throw new FlightBookingSystemException("Insufficient data on line " + line_idx);
                }
                try {
                    int id = Integer.parseInt(properties[0]);
                    String flightNumber = properties[1];
                    String origin = properties[2];
                    String destination = properties[3];
                    LocalDate departureDate = LocalDate.parse(properties[4]);
                    int capacity = Integer.parseInt(properties[5]);
                    boolean isDeleted = Boolean.parseBoolean(properties[6]);
                    double price = (properties.length >= 8 && !properties[7].isEmpty()) ? Double.parseDouble(properties[7]) : 0.0;

                    if (!isDeleted) {
                        Flight flight = new Flight(id, flightNumber, origin, destination, departureDate, capacity, isDeleted, price);
                        fbs.addFlight(flight);
                    }
                } catch (NumberFormatException ex) {
                    throw new FlightBookingSystemException("Unable to parse flight id " + properties[0] + " on line " + line_idx + "\nError: " + ex);
                }
                line_idx++;
            }
        }
    }
    
    /**
     * Stores the current state of Flight objects from the given Flight Booking
     * System instance into the specified resource file.
     * 
     * @param fbs The Flight Booking System instance containing flights to be stored.
     * @throws IOException If there is an error writing to the data source.
     */
    
    @Override
    public void storeData(FlightBookingSystem fbs) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) {
            for (Flight flight : fbs.getFlights()) {
                out.print(flight.getId() + SEPARATOR);
                out.print(flight.getFlightNumber() + SEPARATOR);
                out.print(flight.getOrigin() + SEPARATOR);
                out.print(flight.getDestination() + SEPARATOR);
                out.print(flight.getDepartureDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + SEPARATOR);
                out.print(flight.getCapacity() + SEPARATOR);
                out.print(flight.isDeleted() + SEPARATOR);
                out.print(flight.getPrice());
                out.println(); // Move to the next line for the next flight
            }
        }
    }
    


}

