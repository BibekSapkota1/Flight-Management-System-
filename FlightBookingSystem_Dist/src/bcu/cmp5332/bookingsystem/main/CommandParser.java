package bcu.cmp5332.bookingsystem.main;

import bcu.cmp5332.bookingsystem.commands.LoadGUI;
import bcu.cmp5332.bookingsystem.commands.ShowCustomer;
import bcu.cmp5332.bookingsystem.commands.ShowFlight;
import bcu.cmp5332.bookingsystem.commands.UpdateBooking;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.Flight.FlightClass;
import bcu.cmp5332.bookingsystem.commands.ListFlights;
import bcu.cmp5332.bookingsystem.commands.AddFlight;
import bcu.cmp5332.bookingsystem.commands.CancelBooking;
import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.commands.DeleteCustomer;
import bcu.cmp5332.bookingsystem.commands.Help;
import bcu.cmp5332.bookingsystem.commands.ListBooking;
import bcu.cmp5332.bookingsystem.commands.ListCustomers;
import bcu.cmp5332.bookingsystem.commands.AddBooking;
import bcu.cmp5332.bookingsystem.commands.AddCustomer;
import bcu.cmp5332.bookingsystem.commands.DeleteFlight;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * The CommandParser class parses user input to create appropriate Command objects.
 * It handles various commands related to managing flights, customers, and bookings
 * within a flight booking system.
 * 
 * @author Bibek
 * @author Solomon
 * @version 1.0
 * @since 2024-06-10
 */

public class CommandParser {
	
	  /**
     * Parses the user input and returns the corresponding Command object.
     * 
     * @param line The user input command line.
     * @return The Command object based on the parsed input.
     * @throws IOException If there is an error reading input.
     * @throws FlightBookingSystemException If there is an error parsing the command or creating Command objects.
     */
    
    public static Command parse(String line) throws IOException, FlightBookingSystemException {
        try {
            String[] parts = line.split(" ", 3);
            String cmd = parts[0];

            
            if (cmd.equals("addflight")) {
            	  return parseAddFlight(parts);     
            } else if (cmd.equals("addcustomer")) {
            	return parseAddCustomer(parts);
            } else if (cmd.equals("loadgui")) {
                return new LoadGUI();
            } else if (parts.length == 1) {
                if (line.equals("listflights")) {
                    return new ListFlights();
                } else if (line.equals("listcustomers")) {
                	 return new ListCustomers();
                }
                else if (line.equals("listbookings")) {
                    return new ListBooking();
                } else if (cmd.equals("addbooking")) {
                    return parseAddBooking(parts);
                }
                else if (cmd.equals("showcustomer")) {
                    int customerId = Integer.parseInt(parts[1]);
                    return new ShowCustomer(customerId);
                }
                else if (cmd.equals("showflight")) {
                    int flightId = Integer.parseInt(parts[1]);
                    return new ShowFlight(flightId);
               
                    
                } else if (line.equals("help")) {
                    return new Help();
                }
            } else if (parts.length == 2) {
                int id = Integer.parseInt(parts[1]);

                if (cmd.equals("showflight")) {
                	return new ShowFlight(id);
                }else if (cmd.equals("deleteflight")) {
                    return new DeleteFlight(id);
                } else if (cmd.equals("showcustomer")) {
                	 return new ShowCustomer(id);
                } else if (cmd.equals("deletecustomer")) {
                	return new DeleteCustomer(id);
                }
            } else if (parts.length == 3) {
                

                if (cmd.equals("addbooking")) {
                	return parseAddBooking(parts);
                } else if (cmd.equals("editbooking")) {
                	int customerId = Integer.parseInt(parts[1]);
               	 int flightId = Integer.parseInt(parts[2]);
               	 LocalDate newBookingDate = parseDateWithAttempts(new BufferedReader(new InputStreamReader(System.in)));
               	 Flight.FlightClass newFlightClass = parseFlightClassWithAttempts(new BufferedReader(new InputStreamReader(System.in)));

                    return new UpdateBooking(customerId, flightId, newBookingDate, newFlightClass);
                } else if (cmd.equals("cancelbooking")) {
                	int customerId = Integer.parseInt(parts[1]);
                	int flightId = Integer.parseInt(parts[2]);
                	return new CancelBooking(customerId, flightId);
                }
            }
        } catch (NumberFormatException ex) {

        }

        throw new FlightBookingSystemException("Invalid commandsssss.");
    }
    
