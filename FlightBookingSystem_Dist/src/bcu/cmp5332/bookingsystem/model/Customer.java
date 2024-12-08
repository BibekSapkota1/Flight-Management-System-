package bcu.cmp5332.bookingsystem.model;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import java.util.ArrayList;
import java.util.List;

/**
 * The `Customer` class represents a customer of the flight booking system.
 * It encapsulates customer information including ID, name, phone number, email address,
 * and deletion status. The class manages a list of bookings associated with the customer.
 * 
 * Customers can have multiple bookings, each linked to a specific flight. Bookings can be
 * added or removed, and the class provides methods to access detailed information about
 * the customer and their bookings.
 * 
 * Example usage:
 * ```
 * Customer customer = new Customer(1, "John Doe", "+123456789", "john.doe@example.com", false);
 * ```
 * 
 * @author Bibek
 * @author Solomon
 * @version 1.0
 * @since 2024-06-10
 */
public class Customer {
    
    private int id;
    private String name;
    private String phone;
    private String email;
    private boolean deleted;
    private final List<Booking> bookings = new ArrayList<>();

    /**
     * Constructs a new customer with the specified ID, name, phone number, email address,
     * and deletion status.
     * 
     * @param id      The unique identifier for the customer.
     * @param name    The name of the customer.
     * @param phone   The phone number of the customer.
     * @param email   The email address of the customer.
     * @param deleted The deletion status of the customer (true if deleted, false otherwise).
     */
    public Customer(int id, String name, String phone, String email, boolean deleted) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.deleted = deleted;
    }
    
    /**
     * Returns the unique identifier of the customer.
     * 
     * @return The customer ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the name of the customer.
     * 
     * @return The name of the customer.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the phone number of the customer.
     * 
     * @return The phone number of the customer.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Returns the list of bookings associated with the customer.
     * 
     * @return The list of bookings.
     */
    public List<Booking> getBookings() {
        return bookings;
    }

    /**
     * Returns the email address of the customer.
     * 
     * @return The email address of the customer.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the deletion status of the customer.
     * 
     * @return {@code true} if the customer is deleted, {@code false} otherwise.
     */
    public boolean isDeleted() {
        return deleted;
    }

    /**
     * Sets the unique identifier of the customer.
     * 
     * @param id The new customer ID.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the name of the customer.
     * 
     * @param name The new name of the customer.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the phone number of the customer.
     * 
     * @param phone The new phone number of the customer.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Sets the email address of the customer.
     * 
     * @param email The new email address of the customer.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the deletion status of the customer.
     * 
     * @param deleted The new deletion status of the customer.
     */
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * Adds a booking to the list of bookings associated with the customer.
     * 
     * @param booking The booking to add.
     * @throws FlightBookingSystemException If the booking is null.
     */
    public void addBooking(Booking booking) throws FlightBookingSystemException {
        if (booking == null) {
            throw new FlightBookingSystemException("Cannot add a null booking.");
        }
        bookings.add(booking);
    }

    /**
     * Removes a booking from the list of bookings associated with the customer.
     * This method marks the booking as deleted.
     * 
     * @param booking The booking to remove.
     */
    public void removeBooking(Booking booking) {
        booking.setDeleted(true);
    }

    /**
     * Generates a detailed string representation of the customer,
     * including their ID, name, phone number, email address, and a list of bookings.
     * 
     * @return A string representation of the customer details.
     */
    public String getDetails() {
        StringBuilder details = new StringBuilder("Customer #" + id + "\n");
        int count = 1;

        for (Booking booking : bookings) {
            Flight flight = booking.getFlight();
            details.append(count).append(". - Flight #").append(flight.getFlightNumber())
                   .append(" from ").append(flight.getOrigin())
                   .append(" to ").append(flight.getDestination())
                   .append(" on ").append(flight.getDepartureDate())
                   .append(", Class: ").append(booking.getFlightClass())
//                 .append(", Price: ").append(booking.getPrice())
                   .append(booking.isDeleted() ? " (cancelled)" : "")
                   .append("\n");
            count++;
        }

        return details.toString();
    }

    /**
     * Generates a short string representation of the customer,
     * including their ID, name, phone number, and email address.
     * 
     * @return A short string representation of the customer.
     */
    public String getDetailsShort() {
        StringBuilder details = new StringBuilder("Customer #" + id + "\n");
        details.append("Name: ").append(name).append("\n");
        details.append("Phone: ").append(phone).append("\n");
        details.append("Email: ").append(email).append("\n");

        return details.toString();
    }
}
