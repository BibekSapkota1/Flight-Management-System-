package bcu.cmp5332.bookingsystem.test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.Flight.FlightClass;

import org.junit.jupiter.api.Test;

class CustomerTest {
	
	 @Test
	    void testCustomerConstructor() {
	        int id = 1;
	        String name = "Bibek";
	        String phone = "123456789";
	        String email = "bibek@gmail.com";
	        boolean deleted = false;

	        Customer customer = new Customer(id, name, phone, email, deleted);

	        assertEquals(id, customer.getId());
	        assertEquals(name, customer.getName());
	        assertEquals(phone, customer.getPhone());
	        assertEquals(email, customer.getEmail());
	        assertEquals(deleted, customer.isDeleted());
	        assertEquals(0, customer.getBookings().size()); 
	    }

	 @Test
	    void testGettersAndSetters() {
	        // Arrange
	        int id = 1;
	        String name = "Solomon";
	        String phone = "123456789";
	        String email = "solomon@gmail.com";
	        boolean deleted = false;

	        Customer customer = new Customer(id, name, phone, email, deleted);

	        assertEquals(id, customer.getId());
	        assertEquals(name, customer.getName());
	        assertEquals(phone, customer.getPhone());
	        assertEquals(email, customer.getEmail());
	        assertEquals(deleted, customer.isDeleted());
	        assertEquals(0, customer.getBookings().size()); 

	        int newId = 2;
	        String newName = "Pradip";
	        String newPhone = "123456789";
	        String newEmail = "pradip@example.com";
	        boolean newDeleted = true;

	        customer.setId(newId);
	        customer.setName(newName);
	        customer.setPhone(newPhone);
	        customer.setEmail(newEmail);
	        customer.setDeleted(newDeleted);

	        assertEquals(newId, customer.getId());
	        assertEquals(newName, customer.getName());
	        assertEquals(newPhone, customer.getPhone());
	        assertEquals(newEmail, customer.getEmail());
	        assertEquals(newDeleted, customer.isDeleted());
	    }
	 
    @Test
    void testAddBooking() {
        try {
            Customer customer = new Customer(1, "Bibek", "123456789", "bibek@gmail.com", false);
            Flight flight = new Flight(1, "KPL1234", "KTM", "QTR", LocalDate.now().plusDays(10), 100, false, 10000.0);
            Booking booking = new Booking(customer, flight, LocalDate.now(), false, FlightClass.ECONOMY_CLASS);

            customer.addBooking(booking);

            assertTrue(customer.getBookings().contains(booking));
        } catch (FlightBookingSystemException e) {
            fail("Exception should not be thrown here.");
        }
    }

    @Test
    void testAddNullBooking() {
        Customer customer = new Customer(1, "Bibek", "123456789", "bibek@gmail.com", false);

        assertThrows(FlightBookingSystemException.class, () -> {
            customer.addBooking(null);
        });
    }

    @Test
    void testRemoveBooking() {
        Customer customer = new Customer(1, "Solomon", "123456789", "solomon@gmail.com", false);
        Flight flight = new Flight(1, "KPL1234", "Qatar", "India", LocalDate.now().plusDays(10), 100, false, 10000.0);
        Booking booking = new Booking(customer, flight, LocalDate.now(), false, FlightClass.ECONOMY_CLASS);

        customer.removeBooking(booking);

        assertTrue(booking.isDeleted());
        assertFalse(customer.getBookings().contains(booking));
    }

   

    @Test
    void testGetDetailsShort() {
        Customer customer = new Customer(1, "Solomon", "123456789", "solomon@gmail.com", false);

        String expectedDetailsShort = "Customer #1\n" +
                "Name: Solomon\n" +
                "Phone: 123456789\n" +
                "Email: solomon@gmail.com\n";

        assertEquals(expectedDetailsShort, customer.getDetailsShort());
    }
    @Test
    void testCancelBooking() {
        try {
            Customer customer = new Customer(1, "Pradip", "123456789", "pradip@gmail.com", false);
            Flight flight = new Flight(1, "KPL1234", "Qatar", "India", LocalDate.now().plusDays(10), 100, false, 10000.0);
            Booking booking = new Booking(customer, flight, LocalDate.now(), false, FlightClass.ECONOMY_CLASS);

            customer.addBooking(booking);
            booking.cancelBooking();

            assertTrue(booking.isCanceled());
            assertTrue(flight.getPassengers().isEmpty());
        } catch (FlightBookingSystemException e) {
            fail("Exception should not be thrown here.");
        }
    }
    
    @Test
    void testUpdateBooking() {
        try {
            Customer customer = new Customer(1, "Pradip", "123456789", "pradip@gmail.com", false);

            Flight flight = new Flight(1, "KPL1234", "Qatar", "India", LocalDate.now().plusDays(10), 100, false, 10000.0);
            Booking booking = new Booking(customer, flight, LocalDate.now(), false, FlightClass.ECONOMY_CLASS);

            customer.addBooking(booking);
            booking.updateBooking();

            assertTrue(booking.getCancellationFee() > 0);
        } catch (FlightBookingSystemException e) {
            fail("Exception should not be thrown here.");
        }
    }
    
    @Test
    void testCalculateCancellationFee() {
        Customer customer = new Customer(1, "Solomon", "1533523525", "solomon@gmail.com", false);
		Flight flight = new Flight(1, "KPL1234", "India", "Nepal", LocalDate.now().plusDays(10), 100, false, 10000.0);
		Booking booking = new Booking(customer, flight, LocalDate.now(), false, FlightClass.ECONOMY_CLASS);

		double cancellationFee = booking.calculateCancellationFee();
		assertEquals(0.1 * flight.getPrice(), cancellationFee);
    }
    
    @Test
    void testCalculateRebookFee() {
        Customer customer = new Customer(1, "Bibek", "123456789", "bibek@gmail.com", false);
		Flight flight = new Flight(1, "KPL1234", "Nepal", "India", LocalDate.now().plusDays(10), 100, false, 10000.0);
		Booking booking = new Booking(customer, flight, LocalDate.now(), false, FlightClass.ECONOMY_CLASS);

		double rebookFee = booking.calculateRebookFee();
		assertEquals(0.05 * flight.getPrice(), rebookFee);
    }
}
