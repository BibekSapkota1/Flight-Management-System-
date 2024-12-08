package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.util.List;
//... existing imports ...
import javax.swing.border.TitledBorder;

/**
 * The {@code ListBookingPanel} class represents a panel for displaying a list of bookings
 * in the Flight Booking System. It extends {@code JPanel} and provides functionality to 
 * view all bookings in a tabular format with additional UI elements such as a back button.
 *
 * <p>The panel includes a table to display booking details, such as Booking ID, Customer,
 * Flight, Booking Date, and Flight Class. It applies custom styling to the table and 
 * header, ensuring a consistent and visually appealing UI. The class also includes a back
 * button to navigate back to the previous container.</p>
 *
 * <p>This class interacts with the main application {@code FlightBookingSystem} to fetch and
 * display booking data. It filters out deleted bookings to ensure only active bookings are shown.</p>
 *
 * @author Bibek
 * @author Solomon
 * @version 1.0
 * @since 2024-06-15
 * @see FlightBookingSystem
 * @see Booking
 */
public class ListBookingPanel extends JPanel {

 private FlightBookingSystem fbs;
 private JTable table;

 /**
  * Constructs a {@code ListBookingPanel} with the specified {@code FlightBookingSystem}.
  *
  * @param fbs The {@code FlightBookingSystem} instance providing access to booking data.
  */
 public ListBookingPanel(FlightBookingSystem fbs) {
     this.fbs = fbs;
     initialize();
 }

 /**
  * Initializes the graphical components of the panel including the table for displaying
  * bookings, table styling, and layout management. It fetches bookings from the 
  * {@code FlightBookingSystem} and filters out deleted bookings before displaying them.
  */
 private void initialize() {
     setLayout(new BorderLayout(10, 10));
     setBackground(new Color(240, 248, 255)); // Light blue background
     setBorder(new EmptyBorder(10, 10, 10, 10));

     List<Booking> bookingsList = fbs.getBookings();
     String[] columns = {"Booking ID", "Customer", "Flight", "Booking Date", "Flight Class"};

     DefaultTableModel model = new DefaultTableModel(columns, 0);
     for (Booking booking : bookingsList) {
         if (!booking.isDeleted()) {
             String customerDetails = "Customer " + booking.getCustomer().getId() + " - " + booking.getCustomer().getName();
             String flightDetails = "Flight " + booking.getFlight().getId() + " - " + booking.getFlight().getFlightNumber();

             model.addRow(new Object[]{
                     booking.getId(),
                     customerDetails,
                     flightDetails,
                     booking.getBookingDate(),
                     booking.getFlightClass()
             });
         }
     }

     table = new JTable(model);
     table.setFillsViewportHeight(true);
     table.setRowHeight(30);
     table.setShowGrid(true);
     table.setGridColor(Color.LIGHT_GRAY);

     DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
     headerRenderer.setHorizontalAlignment(JLabel.CENTER);
     table.getTableHeader().setDefaultRenderer(headerRenderer);

     DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
     cellRenderer.setHorizontalAlignment(JLabel.CENTER);
     for (int i = 0; i < table.getColumnCount(); i++) {
         table.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
     }

     JTableHeader header = table.getTableHeader();
     header.setBackground(new Color(100, 149, 237));
     header.setForeground(Color.WHITE);
     header.setFont(new Font("Arial", Font.BOLD, 14));

     table.setBackground(Color.WHITE);
     table.setForeground(Color.DARK_GRAY);
     table.setFont(new Font("Arial", Font.PLAIN, 12));

     JScrollPane scrollPane = new JScrollPane(table);
     add(scrollPane, BorderLayout.CENTER);

     JLabel title = new JLabel("Booking List", JLabel.CENTER);
     title.setFont(new Font("Arial", Font.BOLD, 24));
     title.setBorder(new EmptyBorder(10, 0, 10, 0));
     add(title, BorderLayout.NORTH);

     JButton backButton = new JButton("Back");
     backButton.setFont(new Font("Arial", Font.BOLD, 14));
     backButton.addActionListener(e -> {
         Container container = this.getParent();
         if (container != null) {
             container.remove(this);
             container.revalidate();
             container.repaint();
         }
     });

     JPanel buttonPanel = new JPanel();
     buttonPanel.add(backButton);
     buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
     add(buttonPanel, BorderLayout.SOUTH);
 }
}
