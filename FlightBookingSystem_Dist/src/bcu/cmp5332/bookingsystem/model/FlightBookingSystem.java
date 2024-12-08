package bcu.cmp5332.bookingsystem.model;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

/**
 * The `FlightBookingSystem` class represents the core of the flight booking system.
 * It manages customers, flights, and bookings within the system.
 * It provides functionalities to add/remove customers, flights, and bookings,
 * retrieve customer/flight details, list all customers, flights, and bookings,
 * and check for the existence of customers and flights.
 * 
 * This class ensures that operations such as adding customers/flights and making bookings
 * are handled appropriately, and it interacts with the data layer for persistence.
 * 
 * The class also provides methods to list flights, customers, and bookings,
 * as well as specific functionalities to handle upcoming and all flights.
 * 
 * @author Your Name
 * @version 1.0
 * @since 2024-06-10
 */
public class FlightBookingSystem {
	
    private int lastCustomerId = 0;
    private static int lastBookingId = 0;

    private final LocalDate systemDate = LocalDate.parse("2024-06-10");

    private final Map<Integer, Customer> customers = new TreeMap<>();
    private final Map<Integer, Flight> flights = new TreeMap<>();
    private final Map<Integer, Booking> bookings = new TreeMap<>();

    /**
     * Retrieves the current system date.
     * 
     * @return The system date.
     */
    public LocalDate getSystemDate() {
        return systemDate;
    }

    /**
     * Retrieves an unmodifiable list of all flights in the system.
     * 
     * @return An unmodifiable list of Flight objects.
     */
    public List<Flight> getFlights() {
        List<Flight> out = new ArrayList<>();
        for (Flight flight : flights.values()) {
            if (!flight.isDeleted()) {
                out.add(flight);
            }
        }
        return Collections.unmodifiableList(out);
    }

    /**
     * Retrieves an unmodifiable list of all customers in the system.
     * 
     * @return An unmodifiable list of Customer objects.
     */
    public List<Customer> getCustomers() {
        List<Customer> out = new ArrayList<>();
        for (Customer customer : customers.values()) {
            if (!customer.isDeleted()) {
                out.add(customer);
            }
        }
        return Collections.unmodifiableList(out);
    }

    /**
     * Retrieves an unmodifiable list of all bookings in the system.
     * 
     * @return An unmodifiable list of Booking objects.
     */
    public List<Booking> getBookings() {
        List<Booking> out = new ArrayList<>();
        for (Booking booking : bookings.values()) {
            if (!booking.isDeleted()) {
                out.add(booking);
            }
        }
        return Collections.unmodifiableList(out);
    }

    /**
     * Retrieves a Flight object by its ID.
     * 
     * @param id The ID of the flight.
     * @return The Flight object.
     * @throws FlightBookingSystemException If there is no flight with the specified ID.
     */
    public Flight getFlightByID(int id) throws FlightBookingSystemException {
        if (!flights.containsKey(id) || flights.get(id).isDeleted()) {
            throw new FlightBookingSystemException("There is no active flight with that ID.");
        }
        return flights.get(id);
    }

    /**
     * Retrieves a Customer object by its ID.
     * 
     * @param id The ID of the customer.
     * @return The Customer object.
     * @throws FlightBookingSystemException If there is no customer with the specified ID.
     */
    public Customer getCustomerByID(int id) throws FlightBookingSystemException {
        if (!customers.containsKey(id) || customers.get(id).isDeleted()) {
            throw new FlightBookingSystemException("There is no active customer with that ID.");
        }
        return customers.get(id);
    }

    /**
     * Adds a new flight to the Flight Booking System.
     * 
     * @param flight The Flight object to be added.
     * @throws FlightBookingSystemException If there is a duplicate flight ID or
     *                                      a flight with the same number and departure date already exists.
     */
    public void addFlight(Flight flight) throws FlightBookingSystemException {
        if (flights.containsKey(flight.getId())) {
            throw new FlightBookingSystemException("Duplicate flight ID.");
        }
        for (Flight existing : flights.values()) {
            if (existing.getFlightNumber().equals(flight.getFlightNumber())
                && existing.getDepartureDate().isEqual(flight.getDepartureDate())) {
                throw new FlightBookingSystemException("A flight with the same number and departure date already exists.");
            }
        }
        flights.put(flight.getId(), flight);
    }

