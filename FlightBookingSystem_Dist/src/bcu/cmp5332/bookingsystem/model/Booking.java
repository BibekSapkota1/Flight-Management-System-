package bcu.cmp5332.bookingsystem.model;

import java.time.LocalDate;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Flight.FlightClass;

/**
 * The `Booking` class represents a reservation made by a customer for a specific flight.
 * It contains information about the customer, flight, booking date, cancellation fee,
 * cancellation status, deletion status, booking ID, and flight class.
 * 
 * A booking can be canceled, updated, and provides methods to calculate cancellation
 * and rebooking fees based on the flight price. It also checks if a booking is completed
 * based on the current date compared to the flight's departure date.
 * 
 * Bookings are associated with a specific customer and flight, and can be uniquely identified
 * by their booking ID.
 * 
 * @author Bibek
 * @author Solomon
 * @version 1.0
 * @since 2024-06-10
 */
public class Booking {

    private Customer customer;
    private Flight flight;
    private LocalDate bookingDate;
    private double cancellationFee;
    private boolean canceled;
    private boolean deleted;
    private int bookingId;
    private FlightClass flightClass;

    /**
     * Constructs a new booking with the specified customer, flight, booking date,
     * deletion status, and flight class.
     * 
     * @param customer     The customer making the booking.
     * @param flight       The flight being booked.
     * @param bookingDate  The date when the booking is made.
     * @param deleted      The deletion status of the booking.
     * @param flightClass  The class of the flight (Economy, Business, First).
     */
    public Booking(Customer customer, Flight flight, LocalDate bookingDate, boolean deleted, FlightClass flightClass) {
        this.customer = customer;
        this.flight = flight;
        this.bookingDate = bookingDate;
        this.flightClass = flightClass;
        this.deleted = deleted;
        this.bookingId = FlightBookingSystem.getNewBookingId();
    }

    /**
     * Gets the customer associated with this booking.
     * 
     * @return The customer making the booking.
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Gets the flight associated with this booking.
     * 
     * @return The flight being booked.
     */
    public Flight getFlight() {
        return flight;
    }

    /**
     * Gets the date when the booking is made.
     * 
     * @return The booking date.
     */
    public LocalDate getBookingDate() {
        return bookingDate;
    }

    /**
     * Sets the customer associated with this booking.
     * 
     * @param customer The new customer making the booking.
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Sets the flight associated with this booking.
     * 
     * @param flight The new flight being booked.
     */
    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    /**
     * Sets the booking date.
     * 
     * @param bookingDate The new booking date.
     */
    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    /**
     * Gets the cancellation fee for this booking.
     * 
     * @return The cancellation fee.
     */
    public double getCancellationFee() {
        return cancellationFee;
    }

    /**
     * Sets the cancellation fee for this booking.
     * 
     * @param cancellationFee The new cancellation fee.
     */
    public void setCancellationFee(double cancellationFee) {
        this.cancellationFee = cancellationFee;
    }

    /**
     * Checks if the booking is canceled.
     * 
     * @return {@code true} if the booking is canceled, {@code false} otherwise.
     */
    public boolean isCanceled() {
        return canceled;
    }

    /**
     * Sets the cancellation status for this booking.
     * 
     * @param canceled The new cancellation status.
     */
    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    /**
     * Gets the details of the booking, including the flight information.
     * 
     * @return A string representation of the booking details.
     */
    public String getDetails() {
        return "Booking for Flight #" + flight.getId() + " on " + flight.getDepartureDate();
    }

    /**
     * Cancels the booking, removing the passenger from the associated flight
     * and setting the cancellation fee and status.
     * 
     * @throws FlightBookingSystemException If the booking is already completed.
     */
    public void cancelBooking() throws FlightBookingSystemException {
        if (isCompleted()) {
            throw new FlightBookingSystemException("Cannot cancel a completed booking.");
        }

        double cancellationFee = getCancellationFee();
        flight.removePassenger(customer);
        setCancellationFee(cancellationFee);
        setCanceled(true);
    }

    /**
     * Updates the booking by calculating and setting the rebook fee.
     * 
     * @throws FlightBookingSystemException If the booking is already completed.
     */
    public void updateBooking() throws FlightBookingSystemException {
        if (isCompleted()) {
            throw new FlightBookingSystemException("Cannot update a completed booking.");
        }

        double rebookFee = calculateRebookFee();
        setCancellationFee(rebookFee);
    }

    /**
     * Calculates the cancellation fee based on a percentage of the flight price.
     * 
     * @return The calculated cancellation fee.
     */
    public double calculateCancellationFee() {
        return 0.05 * getFlight().getPrice();
    }

    /**
     * Calculates the rebook fee based on a percentage of the flight price.
     * 
     * @return The calculated rebook fee.
     */
    public double calculateRebookFee() {
        return 0.02 * getFlight().getPrice();
    }

    /**
     * Checks if the booking is completed by comparing the current date with
     * the departure date of the associated flight.
     * 
     * @return {@code true} if the booking is completed, {@code false} otherwise.
     */
    public boolean isCompleted() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.isAfter(getFlight().getDepartureDate());
    }

    /**
     * Checks if the booking is marked as deleted.
     * 
     * @return {@code true} if the booking is deleted, {@code false} otherwise.
     */
    public boolean isDeleted() {
        return deleted;
    }

    /**
     * Sets the deletion status of the booking.
     * 
     * @param deleted The new deletion status.
     */
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * Gets the unique identifier for this booking.
     * 
     * @return The booking ID.
     */
    public int getId() {
        return this.bookingId;
    }

    /**
     * Gets the booking ID.
     * 
     * @return The booking ID.
     */
    public int getBookingId() {
        return bookingId;
    }

    /**
     * Sets the booking ID.
     * 
     * @param bookingId The new booking ID.
     */
    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    /**
     * Gets the flight class of this booking.
     * 
     * @return The flight class.
     */
    public FlightClass getFlightClass() {
        return flightClass;
    }

    /**
     * Sets the flight class of this booking.
     * 
     * @param flightClass The new flight class.
     */
    public void setFlightClass(FlightClass flightClass) {
        this.flightClass = flightClass;
    }

    /**
     * Checks if the booking is made for a specific customer.
     * 
     * @param customer The customer to check.
     * @return {@code true} if the booking is made for the specified customer, {@code false} otherwise.
     */
    public boolean isBookingForCustomer(Customer customer) {
        return this.customer.equals(customer);
    }
}
