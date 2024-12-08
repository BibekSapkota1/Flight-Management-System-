package bcu.cmp5332.bookingsystem.gui;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

/**
 * The {@code DeleteFlightWindow} class represents a graphical user interface window for deleting a flight
 * from the Flight Booking System. It extends {@code JFrame} and provides functionality to select a flight
 * from a combo box and delete it upon confirmation.
 *
 * <p>The window includes a combo box populated with available flights, where users can select a flight and click
 * the "Delete" button to initiate the deletion process. A confirmation dialog prompts the user to confirm
 * the deletion. After deletion, the flight list in the main window is updated.</p>
 *
 * <p>This class interacts with the main application {@code FlightBookingSystem} to access and modify flight data.
 * It handles exceptions related to flight deletion and displays appropriate error messages.</p>
 *
 * @author Bibek
 * @author Solomon
 * @version 1.0
 * @since 2024-06-15
 * @see FlightBookingSystem
 * @see Flight
 */
public class DeleteFlightWindow extends JFrame implements ActionListener {

    private MainWindow parentWindow;
    private FlightBookingSystem fbs;

    private JLabel flightLabel;
    private JComboBox<Flight> flightComboBox;
    private JButton deleteButton;
    private JButton cancelButton;

    /**
     * Constructs a {@code DeleteFlightWindow} with the specified {@code MainWindow} and {@code FlightBookingSystem}.
     *
     * @param parentWindow The {@code MainWindow} instance where the flight list is displayed.
     * @param fbs The {@code FlightBookingSystem} instance providing access to flight data.
     */
    public DeleteFlightWindow(MainWindow parentWindow, FlightBookingSystem fbs) {
        this.parentWindow = parentWindow;
        this.fbs = fbs;

        setTitle("Delete Flight");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(350, 180);
        setLocationRelativeTo(parentWindow);

        initComponents();
        setVisible(true);
    }

    /**
     * Initializes the graphical components of the window including labels, combo box, buttons, and event listeners.
     * Sets up the layout and functionality to delete a flight upon user interaction.
     */
    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.anchor = GridBagConstraints.WEST;

        flightLabel = new JLabel("Flight ID:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(flightLabel, constraints);

        flightComboBox = new JComboBox<>(fbs.getFlights().toArray(new Flight[0]));
        flightComboBox.setRenderer(new FlightComboBoxRenderer()); // Customize rendering
        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(flightComboBox, constraints);

        deleteButton = new JButton("Delete");
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1; // Reset grid width
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(deleteButton, constraints);
        deleteButton.addActionListener(this);

        cancelButton = new JButton("Cancel");
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 1; // Reset grid width
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(cancelButton, constraints);
        cancelButton.addActionListener(this);

        getContentPane().add(panel);
    }

    /**
     * Handles action events from the "Delete" and "Cancel" buttons.
     * Deletes the selected flight from the flight booking system upon confirmation.
     *
     * @param e The {@code ActionEvent} that occurred.
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == deleteButton) {
            Flight selectedFlight = (Flight) flightComboBox.getSelectedItem();
            if (selectedFlight == null) {
                JOptionPane.showMessageDialog(this, "Please select a flight to delete.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                fbs.removeFlightById(selectedFlight.getId());
                parentWindow.displayFlights(); // Update flight list in main window
                JOptionPane.showMessageDialog(this, "Flight deleted successfully.");
                dispose();
            } catch (FlightBookingSystemException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == cancelButton) {
            dispose();
        }
    }

    /**
     * Custom list cell renderer for rendering flight objects in the combo box.
     * Displays the flight ID alongside each flight in the drop-down list.
     */
    class FlightComboBoxRenderer extends DefaultListCellRenderer {
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof Flight) {
                Flight flight = (Flight) value;
                setText("Flight ID: " + flight.getId()); // Display Flight ID
            }
            return this;
        }
    }
}
