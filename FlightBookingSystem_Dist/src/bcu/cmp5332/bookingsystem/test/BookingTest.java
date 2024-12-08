package bcu.cmp5332.bookingsystem.test;

import static org.junit.jupiter.api.Assertions.*;


import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.Flight.FlightClass;

class BookingTest {

	@Test
    public void testConstructor() throws FlightBookingSystemException {
        Customer customer = new Customer(1, "Solomon", "123456789", "Solomon@gmail.com",false);
        Flight flight = new Flight(1, "KPL1234", "Nepal", "China", LocalDate.now(), 360, false, 150000);
        LocalDate bookingDate = LocalDate.now();
        Booking booking = new Booking(customer, flight, bookingDate, false, FlightClass.BUSINESS_CLASS);

        assertEquals(customer, booking.getCustomer());
        assertEquals(flight, booking.getFlight());
        assertEquals(bookingDate, booking.getBookingDate());
        assertEquals(FlightClass.BUSINESS_CLASS, booking.getFlightClass());
        assertFalse(booking.isDeleted());
    }

    @Test
    public void testSetterMethods() throws FlightBookingSystemException {
        Customer customer1 = new Customer(1, "Bibek", "9841141757", "Bibek@gmail.com",false);
        Customer customer2 = new Customer(2, "Solomon", "9841141858", "Solomon@gmail.com",false);
        Flight flight1 = new Flight(1, "KPL1234", "Nepal", "Japan", LocalDate.now(), 1200, false, 250000);
        Flight flight2 = new Flight(2, "KPL1234", "Nepal", "Qatar", LocalDate.now().plusDays(1), 1100, false, 150000);
        LocalDate bookingDate1 = LocalDate.now();
        LocalDate bookingDate2 = LocalDate.now().plusDays(2);

        Booking booking = new Booking(customer1, flight1, bookingDate1, false, FlightClass.ECONOMY_CLASS);

        booking.setCustomer(customer2);
        booking.setFlight(flight2);
        booking.setBookingDate(bookingDate2);
        booking.setFlightClass(FlightClass.FIRST_CLASS);
        booking.setDeleted(true);

        assertEquals(customer2, booking.getCustomer());
        assertEquals(flight2, booking.getFlight());
        assertEquals(bookingDate2, booking.getBookingDate());
        assertEquals(FlightClass.FIRST_CLASS, booking.getFlightClass());
        assertTrue(booking.isDeleted());
    }
    
    @Test
    public void testCancellationFeeCalculation() throws FlightBookingSystemException {
        Customer customer = new Customer(1, "Bibek", "9841141757", "Bibek@example.com", false);
        Flight flight = new Flight(1, "KPL1234", "KTM", "QTR", LocalDate.now(), 1500, false, 150000);
        LocalDate bookingDate = LocalDate.now();
        Booking booking = new Booking(customer, flight, bookingDate, false, FlightClass.ECONOMY_CLASS);

        double expectedCancellationFee = 0.1 * flight.getPrice();
        booking.setCancellationFee(expectedCancellationFee);

        assertEquals(expectedCancellationFee, booking.getCancellationFee());
    }

    @Test
    public void testBookingCancellation() throws FlightBookingSystemException {
        Customer customer = new Customer(1, "Solomon", "941141858", "solo@example.com", false);
        Flight flight = new Flight(1, "KPL1234", "KTM", "QTR", LocalDate.now().plusDays(7), 600, false, 150000);
        LocalDate bookingDate = LocalDate.now();
        Booking booking = new Booking(customer, flight, bookingDate, false, FlightClass.BUSINESS_CLASS);

        booking.cancelBooking();

        assertTrue(booking.isCanceled());
    }
    
    @Test
    public void testBookingUpdate() throws FlightBookingSystemException {
        Customer customer = new Customer(1, "Solomon", "9841141858", "solo@example.com", false);
        Flight flight = new Flight(1, "KPL1234", "KTM", "QTR", LocalDate.now().plusDays(14), 6000, false, 15000);
        LocalDate bookingDate = LocalDate.now();
        Booking booking = new Booking(customer, flight, bookingDate, false, FlightClass.FIRST_CLASS);

        double initialCancellationFee = booking.getCancellationFee();
        booking.updateBooking();

        double updatedCancellationFee = booking.getCancellationFee();
        assertNotEquals(initialCancellationFee, updatedCancellationFee);
    }


}
