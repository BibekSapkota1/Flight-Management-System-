package bcu.cmp5332.bookingsystem.gui;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

//... existing imports ...
import javax.swing.border.TitledBorder;

/**
 * The {@code ListCustomerPanel} class represents a panel for displaying a list of customers
 * in the Flight Booking System. It extends {@code JPanel} and provides functionality to 
 * view all customers in a tabular format with additional UI elements such as a back button.
 *
 * <p>The panel includes a table to display customer details, such as Customer ID, Name,
 * Phone, and Email. It applies custom styling to the table and header, ensuring a consistent 
 * and visually appealing UI. The class also includes a back button to navigate back to the 
 * previous container.</p>
 *
 * <p>This class interacts with the main application {@code FlightBookingSystem} to fetch and
 * display customer data. It filters out deleted customers to ensure only active customers 
 * are shown.</p>
 *
 * @author Bibek
 * @author Solomon
 * @version 1.0
 * @since 2024-06-15
 * @see FlightBookingSystem
 * @see Customer
 */
public class ListCustomerPanel extends JPanel {

 private final FlightBookingSystem flightBookingSystem;

 /**
  * Constructs a {@code ListCustomerPanel} with the specified {@code FlightBookingSystem}.
  *
  * @param flightBookingSystem The {@code FlightBookingSystem} instance providing access to customer data.
  */
 public ListCustomerPanel(FlightBookingSystem flightBookingSystem) {
     this.flightBookingSystem = flightBookingSystem;
     initialize();
 }

 /**
  * Initializes the graphical components of the panel including the table for displaying
  * customers, table styling, and layout management. It fetches customers from the 
  * {@code FlightBookingSystem} and filters out deleted customers before displaying them.
  */
 private void initialize() {
     setLayout(new BorderLayout(15, 15));
     setBorder(new EmptyBorder(10, 10, 10, 10));

     JPanel customersPanel = new JPanel();
     customersPanel.setLayout(new BorderLayout());

     String[] columnNames = {"Customer ID", "Name", "Phone", "Email"};
     DefaultTableModel model = new DefaultTableModel(columnNames, 0);
     JTable customersTable = new JTable(model);

     List<Customer> customers = flightBookingSystem.getCustomers();
     for (Customer customer : customers) {
         if (!customer.isDeleted()) {
             Object[] rowData = {
                 customer.getId(),
                 customer.getName(),
                 customer.getPhone(),
                 customer.getEmail()
             };
             model.addRow(rowData);
         }
     }

     customersTable.setRowHeight(25);
     customersTable.setFont(new Font("Arial", Font.PLAIN, 14));
     customersTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

     JScrollPane scrollPane = new JScrollPane(customersTable);
     customersPanel.add(scrollPane, BorderLayout.CENTER);

     add(customersPanel, BorderLayout.CENTER);

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
