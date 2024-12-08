package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.commands.AddFlight;
import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Date;

/**
 * The {@code AddFlightWindow} class represents a graphical user interface window for adding a new flight.
 * It extends {@code JFrame} and implements the {@code ActionListener} interface to handle button actions.
 *
 * <p>The window includes text fields and spinners for entering flight details such as flight number, origin,
 * destination, departure date, capacity, and price. Users can click the "Add" button to add a new flight or
 * the "Cancel" button to close the window.</p>
 *
 * <p>This class interacts with the main window {@code MainWindow} to update the flight list display after
 * adding a flight. It also uses the {@code AddFlight} command to execute the addition of a flight.</p>
 *
 * 
 * @author Bibek
 * @author Solomon
 * @version 1.0
 * @since 2024-06-15
 * @see Command
 */
public class AddFlightWindow extends JFrame implements ActionListener {

    private MainWindow mw;
    private JTextField flightNoText = new JTextField();
    private JTextField originText = new JTextField();
    private JTextField destinationText = new JTextField();
    private JSpinner depDateSpinner;
    private JTextField capacityText = new JTextField();
    private JTextField priceText = new JTextField();

    private JButton addBtn = new JButton("Add");
    private JButton cancelBtn = new JButton("Cancel");

    /**
     * Constructs an {@code AddFlightWindow} object initialized with the given {@code mw} (MainWindow).
     *
     * @param mw The main {@code MainWindow} instance.
     */
    public AddFlightWindow(MainWindow mw) {
        this.mw = mw;
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
        }

        setTitle("Add a New Flight");
        setSize(400, 400);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(Color.WHITE);

        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Arial", Font.BOLD, 12);

        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel flightNoLabel = new JLabel("Flight No : ");
        flightNoLabel.setFont(labelFont);
        topPanel.add(flightNoLabel, gbc);

        gbc.gridx = 1;
        topPanel.add(flightNoText, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel originLabel = new JLabel("Origin : ");
        originLabel.setFont(labelFont);
        topPanel.add(originLabel, gbc);

        gbc.gridx = 1;
        topPanel.add(originText, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel destinationLabel = new JLabel("Destination : ");
        destinationLabel.setFont(labelFont);
        topPanel.add(destinationLabel, gbc);

        gbc.gridx = 1;
        topPanel.add(destinationText, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel depDateLabel = new JLabel("Departure Date : ");
        depDateLabel.setFont(labelFont);
        topPanel.add(depDateLabel, gbc);

        gbc.gridx = 1;
        depDateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(depDateSpinner, "yyyy-MM-dd");
        depDateSpinner.setEditor(dateEditor);
        // Adjust spinner size for easier use
        depDateSpinner.setPreferredSize(new Dimension(120, depDateSpinner.getPreferredSize().height));
        topPanel.add(depDateSpinner, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel capacityLabel = new JLabel("Capacity : ");
        capacityLabel.setFont(labelFont);
        topPanel.add(capacityLabel, gbc);

        gbc.gridx = 1;
        topPanel.add(capacityText, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        JLabel priceLabel = new JLabel("Price : ");
        priceLabel.setFont(labelFont);
        topPanel.add(priceLabel, gbc);

        gbc.gridx = 1;
        topPanel.add(priceText, gbc);

        mainPanel.add(topPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);

        addBtn.setPreferredSize(new Dimension(80, 30));
        addBtn.setForeground(Color.BLACK);
        addBtn.setFocusPainted(false); 
        addBtn.addActionListener(this);

        cancelBtn.setPreferredSize(new Dimension(80, 30));
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setBackground(Color.RED);
        cancelBtn.setFocusPainted(false); 
        cancelBtn.addActionListener(this);
        cancelBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cancelBtn.setBackground(Color.RED);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                cancelBtn.setBackground(Color.RED);
            }
        });

        bottomPanel.add(addBtn);
        bottomPanel.add(cancelBtn);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        this.getContentPane().add(mainPanel);
        setLocationRelativeTo(mw);
        setVisible(true);
    }

    /**
     * Handles button click events for Add and Cancel buttons.
     *
     * @param ae The {@code ActionEvent} triggered by the button click.
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == addBtn) {
            addFlight();
        } else if (ae.getSource() == cancelBtn) {
            this.setVisible(false);
        }
    }

    /**
     * Validates user input and adds a new flight to the flight booking system.
     * Displays error messages if input validation fails.
     */
    private void addFlight() {
        try {
            String flightNumber = flightNoText.getText();
            String origin = originText.getText();
            String destination = destinationText.getText();
            LocalDate departureDate;
            int capacity;
            double price;

            try {
                Date selectedDate = (Date) depDateSpinner.getValue();
                if (selectedDate == null) {
                    throw new FlightBookingSystemException("Please select a valid date.");
                }
                departureDate = new java.sql.Date(selectedDate.getTime()).toLocalDate();
            } catch (Exception e) {
                throw new FlightBookingSystemException("Please select a valid date.");
            }

            try {
                capacity = Integer.parseInt(capacityText.getText());
            } catch (NumberFormatException nfe) {
                throw new FlightBookingSystemException("Capacity must be a valid integer");
            }

            try {
                price = Double.parseDouble(priceText.getText());
            } catch (NumberFormatException nfe) {
                throw new FlightBookingSystemException("Price must be a valid number");
            }

            Command addFlight = new AddFlight(flightNumber, origin, destination, departureDate, capacity, price);
            addFlight.execute(mw.getFlightBookingSystem());

            mw.displayFlights();

            this.setVisible(false);
        } catch (FlightBookingSystemException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Entry point to launch the AddFlightWindow.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AddFlightWindow(null); // Replace null with your MainWindow instance
        });
    }
}
