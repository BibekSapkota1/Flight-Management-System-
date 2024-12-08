package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * The {@code AddCustomerWindow} class represents a graphical user interface window for adding a new customer.
 * It extends {@code JFrame} and implements the {@code ActionListener} interface to handle button actions.
 *
 * <p>The window includes text fields for entering customer details such as name, phone, and email. Users can click
 * the "Add" button to add a new customer or the "Cancel" button to close the window.</p>
 *
 * <p>This class is part of the Flight Booking System GUI and interacts with the main window {@code MainWindow} and
 * the command to add a customer {@code AddCustomer}.</p>
 *
 * @author Bibek
 * @author Solomon
 * @version 1.0
 * @since 2024-06-15
 * @see Command
 */

public class AddCustomerWindow extends JFrame {

    private JTextField nameField;
    private JTextField phoneField;
    private JTextField emailField;
    private JButton addButton;
    private JButton cancelButton;
    private FlightBookingSystem flightBookingSystem;
    
    /**
     * Constructs an {@code AddCustomerWindow} object initialized with the given {@code flightBookingSystem}.
     *
     * @param flightBookingSystem The main {@code FlightBookingSystem} instance.
     */
    public AddCustomerWindow(FlightBookingSystem flightBookingSystem) {
        this.flightBookingSystem = flightBookingSystem;
        initialize();
    }

    /**
     * Initializes the GUI components of the window.
     */
    private void initialize() {
        setTitle("Add Customer");
        setSize(400, 250);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
        }

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        nameField = new JTextField(20);
        formPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Phone:"), gbc);

        gbc.gridx = 1;
        phoneField = new JTextField(20);
        formPanel.add(phoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        emailField = new JTextField(20);
        formPanel.add(emailField, gbc);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCustomer();
            }
        });
        buttonPanel.add(addButton);

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Handles the addition of a new customer when the Add button is clicked.
     * Validates input and adds the customer to the flight booking system.
     */
    private void addCustomer() {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();

        if (name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int maxCustomerId = 0;

            if (!flightBookingSystem.getCustomers().isEmpty()) {
                maxCustomerId = flightBookingSystem.getCustomers().stream()
                        .mapToInt(Customer::getId)
                        .max()
                        .orElse(0);
            }

            int customerId = maxCustomerId + 1;

            Customer customer = new Customer(customerId, name, phone, email, false);
            flightBookingSystem.addCustomer(customer);
            FlightBookingSystemData.store(flightBookingSystem);

            JOptionPane.showMessageDialog(this, "Customer added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error storing data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (FlightBookingSystemException ex) {
            JOptionPane.showMessageDialog(this, "Error adding customer: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
