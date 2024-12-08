package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

/**
 * The {@code Command} interface defines the contract for all commands in the Flight Booking System application.
 * Commands are operations that can be executed on the Flight Booking System, such as adding or canceling bookings,
 * listing flights, customers, or bookings, and performing other administrative tasks.
 * 
 * Each command must implement the {@code execute} method, which specifies how the command operates on the Flight
 * Booking System. Commands may throw a {@code FlightBookingSystemException} to indicate specific errors that occur
 * during command execution.
 * 
 * Additionally, the interface provides a static {@code HELP_MESSAGE} that outlines the available commands and their
 * descriptions, which can be printed to provide guidance on command usage within the application.
 * 
 * @author Bibek
 * @author Solomon
 * @version 1.0
 * @since 2024-06-10
 * @see Command
 */
public interface Command {
	
	  /**
     * Static constant HELP_MESSAGE provides a comprehensive list of commands supported by the Flight Booking System.
     * Each command is described briefly to aid users in understanding its purpose and usage.
     */

    public static final String HELP_MESSAGE = "Commands:\n"
        + "\tlistflights                               print all flights\n"
        + "\tlistcustomers                             print all customers\n"
        + "\tlistbookings                              print all bookings\n"
        + "\taddflight                                 add a new flight\n"
        + "\taddcustomer                               add a new customer\n"
        + "\tshowflight [flight id]                    show flight details\n"
        + "\tshowcustomer [customer id]                show customer details\n"
        + "\tdeletecustomer [customer id]			   delete a customer details\n"
        + "\tdeleteflight [flight id]                  delete a Flight \n"
        + "\taddbooking [customer id] [flight id]      add a new booking\n"
        + "\tcancelbooking [customer id] [flight id]   cancel a booking\n"
        + "\teditbooking [booking id] [flight id]      update a booking\n"
        + "\tloadgui                                   loads the GUI version of the app\n"
        + "\thelp                                      prints this help message\n"
        + "\texit                                      exits the program";
    
    /**
     * Executes the command on the specified Flight Booking System.
     * 
     * @param flightBookingSystem The Flight Booking System on which the command operates.
     * @throws FlightBookingSystemException If an error specific to the Flight Booking System occurs during execution.
     */

    
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException;
    
}
