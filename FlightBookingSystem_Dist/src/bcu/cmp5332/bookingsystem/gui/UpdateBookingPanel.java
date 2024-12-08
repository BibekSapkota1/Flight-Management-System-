package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.Flight.FlightClass;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * The {@code UpdateBookingPanel} class represents a graphical user interface panel for updating a booking
 * in the Flight Booking System. It extends {@code JPanel} and provides functionality to update the flight class
 * for a specific booking.
 *
 * <p>The panel includes fields for entering the customer ID and flight ID, displaying the current booking date,
 * and selecting a new flight class. Users can click the "Update Booking" button to save the changes.
 * </p>
 *
 * <p>This class interacts with the main application {@code FlightBookingSystem} to access and modify booking data.
 * It handles exceptions related to updating bookings and displays appropriate error messages.</p>
 *
 * <p>It is designed with a user-friendly interface that adjusts well on different screen sizes.</p>
 *
 * @author Bibek
 * @author Solomon
 * @version 1.0
 * @since 2024-06-15
 * @see FlightBookingSystem
 * @see Booking
 * @see Customer
 * @see Flight
 * @see FlightClass
 */
public class UpdateBookingPanel extends JPanel implements ActionListener {

    private final FlightBookingSystem fbs;

    private JLabel customerIdLabel;
    private JTextField customerIdField;
    private JLabel flightIdLabel;
    private JTextField flightIdField;
    private JLabel newBookingDateLabel;
    private JLabel newBookingDateValue;
    private JLabel newFlightClassLabel;
    private JComboBox<FlightClass> newFlightClassComboBox;
    private JButton updateButton;

    /**
     * Constructs an {@code UpdateBookingPanel} with the specified {@code FlightBookingSystem}.
     *
     * @param fbs The {@code FlightBookingSystem} instance providing access to booking data.
     */
    public UpdateBookingPanel(FlightBookingSystem fbs) {
        this.fbs = fbs;
        initialize();
    }

    /**
     * Initializes the graphical components of the panel including labels, text fields, combo box, and button.
     * Sets up the layout and functionality to update a booking upon user interaction.
     */
    private void initialize() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(240, 248, 255)); // Light blue background
        setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(240, 248, 255)); // Light blue background
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.anchor = GridBagConstraints.WEST;

        // Customer ID
        customerIdLabel = new JLabel("Customer ID:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        formPanel.add(customerIdLabel, constraints);

        customerIdField = new JTextField(15);
        constraints.gridx = 1;
        constraints.gridy = 0;
        formPanel.add(customerIdField, constraints);

        // Flight ID
        flightIdLabel = new JLabel("Flight ID:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        formPanel.add(flightIdLabel, constraints);

        flightIdField = new JTextField(15);
        constraints.gridx = 1;
        constraints.gridy = 1;
        formPanel.add(flightIdField, constraints);

        // New Booking Date
        newBookingDateLabel = new JLabel("Booking Date:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        formPanel.add(newBookingDateLabel, constraints);

        // Display current date as non-editable label
        newBookingDateValue = new JLabel(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        newBookingDateValue.setFont(newBookingDateValue.getFont().deriveFont(Font.PLAIN)); // Ensure the font style is not bold
        constraints.gridx = 1;
        constraints.gridy = 2;
        formPanel.add(newBookingDateValue, constraints);

        // New Flight Class
        newFlightClassLabel = new JLabel("New Flight Class:");
        constraints.gridx = 0;
        constraints.gridy = 3;
        formPanel.add(newFlightClassLabel, constraints);

        newFlightClassComboBox = new JComboBox<>(FlightClass.values());
        constraints.gridx = 1;
        constraints.gridy = 3;
        formPanel.add(newFlightClassComboBox, constraints);

        // Update Button
        updateButton = new JButton("Update Booking");
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        formPanel.add(updateButton, constraints);
        updateButton.addActionListener(this);

        add(formPanel, BorderLayout.CENTER);
    }

    /**
     * Handles the action performed event when the update button is clicked.
     *
     * @param e The action event triggered by the button click.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == updateButton) {
            updateBooking();
        }
    }

    /**
     * Updates the booking with the new flight class selected by the user.
     * Displays appropriate error messages in case of invalid input or exceptions.
     */
    private void updateBooking() {
        String customerIdStr = customerIdField.getText().trim();
        String flightIdStr = flightIdField.getText().trim();
        FlightClass newFlightClass = (FlightClass) newFlightClassComboBox.getSelectedItem();

        if (customerIdStr.isEmpty() || flightIdStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Customer ID and Flight ID.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int customerId = Integer.parseInt(customerIdStr);
            int flightId = Integer.parseInt(flightIdStr);

            Customer customer = fbs.getCustomerByID(customerId);
            Flight oldFlight = fbs.getFlightByID(flightId);

            if (customer == null) {
                throw new FlightBookingSystemException("Customer with ID " + customerId + " not found.");
            }

            if (oldFlight == null) {
                throw new FlightBookingSystemException("Flight with ID " + flightId + " not found.");
            }

            Booking booking = fbs.getBookingByCustomerAndFlight(customer, oldFlight);

            if (booking == null) {
                throw new FlightBookingSystemException("Booking not found for customer " + customer.getName() + " on flight " + oldFlight.getFlightNumber());
            }

            // Set new flight class if selected
            if (newFlightClass != null) {
                booking.setFlightClass(newFlightClass);
            }

            JOptionPane.showMessageDialog(this, "Booking updated successfully.");
            FlightBookingSystemData.store(fbs);

            // Switch to list bookings panel
            switchToPanel(new ListBookingPanel(fbs));

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid IDs.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (FlightBookingSystemException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error storing data to file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Switches to a different panel, typically the list bookings panel, to display the updated list of bookings.
     *
     * @param panel The panel to switch to.
     */
    private void switchToPanel(JPanel panel) {
        Component parent = this.getParent();
        while (!(parent instanceof MainWindow) && parent != null) {
            parent = parent.getParent();
        }
        if (parent instanceof MainWindow) {
            MainWindow mainWindow = (MainWindow) parent;
            mainWindow.displayBookings();
        } else {
            // Handle case where MainWindow is not found
            JOptionPane.showMessageDialog(this, "MainWindow not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