    /**
     * Adds a new customer to the Flight Booking System.
     * 
     * @param customer The Customer object to be added.
     * @throws FlightBookingSystemException If there is a duplicate customer ID.
     */
    public void addCustomer(Customer customer) throws FlightBookingSystemException {
        if (customers.containsKey(customer.getId())) {
            throw new FlightBookingSystemException("Duplicate customer ID.");
        }
        customers.put(customer.getId(), customer);
    }

    /**
     * Retrieves a new unique customer ID.
     * 
     * @return A new unique customer ID.
     */
    public int getNewCustomerId() {
        return ++lastCustomerId;
    }

    /**
     * Removes a flight from the system by marking it as deleted.
     * 
     * @param flightId The ID of the flight to be removed.
     * @throws FlightBookingSystemException If there is no flight with the specified ID.
     */
    public void removeFlightById(int flightId) throws FlightBookingSystemException {
        if (!flights.containsKey(flightId)) {
            throw new FlightBookingSystemException("There is no flight with that ID.");
        }
        Flight flight = flights.get(flightId);
        flight.setDeleted(true);

        try {
            FlightBookingSystemData.store(this);
        } catch (IOException e) {
            throw new FlightBookingSystemException("Error storing data to file: " + e.getMessage());
        }
    }

    /**
     * Retrieves a booking by customer and flight.
     * 
     * @param customer The customer associated with the booking.
     * @param flight   The flight associated with the booking.
     * @return The Booking object if found, otherwise null.
     */
    public Booking getBookingByCustomerAndFlight(Customer customer, Flight flight) {
        for (Customer c : customers.values()) {
            if (c.equals(customer)) {
                for (Booking booking : c.getBookings()) {
                    if (booking.getFlight().equals(flight)) {
                        return booking;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Retrieves a new unique booking ID.
     * 
     * @return A new unique booking ID.
     */
    public static int getNewBookingId() {
        return ++lastBookingId;
    }

    /**
     * Adds a new booking to the system.
     * 
     * @param booking The Booking object to be added.
     * @throws FlightBookingSystemException If the customer or flight associated with the booking is not found.
     */
    public void addBooking(Booking booking) throws FlightBookingSystemException {
        Customer customer = booking.getCustomer();
        Flight flight = booking.getFlight();

        if (customers.containsKey(customer.getId()) && flights.containsKey(flight.getId())) {
            int bookingId = getNewBookingId();
            bookings.put(bookingId, booking);
            customer.addBooking(booking);
            flight.addPassenger(customer);

        } else {
            throw new FlightBookingSystemException("Customer or Flight not found.");
        }
    }

    /**
     * Removes a customer from the system by marking them as deleted.
     * 
     * @param customerId The ID of the customer to be removed.
     * @throws FlightBookingSystemException If there is no customer with the specified ID.
     */
    public void removeCustomerById(int customerId) throws FlightBookingSystemException {
        if (!customers.containsKey(customerId)) {
            throw new FlightBookingSystemException("There is no customer with that ID.");
        }
        Customer customer = customers.get(customerId);
        customer.setDeleted(true);
        try {
            FlightBookingSystemData.store(this);
        } catch (IOException e) {
            throw new FlightBookingSystemException("Error storing data to file: " + e.getMessage());
        }
    }

    /**
     * Lists all flights in the system, including details such as ID, departure date, origin, destination,
     * and prices for economy, business, and first class.
     */
    public void listAllFlights() {
        List<Flight> flights = getFlights();

        System.out.printf("%-10s%-25s%-20s%-20s%-20s%-20s%-20s%n", "Flight #", "Departure Date", "Origin", "Destination", "First Class", "Business Class", "Economy Class");

        for (Flight flight : flights) {
            if (!flight.isDeleted()) {
                double firstClassPrice = flight.calculateDynamicPrice(Flight.FlightClass.FIRST_CLASS);
                double businessPrice = flight.calculateDynamicPrice(Flight.FlightClass.BUSINESS_CLASS);
                double economyPrice = flight.calculateDynamicPrice(Flight.FlightClass.ECONOMY_CLASS);

                System.out.printf("%-10d%-25s%-20s%-20s%-20.2f%-20.2f%-20.2f%n",
                        flight.getId(),
                        flight.getDepartureDate(),
                        flight.getOrigin(),
                        flight.getDestination(),
                        firstClassPrice,
                        businessPrice,
                        economyPrice);
            }
        }
    }

    /**
     * Lists upcoming flights in the system, including details such as ID, departure date, origin, destination,
     * and prices for economy, business, and first class.
     */
	    public void listUpcomingFlights() {
	        List<Flight> flights = getFlights();
	
	        System.out.printf("%-10s%-25s%-20s%-20s%-20s%-20s%-20s%n", "Flight #", "Departure Date", "Origin", "Destination", "First Class", "Business Class", "Economy Class");
	
	        for (Flight flight : flights) {
	            if (!flight.hasDeparted() && !flight.isDeleted()) {
	                double firstClassPrice = flight.calculateDynamicPrice(Flight.FlightClass.FIRST_CLASS);
	                double businessPrice = flight.calculateDynamicPrice(Flight.FlightClass.BUSINESS_CLASS);
	                double economyPrice = flight.calculateDynamicPrice(Flight.FlightClass.ECONOMY_CLASS);
	
	                System.out.printf("%-10d%-25s%-20s%-20s%-20.2f%-20.2f%-20.2f%n",
	                        flight.getId(),
	                        flight.getDepartureDate(),
	                        flight.getOrigin(),
	                        flight.getDestination(),
	                        firstClassPrice,
	                        businessPrice,
	                        economyPrice);
	            }
	        }
	    }

    /**
     * Lists all customers in the system, including details such as ID, name, phone, and email.
     */
    public void listAllCustomers() {
        List<Customer> customers = getCustomers();

        System.out.printf("%-15s%-15s%-15s%-20s%n", "Customer ID", "Name", "Phone", "Email");

        for (Customer customer : customers) {
            if (!customer.isDeleted()) {
                System.out.printf("%-15d%-15s%-15s%-20s%n",
                        customer.getId(),
                        customer.getName(),
                        customer.getPhone(),
                        customer.getEmail());
            }
        }
    }

    /**
     * Lists all bookings in the system, including details such as ID, customer, flight, booking date, and flight class.
     */
    public void listAllBookings() {
        List<Booking> bookings = getBookings();

        System.out.printf("%-15s%-25s%-20s%-25s%-15s%n", "Booking ID", "Customer", "Flight", "Booking Date", "Flight Class");

        for (Booking booking : bookings) {
            if (!booking.isDeleted()) {
                String customerName = booking.getCustomer().getName();
                String customerDetails = "Customer " + booking.getCustomer().getId() + " - " + customerName;

                String flightDetails = "Flight " + booking.getFlight().getId() + " - " + booking.getFlight().getFlightNumber();
                LocalDate bookingDate = booking.getBookingDate();
                Flight.FlightClass flightClass = booking.getFlightClass();

                System.out.printf("%-15d%-25s%-20s%-25s%-15s%n",
                        booking.getId(),
                        customerDetails,
                        flightDetails,
                        bookingDate,
                        flightClass);
            }
        }
    }

    /**
     * Checks if a flight exists in the system and is active.
     * 
     * @param flightId The ID of the flight to check.
     * @return true if the flight exists and is active, false otherwise.
     */
    public boolean flightExists(int flightId) {
        return flights.containsKey(flightId) && !flights.get(flightId).isDeleted();
    }

    /**
     * Checks if a customer exists in the system and is active.
     * 
     * @param customerId The ID of the customer to check.
     * @return true if the customer exists and is active, false otherwise.
     */
    public boolean customerExists(int customerId) {
        return customers.containsKey(customerId) && !customers.get(customerId).isDeleted();
    }
}
