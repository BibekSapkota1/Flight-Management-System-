package bcu.cmp5332.bookingsystem.model;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The `Flight` class represents a flight within the booking system.
 * It contains details such as flight ID, flight number, origin, destination,
 * departure date, capacity, price, and deletion status.
 * 
 * Flights can have passengers booked on them, which are managed through methods
 * to add and remove passengers. It also provides methods to determine if the flight
 * is fully booked, has departed, and calculates dynamic pricing based on various factors.
 * 
 * The class utilizes enums for different flight classes and provides methods to get
 * flight details in both short and long formats.
 * 
 * @author Bibek
 * @author Solomon
 * @version 1.0
 * @since 2024-06-10
 */
public class Flight {

    private int id;
    private String flightNumber;
    private String origin;
    private String destination;
    private LocalDate departureDate;
    private int capacity;
    private double price;
    private boolean isDeleted;

    private Map<FlightClass, Double> classPrices;

    private final Set<Customer> passengers;

    /**
     * Constructs a new flight with the specified ID, flight number, origin, destination,
     * departure date, capacity, deletion status, and base price.
     * 
     * @param id            The unique identifier for the flight.
     * @param flightNumber  The flight number.
     * @param origin        The origin airport code.
     * @param destination   The destination airport code.
     * @param departureDate The date and time of departure.
     * @param capacity      The maximum capacity of the flight.
     * @param isDeleted     The deletion status of the flight.
     * @param price         The base price of the flight.
     */
    public Flight(int id, String flightNumber, String origin, String destination, LocalDate departureDate, int capacity, boolean isDeleted, double price) {
        this.id = id;
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departureDate = departureDate;
        this.capacity = capacity;
        this.price = price;
        this.isDeleted = isDeleted;

        passengers = new HashSet<>();

        classPrices = new HashMap<>();
        classPrices.put(FlightClass.FIRST_CLASS, 3.0);
        classPrices.put(FlightClass.BUSINESS_CLASS, 1.8);
        classPrices.put(FlightClass.ECONOMY_CLASS, 1.0);
    }

    /**
     * Returns the unique identifier of the flight.
     * 
     * @return The flight ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the flight.
     * 
     * @param id The new flight ID.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the flight number.
     * 
     * @return The flight number.
     */
    public String getFlightNumber() {
        return flightNumber;
    }

    /**
     * Sets the flight number.
     * 
     * @param flightNumber The new flight number.
     */
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    /**
     * Returns the origin airport code.
     * 
     * @return The origin airport code.
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * Sets the origin airport code.
     * 
     * @param origin The new origin airport code.
     */
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    /**
     * Returns the destination airport code.
     * 
     * @return The destination airport code.
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Sets the destination airport code.
     * 
     * @param destination The new destination airport code.
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * Returns the departure date and time of the flight.
     * 
     * @return The departure date and time.
     */
    public LocalDate getDepartureDate() {
        return departureDate;
    }

    /**
     * Sets the departure date and time of the flight.
     * 
     * @param departureDate The new departure date and time.
     */
    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    /**
     * Returns a list of passengers booked on the flight.
     * 
     * @return The list of passengers.
     */
    public List<Customer> getPassengers() {
        return new ArrayList<>(passengers);
    }

