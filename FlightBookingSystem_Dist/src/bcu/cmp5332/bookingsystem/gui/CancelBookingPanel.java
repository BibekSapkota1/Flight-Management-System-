package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.commands.CancelBooking;
import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


/**
 * The {@code CancelBookingPanel} class represents a modal dialog for canceling a booking in the Flight Booking System.
 * It extends {@code JDialog} and implements {@code ActionListener} to handle button actions.
 *
 * <p>The dialog includes text fields for entering customer ID and flight ID. Users can click the "Cancel Booking"
 * button to cancel a booking, which executes the {@code CancelBooking} command. After cancellation, the dialog
 * stores the updated data and switches to display the list of bookings.</p>
 *
 * <p>This class interacts with the main application {@code MainWindow} to display bookings and with the data
 * management class {@code FlightBookingSystemData} to store updated data.</p>
 *
 * 
 * @author Bibek
 * @author Solomon
 * @version 1.0
 * @since 2024-06-15
 * @see CancelBooking
 * @see FlightBookingSystemData
 */
public class CancelBookingPanel extends JDialog implements ActionListener {

    private FlightBookingSystem fbs;

    private JLabel customerIdLabel;
    private JTextField customerIdField;
    private JLabel flightIdLabel;
    private JTextField flightIdField;
    private JButton cancelButton;

    /**
     * Constructs a {@code CancelBookingPanel} dialog with the specified parent frame and {@code FlightBookingSystem}.
     *
     * @param parent The parent {@code JFrame} instance to center the dialog relative to.
     * @param fbs The {@code FlightBookingSystem} instance representing the application's data.
     */
    public CancelBookingPanel(JFrame parent, FlightBookingSystem fbs) {
        super(parent, "Cancel Booking", true);
        this.fbs = fbs;
        initialize();
        setSize(300, 200); 
        setLocationRelativeTo(parent); 
    }

    /**
     * Initializes the dialog components including labels, text fields, and buttons.
     * Sets up layout and action listeners.
     */
    private void initialize() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(240, 248, 255)); 

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(240, 248, 255)); 
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.anchor = GridBagConstraints.WEST;

        customerIdLabel = new JLabel("Customer ID:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        formPanel.add(customerIdLabel, constraints);

        customerIdField = new JTextField(15);
        constraints.gridx = 1;
        constraints.gridy = 0;
        formPanel.add(customerIdField, constraints);

        flightIdLabel = new JLabel("Flight ID:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        formPanel.add(flightIdLabel, constraints);

        flightIdField = new JTextField(15);
        constraints.gridx = 1;
        constraints.gridy = 1;
        formPanel.add(flightIdField, constraints);

        cancelButton = new JButton("Cancel Booking");
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        formPanel.add(cancelButton, constraints);
        cancelButton.addActionListener(this);

        add(formPanel, BorderLayout.CENTER);
    }

    /**
     * Handles the button click events for the "Cancel Booking" button.
     *
     * @param e The {@code ActionEvent} triggered by the button click.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cancelButton) {
            cancelBooking();
        }
    }

    /**
     * Validates user input, cancels the booking using the {@code CancelBooking} command,
     * and handles exceptions related to input validation and data storage.
     */
    private void cancelBooking() {
        String customerIdStr = customerIdField.getText().trim();
        String flightIdStr = flightIdField.getText().trim();

        if (customerIdStr.isEmpty() || flightIdStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Customer ID and Flight ID.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int customerId = Integer.parseInt(customerIdStr);
            int flightId = Integer.parseInt(flightIdStr);

            CancelBooking cancelBookingCommand = new CancelBooking(customerId, flightId);
            cancelBookingCommand.execute(fbs);

            JOptionPane.showMessageDialog(this, "Booking canceled successfully.");

            FlightBookingSystemData.store(fbs);

            switchToListBookingsPanel();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid IDs.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (FlightBookingSystemException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error storing data to file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Switches the application's main window to display the list of bookings after successful cancellation.
     */
    private void switchToListBookingsPanel() {
        if (getParent() instanceof MainWindow) {
            ((MainWindow) getParent()).displayBookings();
        }
        dispose(); 
    }

    /**
     * Displays the CancelBookingPanel dialog.
     */
    public void display() {
        setVisible(true); 
    }
}
