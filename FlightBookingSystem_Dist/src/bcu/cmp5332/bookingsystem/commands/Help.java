package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

/**
 * The {@code Help} class represents a command to print the help message for the Flight Booking System.
 * It implements the {@code Command} interface and is used to display a predefined help message that lists
 * all available commands and their descriptions.
 * 
 * Upon execution, the command prints the {@code HELP_MESSAGE} constant defined in the {@code Command} interface.
 * This message includes a list of commands supported by the system along with their descriptions.
 * 
 * @author Bibek
 * @author Solomon
 * @version 1.0
 * @since 2024-06-10
 * @see Command
 */

public class Help implements Command {

	  /**
     * Executes the command by printing the help message to the console.
     * 
     * @param flightBookingSystem The Flight Booking System on which the command operates (not used in this command).
     */
	
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) {
        System.out.println(Command.HELP_MESSAGE);
    }
}
