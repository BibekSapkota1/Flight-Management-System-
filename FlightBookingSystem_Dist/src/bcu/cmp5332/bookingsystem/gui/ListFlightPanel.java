package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

/**
 * The {@code ListFlightPanel} class represents a panel for displaying a list of flights
 * in the Flight Booking System. It extends {@code JPanel} and provides functionality to
 * view all flights in a tabular format with custom styling and UI elements.
 *
 * <p>The panel includes a table to display flight details, such as Flight ID, Flight Number,
 * Origin, Destination, Departure Date, and prices for different classes. The table is 
 * styled with alternating row colors, customized headers, and a light blue background.</p>
 *
 * <p>This class interacts with the main application {@code FlightBookingSystem} to fetch and
 * display flight data. It filters out deleted flights to ensure only active flights are shown.</p>
 *
 * @author Bibek
 * @author Solomon
 * @version 1.0
 * @since 2024-06-15
 * @see FlightBookingSystem
 * @see Flight
 */
public class ListFlightPanel extends JPanel {

    private FlightBookingSystem fbs;

    /**
     * Constructs a {@code ListFlightPanel} with the specified {@code FlightBookingSystem}.
     *
     * @param fbs The {@code FlightBookingSystem} instance providing access to flight data.
     */
    public ListFlightPanel(FlightBookingSystem fbs) {
        this.fbs = fbs;
        initialize();
    }

    /**
     * Initializes the graphical components of the panel including the table for displaying
     * flights, table styling, and layout management. It fetches flights from the 
     * {@code FlightBookingSystem} and filters out deleted flights before displaying them.
     */
    private void initialize() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(240, 248, 255)); 
        setBorder(new EmptyBorder(10, 10, 10, 10));

        List<Flight> flightsList = fbs.getFlights();
        String[] columns = {"Flight ID", "Flight No", "Origin", "Destination", "Departure Date", "Economy Price", "Business Price", "First Class Price"};

        DefaultTableModel model = new DefaultTableModel(columns, 0);
        for (Flight flight : flightsList) {
            if (!flight.isDeleted()) {
                Object[] row = {
                    flight.getId(),
                    flight.getFlightNumber(),
                    flight.getOrigin(),
                    flight.getDestination(),
                    flight.getDepartureDate(),
                    flight.calculateDynamicPrice(Flight.FlightClass.ECONOMY_CLASS),
                    flight.calculateDynamicPrice(Flight.FlightClass.BUSINESS_CLASS),
                    flight.calculateDynamicPrice(Flight.FlightClass.FIRST_CLASS)
                };
                model.addRow(row);
            }
        }

        JTable table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setRowHeight(30);
        table.setShowGrid(true);
        table.setGridColor(Color.LIGHT_GRAY);
        table.setIntercellSpacing(new Dimension(5, 5)); 

        JTableHeader header = table.getTableHeader();
        header.setBackground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 14)); 
        header.setPreferredSize(new Dimension(header.getWidth(), 30)); 

     
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (row % 2 == 0) {
                    c.setBackground(Color.WHITE); 
                } else {
                    c.setBackground(new Color(240, 240, 240));
                }
                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY)); 

        add(scrollPane, BorderLayout.CENTER);

        JLabel title = new JLabel("Flights List", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBorder(new EmptyBorder(10, 0, 20, 0)); 
        add(title, BorderLayout.NORTH);
    }

    /**
     * The main method to demonstrate the {@code ListFlightPanel} in a standalone frame.
     * 
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        // Example usage:
        FlightBookingSystem fbs = new FlightBookingSystem(); 
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Flight List");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 600);

            ListFlightPanel panel = new ListFlightPanel(fbs);
            frame.add(panel);

            frame.setLocationRelativeTo(null); 
            frame.setVisible(true);
        });
    }
}
