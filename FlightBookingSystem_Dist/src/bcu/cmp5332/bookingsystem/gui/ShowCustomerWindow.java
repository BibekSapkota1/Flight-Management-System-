package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import javax.swing.*;
import java.awt.*;

/**
 * The {@code ShowCustomerWindow} class is a GUI component that displays the details
 * of a specific customer in a new window. It extends {@code JFrame} and uses a
 * {@code FlightBookingSystem} instance to retrieve and display customer information.
 *
 * @author Bibek
 * @author Solomon
 * @version 1.0
 * @since 2024-06-15
 */
public class ShowCustomerWindow extends JFrame {

    private final int customerId;
    private final FlightBookingSystem flightBookingSystem;

    /**
     * Constructs a new {@code ShowCustomerWindow} with the specified customer ID and
     * {@code FlightBookingSystem} instance.
     *
     * @param customerId          The ID of the customer whose details are to be displayed.
     * @param flightBookingSystem The {@code FlightBookingSystem} instance used to retrieve customer details.
     */
    public ShowCustomerWindow(int customerId, FlightBookingSystem flightBookingSystem) {
        this.customerId = customerId;
        this.flightBookingSystem = flightBookingSystem;
        initialize();
    }

    /**
     * Initializes the components of the {@code ShowCustomerWindow}.
     */
    private void initialize() {
        setTitle("Customer Details");
        setSize(800, 600); // Initial size
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Customer Details");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel detailsPanel = new JPanel(new GridBagLayout());
        detailsPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        try {
            Customer customer = flightBookingSystem.getCustomerByID(customerId);
            if (customer == null) {
                throw new FlightBookingSystemException("Customer with ID " + customerId + " not found.");
            }

            addDetail(detailsPanel, gbc, "Name:", customer.getName());
            addDetail(detailsPanel, gbc, "Phone:", customer.getPhone());
            addDetail(detailsPanel, gbc, "Email:", customer.getEmail());
            JLabel bookingsLabel = new JLabel("Bookings:");
            bookingsLabel.setFont(new Font("Arial", Font.BOLD, 16));
            bookingsLabel.setForeground(Color.BLUE);
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            detailsPanel.add(bookingsLabel, gbc);

            gbc.gridx = 0;
            gbc.gridy = GridBagConstraints.RELATIVE;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.WEST;

            JTextArea bookingsArea = new JTextArea(customer.getDetails());
            bookingsArea.setFont(new Font("Arial", Font.PLAIN, 14));
            bookingsArea.setEditable(false);
            bookingsArea.setLineWrap(true);
            bookingsArea.setWrapStyleWord(true);


            bookingsArea.setBorder(BorderFactory.createLineBorder(Color.GRAY)); 

            JScrollPane scrollPane = new JScrollPane(bookingsArea);
            scrollPane.setPreferredSize(new Dimension(750, 200)); 
            detailsPanel.add(scrollPane, gbc);

        } catch (FlightBookingSystemException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        mainPanel.add(detailsPanel, BorderLayout.CENTER);
        add(mainPanel);
        pack(); 
        setVisible(true);
    }

    /**
     * Adds a detail label and value to the specified panel with the given {@code GridBagConstraints}.
     *
     * @param panel The panel to which the detail should be added.
     * @param gbc   The {@code GridBagConstraints} to use for positioning the detail.
     * @param label The text of the label.
     * @param value The text of the value.
     */
    private void addDetail(JPanel panel, GridBagConstraints gbc, String label, String value) {
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel(value), gbc);
    }

}
