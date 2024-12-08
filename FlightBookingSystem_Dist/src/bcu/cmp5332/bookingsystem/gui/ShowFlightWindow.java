package bcu.cmp5332.bookingsystem.gui;

import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.Flight.FlightClass;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

/**
 * The {@code ShowFlightWindow} class is a GUI component that displays the details
 * of a specific flight in a new window. It extends {@code JFrame} and uses a
 * {@code FlightBookingSystem} instance to retrieve and display flight information.
 *
 * @author Bibek
 * @author Solomon
 * @version 1.0
 * @since 2024-06-15
 */

public class ShowFlightWindow extends JFrame {
    
    private final int flightId;
    private final FlightBookingSystem flightBookingSystem;
    
    /**
     * Constructs a new {@code ShowFlightWindow} with the specified flight ID and
     * {@code FlightBookingSystem} instance.
     *
     * @param flightId             The ID of the flight whose details are to be displayed.
     * @param flightBookingSystem  The {@code FlightBookingSystem} instance used to retrieve flight details.
     */
    public ShowFlightWindow(int flightId, FlightBookingSystem flightBookingSystem) {
        this.flightId = flightId;
        this.flightBookingSystem = flightBookingSystem;
        initialize();
    }

    /**
     * Initializes the components of the {@code ShowFlightWindow}.
     */
    private void initialize() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            // Handle exception
        }

        setTitle("Flight Details");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));

        JPanel flightDetailsPanel = new JPanel();
        flightDetailsPanel.setLayout(new GridBagLayout());
        flightDetailsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Flight Information"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        Flight flight = null;
        try {
            flight = flightBookingSystem.getFlightByID(flightId);
            if (flight == null || flight.isDeleted()) {
                throw new FlightBookingSystemException("Flight with ID " + flightId + " not found.");
            }
        } catch (FlightBookingSystemException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        addLabelAndValue(flightDetailsPanel, gbc, "Flight ID:", String.valueOf(flight.getId()), 0);
        addLabelAndValue(flightDetailsPanel, gbc, "Flight Number:", flight.getFlightNumber(), 1);
        addLabelAndValue(flightDetailsPanel, gbc, "Origin:", flight.getOrigin(), 2);
        addLabelAndValue(flightDetailsPanel, gbc, "Destination:", flight.getDestination(), 3);
        addLabelAndValue(flightDetailsPanel, gbc, "Departure Date:", flight.getDepartureDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), 4);

        add(flightDetailsPanel, BorderLayout.NORTH);

        if (flight.getPassengers().isEmpty()) {
            JLabel noPassengersLabel = new JLabel("No passengers for this flight.");
            noPassengersLabel.setHorizontalAlignment(JLabel.CENTER);
            noPassengersLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            add(noPassengersLabel, BorderLayout.CENTER);
        } else {
            String[] columnNames = {"No.", "Name", "Phone", "Email", "Class"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
            JTable passengersTable = new JTable(model);
            
            List<Customer> passengers = flight.getPassengers();
            for (int i = 0; i < passengers.size(); i++) {
                Customer passenger = passengers.get(i);
                Booking booking = flightBookingSystem.getBookingByCustomerAndFlight(passenger, flight);
                FlightClass flightClass = booking.getFlightClass();
                Object[] rowData = {
                    (i + 1),
                    passenger.getName(),
                    passenger.getPhone(),
                    passenger.getEmail(),
                    flightClass
                };
                model.addRow(rowData);
            }

            passengersTable.setRowHeight(25);
            passengersTable.setFont(new Font("Arial", Font.PLAIN, 14));
            passengersTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

            JScrollPane scrollPane = new JScrollPane(passengersTable);
            scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Passenger List"));
            add(scrollPane, BorderLayout.CENTER);
        }

        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Arial", Font.BOLD, 14));
        closeButton.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        add(buttonPanel, BorderLayout.SOUTH);
        
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Adds a detail label and value to the specified panel with the given {@code GridBagConstraints}.
     *
     * @param panel     The panel to which the detail should be added.
     * @param gbc       The {@code GridBagConstraints} to use for positioning the detail.
     * @param labelText The text of the label.
     * @param valueText The text of the value.
     * @param yPos      The y position of the detail in the grid.
     */
    private void addLabelAndValue(JPanel panel, GridBagConstraints gbc, String labelText, String valueText, int yPos) {
        gbc.gridx = 0;
        gbc.gridy = yPos;
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(label, gbc);

        gbc.gridx = 1;
        JLabel value = new JLabel(valueText);
        value.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(value, gbc);
    }
}
