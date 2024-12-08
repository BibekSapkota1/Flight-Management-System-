package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;

/**
 * The {@code IssueBookingWindow} class represents a graphical user interface window for issuing a booking
 * in the Flight Booking System. It extends {@code JFrame} and provides functionality to enter customer ID,
 * flight ID, flight class, and issue a booking upon validation.
 *
 * <p>The window includes text fields for entering customer ID and flight ID, a combo box for selecting
 * the flight class, and buttons to issue the booking or cancel the operation. Upon issuing a booking,
 * the current date is automatically set as the booking date. It handles various validation checks such
 * as customer and flight existence, flight availability, and capacity constraints before creating a new booking.</p>
 *
 * <p>This class interacts with the main application {@code FlightBookingSystem} to access and modify booking data.
 * It updates the main window {@code MainWindow} to display the updated list of bookings after issuing a new booking.
 * Error messages are displayed using {@code JOptionPane} in case of input errors or system exceptions.</p>
 *
 * @author Bibek
 * @author Solomon
 * @version 1.0
 * @since 2024-06-15
 * @see FlightBookingSystem
 * @see Booking
 * @see Customer
 * @see Flight
 */
public class IssueBookingWindow extends JFrame implements ActionListener {

    private MainWindow parentWindow;
    private FlightBookingSystem fbs;

    private JLabel customerIdLabel;
    private JTextField customerIdField;
    private JLabel flightIdLabel;
    private JTextField flightIdField;
    private JLabel flightClassLabel;
    private JComboBox<Flight.FlightClass> flightClassComboBox; // Ensure correct import of Flight.FlightClass
    private JButton issueButton;
    private JButton cancelButton;

    /**
     * Constructs an {@code IssueBookingWindow} with the specified {@code MainWindow} and {@code FlightBookingSystem}.
     *
     * @param parentWindow The {@code MainWindow} instance where the booking list is displayed.
     * @param fbs The {@code FlightBookingSystem} instance providing access to booking and flight data.
     */
    public IssueBookingWindow(MainWindow parentWindow, FlightBookingSystem fbs) {
        this.parentWindow = parentWindow;
        this.fbs = fbs;

        setTitle("Issue Booking");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(parentWindow);

        initComponents();
        setVisible(true);
    }

    /**
     * Initializes the graphical components of the window including labels, text fields, combo box, buttons,
     * and event listeners. Sets up the layout and functionality to issue a booking upon user interaction.
     */
    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

        customerIdLabel = new JLabel("Customer ID:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(customerIdLabel, constraints);

        customerIdField = new JTextField(15);
        constraints.gridx = 1;
        panel.add(customerIdField, constraints);

        flightIdLabel = new JLabel("Flight ID:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(flightIdLabel, constraints);

        flightIdField = new JTextField(15);
        constraints.gridx = 1;
        panel.add(flightIdField, constraints);

        flightClassLabel = new JLabel("Class:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(flightClassLabel, constraints);

        flightClassComboBox = new JComboBox<>(Flight.FlightClass.values()); // Ensure correct import of Flight.FlightClass
        constraints.gridx = 1;
        panel.add(flightClassComboBox, constraints);

        issueButton = new JButton("Issue");
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(issueButton, constraints);
        issueButton.addActionListener(this);

        cancelButton = new JButton("Cancel");
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(cancelButton, constraints);
        cancelButton.addActionListener(this);

        getContentPane().add(panel);
    }

    /**
     * Handles action events from the "Issue" and "Cancel" buttons.
     * Issues a booking after validating customer ID, flight ID, and flight class.
     *
     * @param e The {@code ActionEvent} that occurred.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == issueButton) {
            issueBooking();
        } else if (e.getSource() == cancelButton) {
            dispose();
        }
    }

    /**
     * Issues a booking by validating input data (customer ID, flight ID, and flight class),
     * checking availability and capacity constraints of the flight, and creating a new booking
     * if all conditions are met. Updates the main window {@code MainWindow} to display the updated
     * list of bookings after issuing a new booking.
     *
     * <p>Displays error messages using {@code JOptionPane} for input errors or system exceptions.</p>
     */
    private void issueBooking() {
        try {
            int customerId = Integer.parseInt(customerIdField.getText().trim());
            int flightId = Integer.parseInt(flightIdField.getText().trim());
            Flight.FlightClass flightClass = (Flight.FlightClass) flightClassComboBox.getSelectedItem(); // Correct type casting
            LocalDate bookingDate = LocalDate.now(); // Automatically use current date

            Customer customer = fbs.getCustomerByID(customerId);
            Flight flight = fbs.getFlightByID(flightId);

            if (customer == null) {
                throw new FlightBookingSystemException("Customer with ID " + customerId + " not found.");
            }

            if (flight == null) {
                throw new FlightBookingSystemException("Flight with ID " + flightId + " not found.");
            }

            if (flight.isDeleted()) {
                throw new FlightBookingSystemException("Flight " + flightId + " is deleted. Booking not allowed.");
            }

            if (flight.isFullyBooked() || flight.getPassengers().size() >= flight.getCapacity()) {
                throw new FlightBookingSystemException("Flight " + flightId + " is fully booked or at full capacity. Booking not allowed.");
            }

            Booking booking = new Booking(customer, flight, bookingDate, false, flightClass);
            fbs.addBooking(booking);
            FlightBookingSystemData.store(fbs);

            parentWindow.displayBookings();  // Update booking list in main window
            JOptionPane.showMessageDialog(this, "Booking issued successfully.");
            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid IDs and date.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (FlightBookingSystemException | IOException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
