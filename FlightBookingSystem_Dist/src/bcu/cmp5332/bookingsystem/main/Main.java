package bcu.cmp5332.bookingsystem.main;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.*;


/**
 * The {@code Main} class serves as the entry point for the Flight Booking System application.
 * It initializes the application, handles user input from the command line, and interacts with
 * the {@code FlightBookingSystem} data model to perform operations based on user commands.
 * <p>
 * The application loads an existing flight booking system data from a file, provides a command-line
 * interface for users to execute commands, and stores the updated data back to the file upon exit.
 * 
 * @author Bibek
 * @author Solomon
 * @version 1.0
 * @since 2024-06-15
 * @see Command
 * @see FlightBookingSystem
 * @see FlightBookingSystemData
 */
public class Main {

	 /**
     * The main method to start the Flight Booking System application.
     * 
     * @param args Command-line arguments (not used).
     * @throws IOException if there is an error reading from the input stream or writing to the file.
     * @throws FlightBookingSystemException if there is an error loading or storing the flight booking system data,
     *                                      or if there is an error executing a command.
     */
    public static void main(String[] args) throws IOException, FlightBookingSystemException {
        
        FlightBookingSystem fbs = FlightBookingSystemData.load();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Flight Booking System");
        System.out.println("Enter 'help' to see a list of available commands.");
        while (true) {
            System.out.print("> ");
            String line = br.readLine();
            if (line.equals("exit")) {
                break;
            }

            try {
                Command command = CommandParser.parse(line);
                command.execute(fbs);
            } catch (FlightBookingSystemException ex) {
                System.out.println(ex.getMessage());
            }
        }
        FlightBookingSystemData.store(fbs);
        System.exit(0);
    }
}