    /**
     * Parses a flight class input from user with multiple attempts.
     * 
     * @param bufferedReader Reader object to read input.
     * @return The parsed FlightClass enumeration value.
     * @throws IOException If there is an error reading input.
     * @throws FlightBookingSystemException If there is an error parsing the flight class.
     */
    private static FlightClass parseFlightClassWithAttempts(BufferedReader bufferedReader) throws IOException, FlightBookingSystemException {
        int attempts = 5;
        while (attempts > 0) {
            attempts--;
            System.out.print("Enter flight class (FIRST_CLASS, BUSINESS_CLASS, ECONOMY_CLASS): ");
            try {
                String input = bufferedReader.readLine().toUpperCase();
                return Flight.FlightClass.valueOf(input);
            } catch (IllegalArgumentException | IOException ex) {
                System.out.println("Invalid input. Please enter a valid flight class. " + attempts + " attempts remaining...");
            }
        }

        throw new FlightBookingSystemException("Failed to parse flight class. Cannot update booking.");
    }

    /**
     * Parses the "addbooking" command from user input and creates an AddBooking Command object.
     * 
     * @param parts Array of input parts split from user command.
     * @return The AddBooking Command object.
     * @throws IOException If there is an error reading input.
     * @throws FlightBookingSystemException If there is an error parsing or creating the AddBooking command.
     */
    
	private static Command parseAddBooking(String[] parts) throws IOException, FlightBookingSystemException {
        if (parts.length != 3) {
            throw new FlightBookingSystemException("Invalid command format for addbooking. Correct format: addbooking <customer_id> <flight_id>");
        }

        int customerId = Integer.parseInt(parts[1]);
        int flightId = Integer.parseInt(parts[2]);
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Flight Class (ECONOMY_CLASS/BUSINESS_CLASS/FIRST_CLASS): ");
        String flightClassInput = reader.readLine().toUpperCase();
        
 
        Flight.FlightClass flightClass = Flight.FlightClass.valueOf(flightClassInput);
        LocalDate bookingDate = LocalDate.now();
        return new AddBooking(customerId, flightId, bookingDate, flightClass);
    }

    
	 /**
     * Parses the "addflight" command from user input and creates an AddFlight Command object.
     * 
     * @param parts Array of input parts split from user command.
     * @return The AddFlight Command object.
     * @throws IOException If there is an error reading input.
     * @throws FlightBookingSystemException If there is an error parsing or creating the AddFlight command.
     */

	private static Command parseAddFlight(String[] parts) throws IOException, FlightBookingSystemException {
    	  BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
          System.out.print("Flight Number: ");
          String flighNumber = reader.readLine();
          System.out.print("Origin: ");
          String origin = reader.readLine();
          System.out.print("Destination: ");
          String destination = reader.readLine();

          LocalDate departureDate = parseDateWithAttempts(reader);
          
          System.out.print("Capacity: ");
	        int capacity = Integer.parseInt(reader.readLine());
	      System.out.print("Price: ");
	      double price = Double.parseDouble(reader.readLine());

          return new AddFlight(flighNumber, origin, destination, departureDate,  capacity, price);
	}

	  /**
     * Parses the "addcustomer" command from user input and creates an AddCustomer Command object.
     * 
     * @param parts Array of input parts split from user command.
     * @return The AddCustomer Command object.
     * @throws IOException If there is an error reading input.
     * @throws FlightBookingSystemException If there is an error parsing or creating the AddCustomer command.
     */
	private static Command parseAddCustomer(String[] parts) throws IOException, FlightBookingSystemException {
    	 BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	        System.out.print("Name: ");
	        String name = reader.readLine();
	        System.out.print("Phone: ");
	        String phone = reader.readLine();
	        System.out.print("Email: ");
	        String email = reader.readLine();

	        return new AddCustomer(name, phone, email);
	}

	   /**
     * Parses a date input from user with multiple attempts.
     * 
     * @param br Reader object to read input.
     * @param attempts Number of attempts to read date input.
     * @return The parsed LocalDate object.
     * @throws IOException If there is an error reading input.
     * @throws FlightBookingSystemException If there is an error parsing the date.
     */
	private static LocalDate parseDateWithAttempts(BufferedReader br, int attempts) throws IOException, FlightBookingSystemException {
        if (attempts < 1) {
            throw new IllegalArgumentException("Number of attempts should be higher that 0");
        }
        while (attempts > 0) {
            attempts--;
            System.out.print("Departure Date (\"YYYY-MM-DD\" format): ");
            try {
                LocalDate departureDate = LocalDate.parse(br.readLine());
                return departureDate;
            } catch (DateTimeParseException dtpe) {
                System.out.println("Date must be in YYYY-MM-DD format. " + attempts + " attempts remaining...");
            }
        }
        
        throw new FlightBookingSystemException("Incorrect departure date provided. Cannot create flight.");
    }
    
	 /**
     * Parses date input with default 3 attempts.
     * 
     * @param br Reader object to read input.
     * @return The parsed LocalDate object.
     * @throws IOException If there is an error reading input.
     * @throws FlightBookingSystemException If there is an error parsing the date.
     */
    private static LocalDate parseDateWithAttempts(BufferedReader br) throws IOException, FlightBookingSystemException {
        return parseDateWithAttempts(br, 3);
    }
    

}