    /**
     * Returns a short string representation of the flight, including flight ID,
     * flight number, origin, destination, and departure date.
     * 
     * @return A short string representation of the flight.
     */
    public String getDetailsShort() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "Flight #" + id + " - " + flightNumber + " - " + origin + " to " + destination + " on " + departureDate.format(dtf);
    }

    /**
     * Returns a detailed string representation of the flight, including flight ID,
     * flight number, origin, destination, departure date, and list of passengers.
     * 
     * @return A detailed string representation of the flight.
     * @throws FlightBookingSystemException If there is an issue retrieving passenger details.
     */
    public String getDetailsLong() throws FlightBookingSystemException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        StringBuilder details = new StringBuilder("Flight #" + id + " - " + flightNumber + "\n");
        details.append("From: ").append(origin).append("\n");
        details.append("To: ").append(destination).append("\n");
        details.append("Departure Date: ").append(departureDate.format(dtf)).append("\n");
        details.append("Passengers:\n");
        for (Customer passenger : passengers) {
            details.append("- ").append(passenger.getName()).append("\n");
        }
        return details.toString();
    }

    /**
     * Returns the maximum capacity of the flight.
     * 
     * @return The maximum capacity.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Sets the maximum capacity of the flight.
     * 
     * @param capacity The new maximum capacity.
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Returns the base price of the flight.
     * 
     * @return The base price.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the base price of the flight.
     * 
     * @param price The new base price.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Adds a passenger to the flight.
     * 
     * @param passenger The customer to add as a passenger.
     */
    public void addPassenger(Customer passenger) {
        passengers.add(passenger);
    }

    /**
     * Checks if the flight is fully booked.
     * 
     * @return {@code true} if the flight is fully booked, {@code false} otherwise.
     */
    public boolean isFullyBooked() {
        return passengers.size() >= capacity;
    }

    /**
     * Removes a passenger from the flight.
     * 
     * @param passenger The customer to remove from the passengers list.
     */
    public void removePassenger(Customer passenger) {
        passengers.remove(passenger);
    }

    /**
     * Returns the deletion status of the flight.
     * 
     * @return {@code true} if the flight is deleted, {@code false} otherwise.
     */
    public boolean isDeleted() {
        return isDeleted;
    }

    /**
     * Sets the deletion status of the flight.
     * 
     * @param isDeleted The new deletion status.
     */
    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * Checks if the flight has departed based on the current date and time
     * compared to the departure date.
     * 
     * @return {@code true} if the flight has departed, {@code false} otherwise.
     */
    public boolean hasDeparted() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime departureDateTime = departureDate.atStartOfDay();
        return departureDateTime.isBefore(currentDateTime);
    }

    /**
     * Returns the number of seats booked on the flight.
     * 
     * @return The number of booked seats.
     */
    public int getBookedSeats() {
        return passengers.size();
    }

    /**
     * Calculates the dynamic price for a given flight class based on departure date,
     * current date, booking status, and flight occupancy.
     * 
     * @param flightClass The class of the flight (First, Business, Economy).
     * @return The dynamically calculated price for the flight class.
     */
    public double calculateDynamicPrice(FlightClass flightClass) {
        if (hasDeparted()) {
            return getPrice(flightClass);
        } else {
            LocalDate currentDate = LocalDate.now();
            long daysLeft = ChronoUnit.DAYS.between(currentDate, departureDate);
            double maxDaysForPriceFactor = 15.0;

            if (daysLeft <= maxDaysForPriceFactor) {
                double daysPriceFactor = 1.0 + 0.1 * (maxDaysForPriceFactor - daysLeft);
                double classPriceFactor = classPrices.get(flightClass);
                double bookedSeatsFactor = 1.0 + 0.1 * ((double) getBookedSeats() / (double) getCapacity());
                return getPrice(flightClass) * daysPriceFactor * classPriceFactor * bookedSeatsFactor;
            } else {
                return getPrice(flightClass);
            }
        }
    }

    /**
     * Returns the price for a given flight class.
     * 
     * @param flightClass The class of the flight (First, Business, Economy).
     * @return The price for the specified flight class.
     */
    public double getPrice(FlightClass flightClass) {
        return price * classPrices.get(flightClass);
    }

    /**
     * Returns the dynamic price for a given flight class, considering various factors
     * such as departure date, current date, booking status, and flight occupancy.
     * 
     * @param flightClass The class of the flight (First, Business, Economy).
     * @return The dynamically calculated price for the flight class.
     */
    public double getDynamicPrice(FlightClass flightClass) {
        return calculateDynamicPrice(flightClass);
    }

    /**
     * Retrieves the enum representation of the flight class based on a string input.
     * 
     * @param flightClassString The string representation of the flight class.
     * @return The enum value corresponding to the flight class.
     * @throws IllegalArgumentException If the input flight class string is invalid.
     */
    public static FlightClass getFlightClass(String flightClassString) throws IllegalArgumentException {
        if (flightClassString == null || flightClassString.isEmpty()) {
            throw new IllegalArgumentException("Flight class cannot be null or empty");
        }
        try {
            return FlightClass.valueOf(flightClassString.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid flight class: " + flightClassString, e);
        }
    }

    /**
     * Enum representing different classes of a flight.
     */
    public enum FlightClass {
        FIRST_CLASS,
        BUSINESS_CLASS,
        ECONOMY_CLASS
    }

    /**
     * Placeholder method for getting the arrival time of the flight.
     * 
     * @return The arrival time of the flight.
     */
    public LocalDateTime getArrivalTime() {
        // Placeholder method, to be implemented as per requirements
        return null;
    }

    /**
     * Placeholder method for getting the number of available seats on the flight.
     * 
     * @return The number of available seats.
     */
    public int getAvailableSeats() {
        // Placeholder method, to be implemented as per requirements
        return capacity - passengers.size();
    }
}
