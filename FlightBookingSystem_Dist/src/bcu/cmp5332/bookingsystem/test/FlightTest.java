package bcu.cmp5332.bookingsystem.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;

class FlightTest {

	@Test
    public void testConstructor() throws FlightBookingSystemException {
        Flight flight = new Flight(1, "KPL1234", "KTM", "QTR", LocalDate.now(), 300, false, 15000.0);

        assertEquals(1, flight.getId());
        assertEquals("KPL1234", flight.getFlightNumber());
        assertEquals("KTM", flight.getOrigin());
        assertEquals("QTR", flight.getDestination());
        assertEquals(LocalDate.now(), flight.getDepartureDate());
        assertEquals(300, flight.getCapacity());
        assertEquals(15000.0, flight.getPrice(), 0.001);
        assertTrue(flight.getPassengers().isEmpty());
    }

	@Test
	public void testSetterMethods() throws FlightBookingSystemException {
	    Flight flight = new Flight(1, "KPL1234", "KTM", "QTR", LocalDate.now(), 300, false,15000.0);

	    flight.setFlightNumber("KPL1234");
	    flight.setOrigin("KTM");
	    flight.setDestination("QTR");
	    LocalDate newDepartureDate = LocalDate.now().plusDays(1);
	    flight.setDepartureDate(newDepartureDate);
	    flight.setCapacity(300);
	    flight.setPrice(15000.0);

	    assertEquals("KPL1234", flight.getFlightNumber());
	    assertEquals("KTM", flight.getOrigin());
	    assertEquals("QTR", flight.getDestination());
	    assertEquals(newDepartureDate, flight.getDepartureDate());
	    assertEquals(300, flight.getCapacity());
	    assertEquals(15000.0, flight.getPrice(), 0.001);
	}


    @Test
    public void testAddPassenger() throws FlightBookingSystemException {
        Flight flight = new Flight(1, "KPL1234", "KTM", "QTR", LocalDate.now(), 300, false,15000.0);
        Customer passenger = new Customer(1, "Solomon", "123456789", "Solomon@gmail.com",false);

        flight.addPassenger(passenger);

        assertTrue(flight.getPassengers().contains(passenger));
    }
    
    @Test
    public void testRemovePassenger() throws FlightBookingSystemException {
        Flight flight = new Flight(1, "KPL1234", "KTM", "QTR", LocalDate.now(), 300, false, 15000.0);
        Customer passenger = new Customer(1, "Bibek", "123456789", "bibek@gmail.com", false);

        flight.addPassenger(passenger);

        flight.removePassenger(passenger);

        assertFalse(flight.getPassengers().contains(passenger));
    }

    
    @Test
    public void testAddAndRemovePassenger() throws FlightBookingSystemException {
        Flight flight = new Flight(1, "KPL1234", "KTM", "QTR", LocalDate.now(), 300, false, 15000.0);
        Customer passenger = new Customer(1, "Solomon", "123456789", "nayan@gmail.com", false);

        assertFalse(flight.getPassengers().contains(passenger));

        flight.addPassenger(passenger);
        assertTrue(flight.getPassengers().contains(passenger));

        flight.removePassenger(passenger);
        assertFalse(flight.getPassengers().contains(passenger));
    }

   
    @Test
    public void testIsFullyBooked() throws FlightBookingSystemException {
        Flight flight = new Flight(1, "KPL1234", "KTM", "QTR", LocalDate.now(), 2, false, 15000.0);

        Customer passenger1 = new Customer(1, "Solomon", "123456789", "solomon@gmail.com", false);
        Customer passenger2 = new Customer(2, "Bibek", "123456789", "bibek@gmail.com", false);

        assertFalse(flight.isFullyBooked());

        
        flight.addPassenger(passenger1);
        assertFalse(flight.isFullyBooked()); 

        
        flight.addPassenger(passenger2);
        assertTrue(flight.isFullyBooked()); 
    }

    
    @Test
    public void testHasDeparted() throws FlightBookingSystemException {
        Flight futureFlight = new Flight(1, "KPL1234", "KTM", "QTR", LocalDate.now().plusDays(1), 300, false, 15000.0);
        assertFalse(futureFlight.hasDeparted());

        Flight pastFlight = new Flight(2, "KPL1234", "KTM", "QTR", LocalDate.now().minusDays(1), 300, false, 15000.0);
        assertTrue(pastFlight.hasDeparted()); 
    }


    @Test
    public void testCalculateDynamicPrice() throws FlightBookingSystemException {
        Flight flight = new Flight(1, "KPL1234", "KTM", "QTR", LocalDate.now().plusDays(5), 10, false, 100.0);

        Customer passenger = new Customer(1, "Solomon", "123456789", "solomon@gmail.com", false);
        flight.addPassenger(passenger);

        double daysLeft = 5.0;
        double maxDaysForPriceFactor = 15.0;
        double daysPriceFactor = 1.0 + 0.1 * (maxDaysForPriceFactor - daysLeft);
        double classPriceFactor = 3.0;
        double bookedSeatsFactor = 1.0 + 0.1 * (double) flight.getBookedSeats() / (double) flight.getCapacity();
        double expectedDynamicPrice = flight.getPrice(Flight.FlightClass.FIRST_CLASS) * daysPriceFactor * classPriceFactor * bookedSeatsFactor;

        assertEquals(expectedDynamicPrice, flight.calculateDynamicPrice(Flight.FlightClass.FIRST_CLASS), 0.001);
    } 

}
