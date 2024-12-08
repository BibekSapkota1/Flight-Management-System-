package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code DeleteCustomerWindow} class represents a graphical user interface window for deleting a customer
 * from the Flight Booking System. It extends {@code JFrame} and provides functionality to select a customer
 * from a combo box and delete them upon confirmation.
 *
 * <p>The window includes a combo box populated with active customers' names. Users can select a customer and click
 * the "Delete Customer" button to initiate the deletion process. A confirmation dialog prompts the user to confirm
 * the deletion. After deletion, the customer list is updated, and the combo box is refreshed.</p>
 *
 * <p>This class interacts with the main application {@code FlightBookingSystem} to access and modify customer data.
 * It handles exceptions related to customer deletion and displays appropriate error messages.</p>
 *
 * 
 * @see FlightBookingSystem
 * @see Customer
 */

public class DeleteCustomerWindow extends JFrame {

    private final FlightBookingSystem flightBookingSystem;
    private JComboBox<String> customerComboBox;

    /**
     * Constructs a {@code DeleteCustomerWindow} with the specified {@code FlightBookingSystem}.
     *
     * @param flightBookingSystem The {@code FlightBookingSystem} instance providing access to customer data.
     */
    public DeleteCustomerWindow(FlightBookingSystem flightBookingSystem) {
        this.flightBookingSystem = flightBookingSystem;
        initialize();
    }

    /**
     * Initializes the graphical components of the window including labels, combo box, buttons, and event listeners.
     * Sets up the layout and functionality to delete a customer upon user interaction.
     */
    private void initialize() {
        setTitle("Delete Customer");
        setSize(600, 400); // Adjusted size for larger windows
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Delete Customer");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(new EmptyBorder(0, 0, 20, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel selectLabel = new JLabel("Select Customer:");
        selectLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(selectLabel, gbc);

        List<Customer> modifiableCustomers = new ArrayList<>(flightBookingSystem.getCustomers());
        modifiableCustomers.removeIf(Customer::isDeleted);
        String[] customerNames = modifiableCustomers.stream()
                .map(Customer::getName)
                .toArray(String[]::new);
        customerComboBox = new JComboBox<>(customerNames);
        customerComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        customerComboBox.setPreferredSize(new Dimension(300, 30));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        centerPanel.add(customerComboBox, gbc);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton deleteButton = new JButton("Delete Customer");
        deleteButton.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        deleteButton.setIcon(new ImageIcon("icons/delete.png")); // Make sure the icon exists
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String customerName = (String) customerComboBox.getSelectedItem();
                if (customerName != null && !customerName.isEmpty()) {
                    int confirmation = JOptionPane.showConfirmDialog(DeleteCustomerWindow.this,
                            "Are you sure you want to delete customer " + customerName + "?",
                            "Confirm Deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (confirmation == JOptionPane.YES_OPTION) {
                        try {
                            Customer customerToDelete = modifiableCustomers.stream()
                                    .filter(c -> c.getName().equals(customerName))
                                    .findFirst()
                                    .orElse(null);
                            if (customerToDelete != null) {
                                flightBookingSystem.removeCustomerById(customerToDelete.getId());
                                JOptionPane.showMessageDialog(DeleteCustomerWindow.this,
                                        "Customer " + customerName + " has been deleted.",
                                        "Success", JOptionPane.INFORMATION_MESSAGE);
                                refreshCustomerComboBox(); 
                            } else {
                                JOptionPane.showMessageDialog(DeleteCustomerWindow.this,
                                        "Customer not found.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (FlightBookingSystemException ex) {
                            JOptionPane.showMessageDialog(DeleteCustomerWindow.this,
                                    ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(DeleteCustomerWindow.this,
                            "Please select a customer to delete.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        buttonPanel.add(deleteButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        cancelButton.setIcon(new ImageIcon("icons/cancel.png")); // Make sure the icon exists
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); 
            }
        });

        buttonPanel.add(cancelButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    /**
     * Refreshes the customer combo box with the updated list of active customers after a deletion operation.
     */
    private void refreshCustomerComboBox() {
        List<Customer> modifiableCustomers = new ArrayList<>(flightBookingSystem.getCustomers());
        modifiableCustomers.removeIf(Customer::isDeleted);
        String[] customerNames = modifiableCustomers.stream()
                .map(Customer::getName)
                .toArray(String[]::new);
        customerComboBox.setModel(new DefaultComboBoxModel<>(customerNames));
    }
}
