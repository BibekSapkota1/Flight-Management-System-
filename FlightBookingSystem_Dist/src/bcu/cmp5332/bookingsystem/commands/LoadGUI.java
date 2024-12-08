package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import bcu.cmp5332.bookingsystem.gui.MainWindow;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;

/**
 * The {@code LoadGUI} class represents a command to load the graphical user interface (GUI)
 * of the Flight Booking System.
 * 
 * It implements the {@code Command} interface and is used to initialize and display the main
 * graphical window of the application.
 * 
 * Upon execution, an instance of {@code MainWindow} is created using the provided 
 * {@code FlightBookingSystem} instance, which serves as the backend data source for the GUI.
 * 
 * @author Bibek
 * @author Solomon
 * @version 1.0
 * @since 2024-06-10
 * @see Command
 */

public class LoadGUI implements Command {

	  /**
     * Executes the command by initializing and displaying the graphical user interface (GUI)
     * of the Flight Booking System.
     *
     * @param flightBookingSystem The Flight Booking System instance that provides backend data.
     * @throws FlightBookingSystemException If there is an error while loading or displaying the GUI.
     */
	
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        new MainWindow(flightBookingSystem);
    }
    
}
