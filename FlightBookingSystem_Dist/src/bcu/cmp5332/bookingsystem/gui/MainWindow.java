
package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * The {@code MainWindow} class represents the main window of the Flight Booking Management System GUI.
 * It extends {@code JFrame} and implements {@code ActionListener} to handle user interactions.
 *
 * <p>The main window consists of three main components: side panel, center panel, and main panel.
 * The side panel contains menu options for navigation, including Home, Flights, Bookings, Customers, and Settings.
 * The center panel dynamically updates to display different content based on user actions.</p>
 *
 * <p>The GUI provides functionalities such as viewing flights, managing bookings, adding/removing customers,
 * and handling system settings. It interacts with {@code FlightBookingSystem} for backend operations.</p>
 *
 * <p>This class uses Swing components for the graphical interface, including panels, buttons, labels, and dialog boxes.
 * It supports logout functionality and dynamic content updates when menu options are selected.</p>
 *
 * @author Bibek
 * @author Solomon
 * @version 1.0
 * @since 2024-06-15
 * @see FlightBookingSystem
 * @see Flight
 * @see JPanel
 * @see JFrame
 * @see ActionListener
 */
public class MainWindow extends JFrame implements ActionListener {

    private FlightBookingSystem fbs;
    private JPanel mainPanel;
    private JPanel sidePanel;

    private JPanel centerPanel;
    private String currentPanel;

    private String[] initialSidePanelOptions = {"Home", "Flights", "Bookings", "Customers", "Settings"};

    /**
     * Constructor for MainWindow.
     * Initializes the GUI components and sets up the initial state.
     */
    public MainWindow(FlightBookingSystem fbs) {
        this.fbs = fbs;
        this.currentPanel = "Home";
        initialize();
    }

    /**
     * Getter for the FlightBookingSystem instance.
     *
     * @return The FlightBookingSystem instance.
     */
    public FlightBookingSystem getFlightBookingSystem() {
        return fbs;
    }

    /**
     * Initializes the GUI components of the MainWindow.
     */
    private void initialize() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            // Handle the look and feel exception
        }

        setTitle("AERO VISION MANAGEMENT");


        mainPanel = new JPanel(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        sidePanel = createSidePanel();
        add(sidePanel, BorderLayout.WEST);

        centerPanel = new JPanel();
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setLayout(new BorderLayout());

        showHomeMessage(); 

        add(centerPanel, BorderLayout.CENTER);

        setSize(1200, 800);
        setLocationRelativeTo(null); 
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * Creates the sidePanel containing menu options.
     *
     * @return JPanel representing the side menu.
     */
    private JPanel createSidePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(8, 131, 149));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Booking", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(30));

        for (String option : initialSidePanelOptions) {
            JLabel label = createSidePanelButton(option);
            panel.add(label);
            panel.add(Box.createVerticalStrut(20));
        }

        JLabel logoutButton = new JLabel("Logout");
        logoutButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        logoutButton.setFont(new Font("Serif", Font.BOLD, 18));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        logoutButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logoutButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoutButtonMouseClicked(evt);
            }
        });

        panel.add(Box.createVerticalGlue());
        panel.add(logoutButton);
        panel.add(Box.createVerticalStrut(20));

        return panel;
    }

    /**
     * Creates a menu option button for the sidePanel.
     *
     * @param text The label text for the button.
     * @return JLabel representing the button.
     */
    private JLabel createSidePanelButton(String text) {
        JLabel label = new JLabel(text);
        label.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        label.setFont(new Font("Serif", Font.BOLD, 20));
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        label.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelMouseClicked(evt, text);
            }
        });

        return label;
    }

    /**
     * Handles mouse click events on sidePanel menu options.
     *
     * @param evt  MouseEvent instance.
     * @param text The label text of the clicked option.
     */
    private void labelMouseClicked(java.awt.event.MouseEvent evt, String text) {
        switch (text) {
            case "Home":
                showHomeMessage();
                currentPanel = "Home";
                updateSidePanel(initialSidePanelOptions);
                break;
            case "Flights":
                displayFlightsOptions();
                currentPanel = "Flights";
                break;
            case "Bookings":
                displayBookingsOptions();
                currentPanel = "Bookings";
                break;
            case "Customers":
                displayCustomersOptions();
                currentPanel = "Customers";
                break;
            case "Settings":
                // Implement Settings display logic here
                currentPanel = "Settings";
                break;
            case "View Flights":
                displayFlights();
                currentPanel = "View Flights";
                break;
            case "Add Flight":
                new AddFlightWindow(this);
                currentPanel = "Add Flight";
                break;
            case "Delete Flight":
                new DeleteFlightWindow(this, fbs);
                currentPanel = "Delete Flight";
                break;
            case "Show Flight":
                showFlightDetails();
                currentPanel = "Show Flight";
                break;
            case "Issue Booking":
                new IssueBookingWindow(this, fbs);
                currentPanel = "Issue Booking";
                break;
            case "Update Booking":
                openUpdateBookingDialog();
                break;
            case "Cancel Booking":
                openCancelBookingDialog();
                currentPanel = "Cancel Booking";
                break;
            case "List Bookings":
                displayBookings();
                currentPanel = "List Bookings";
                break;
            case "View Customers":
                displayListCustomerWindow();
                currentPanel = "View Customers";
                break;
            case "Add Customer":
                new AddCustomerWindow(fbs);
                currentPanel = "Add Customer";
                break;
                
            case "Show Customer":
                showCustomerDetails();
                currentPanel = "Show Customer";
                break;
                
            case "Delete Customer":
                new DeleteCustomerWindow(fbs);
                currentPanel = "Delete Customer";
                break;
            case "Back":
                showHomeMessage();
                currentPanel = "Home";
                updateSidePanel(initialSidePanelOptions);
                break;
            default:
                showHomeMessage();
                currentPanel = "Home";
                updateSidePanel(initialSidePanelOptions);
                break;
        }
    }

    /**
     * Handles logout button click event.
     *
     * @param evt MouseEvent instance.
     */
    private void logoutButtonMouseClicked(java.awt.event.MouseEvent evt) {
        int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            // Dispose the current MainWindow
            dispose();

            // Open the LoginWindow
            LoginWindow loginWindow = new LoginWindow();
            loginWindow.setVisible(true);  // Display the login window
        }
    }

    /**
     * Displays the home page content in the centerPanel.
     * Includes welcome message, description, and navigation buttons.
     */
    private void showHomeMessage() {
        centerPanel.removeAll();
        centerPanel.setLayout(new BorderLayout());

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        JLabel headingLabel = new JLabel("Welcome to Our Flight Booking System", JLabel.CENTER);
        headingLabel.setFont(new Font("Serif", Font.BOLD, 30));
        headingLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(headingLabel);
        leftPanel.add(Box.createVerticalStrut(20));

        JLabel descriptionLabel = new JLabel("<html><div style='text-align: left;'>\"Fly Smarter, Fly Easier with Aero Vision Management!\"</div></html>", JLabel.CENTER);
        descriptionLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        descriptionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(descriptionLabel);
        leftPanel.add(Box.createVerticalStrut(30));

        String[] homeOptions = {"Flights", "Bookings", "Customers", "Settings"};
        String[] imagePaths = {"./resources/images/8.png", "./resources/images/9.png", "./resources/images/10.png", "./resources/images/11.png"};

        int buttonWidth = 200; 
        int buttonHeight = 120; 

        for (int i = 0; i < homeOptions.length; i++) {
            String option = homeOptions[i];
            String imagePath = imagePaths[i];

            JPanel buttonPanel = new JPanel();
            buttonPanel.setBackground(Color.WHITE);
            buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
            buttonPanel.setMaximumSize(new Dimension(400, buttonHeight + 20));

            JButton button = new JButton(option);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            button.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
            
            button.setMaximumSize(new Dimension(buttonWidth, buttonHeight));
            
            button.setFont(new Font("Serif", Font.BOLD, 20));
            button.setForeground(Color.BLACK);
            button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            
            if (option.equals("Settings")) {
                button.addActionListener(e -> JOptionPane.showMessageDialog(centerPanel, "This feature will be available soon", "Information", JOptionPane.INFORMATION_MESSAGE));
            } else {
                button.addActionListener(e -> labelMouseClicked(null, option));
            }
            
            buttonPanel.add(button);
            buttonPanel.add(Box.createHorizontalStrut(10)); 

            ImageIcon icon = new ImageIcon(imagePath);
            Image img = icon.getImage();
            Image scaledImg = img.getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImg);
            JLabel imgLabel = new JLabel(scaledIcon);
            imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            buttonPanel.add(imgLabel);
            leftPanel.add(buttonPanel);
            leftPanel.add(Box.createVerticalStrut(10)); 
        }

        leftPanel.add(Box.createVerticalStrut(20));

        ImageIcon mainImageIcon = new ImageIcon("./resources/images/vertical.jpg"); 
        Image mainImage = mainImageIcon.getImage();
        Image resizedMainImage = mainImage.getScaledInstance(400, 800, Image.SCALE_SMOOTH); 
        ImageIcon resizedMainImageIcon = new ImageIcon(resizedMainImage);
        JLabel mainImageLabel = new JLabel(resizedMainImageIcon);
        mainImageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel mainImagePanel = new JPanel();
        mainImagePanel.setLayout(new BorderLayout());
        mainImagePanel.add(mainImageLabel, BorderLayout.CENTER);
        mainImagePanel.setBorder(new EmptyBorder(0, -10, 0, 0));

        centerPanel.add(leftPanel, BorderLayout.WEST);
        centerPanel.add(mainImagePanel, BorderLayout.EAST);
        
        leftPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        centerPanel.revalidate();
        centerPanel.repaint();
    }

    /**
     * Opens a dialog window for updating a booking in the Flight Booking Management System.
     * This method creates a modal dialog with an {@code UpdateBookingPanel} embedded in it,
     * allowing users to update booking details such as flight selection and customer information.
     */
    private void openUpdateBookingDialog() {
        JDialog dialog = new JDialog(this, "Update Booking", true);
        dialog.getContentPane().add(new UpdateBookingPanel(fbs));
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    /**
     * Displays the options related to flights in the side panel of the main window.
     * This method updates the side panel with buttons such as View Flights, Add Flight,
     * Delete Flight, Show Flight, and Back, allowing users to navigate and perform actions
     * related to flights within the application.
     */
    private void displayFlightsOptions() {
        updateSidePanel(new String[]{"View Flights", "Add Flight", "Delete Flight", "Show Flight", "Back"});
    }

    /**
     * Displays the options related to bookings in the side panel of the main window.
     * This method updates the side panel with buttons such as Issue Booking, Update Booking,
     * Cancel Booking, List Bookings, and Back, allowing users to navigate and perform actions
     * related to bookings within the application.
     */
    private void displayBookingsOptions() {
        updateSidePanel(new String[]{"Issue Booking", "Update Booking", "Cancel Booking", "List Bookings", "Back"});
    }

    /**
     * Displays the options related to customers in the side panel of the main window.
     * This method updates the side panel with buttons such as View Customers, Add Customer,
     * Delete Customer, Show Customer, and Back, allowing users to navigate and perform actions
     * related to customers within the application.
     */
    private void displayCustomersOptions() {
        updateSidePanel(new String[]{"View Customers", "Add Customer", "Delete Customer", "Show Customer", "Back"});
    }

    /**
     * Updates the side panel with the specified options.
     * This method dynamically updates the side panel with buttons based on the provided options,
     * allowing flexible navigation and action execution within the Flight Booking Management System.
     *
     * @param options An array of strings representing the options to be displayed on the side panel.
     */
    private void updateSidePanel(String[] options) {
        if (sidePanel == null) {
            sidePanel = createSidePanel(); 
            add(sidePanel, BorderLayout.WEST);
        } else {
            sidePanel.removeAll();

            JLabel titleLabel = new JLabel("Booking", SwingConstants.CENTER);
            titleLabel.setFont(new Font("Serif", Font.BOLD, 28));
            titleLabel.setForeground(Color.WHITE);

            sidePanel.add(titleLabel);
            sidePanel.add(Box.createVerticalStrut(30));

            for (String option : options) {
                sidePanel.add(createSidePanelButton(option));
                sidePanel.add(Box.createVerticalStrut(20));
            }

            JLabel logoutButton = new JLabel("Logout");
            logoutButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            logoutButton.setFont(new Font("Serif", Font.BOLD, 18));
            logoutButton.setForeground(Color.WHITE);
            logoutButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            logoutButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            logoutButton.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    logoutButtonMouseClicked(e);
                }
            });

            sidePanel.add(Box.createVerticalGlue());
            sidePanel.add(logoutButton);
            sidePanel.add(Box.createVerticalStrut(20));

            sidePanel.revalidate();
            sidePanel.repaint();
        }
    }

    /**
     * Displays details of a specific flight based on user input.
     * This method prompts the user to enter a Flight ID and, upon valid input, displays
     * a window with detailed information about the selected flight using {@code ShowFlightWindow}.
     */
    private void showFlightDetails() {
        String flightIdStr = JOptionPane.showInputDialog(this, "Enter Flight ID:", "View Flight Details", JOptionPane.PLAIN_MESSAGE);
        if (flightIdStr != null && !flightIdStr.trim().isEmpty()) {
            try {
                int flightId = Integer.parseInt(flightIdStr.trim());
                new ShowFlightWindow(flightId, fbs);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid Flight ID format.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Displays details of a specific customer based on user input.
     * This method prompts the user to enter a Customer ID and, upon valid input, displays
     * a window with detailed information about the selected customer using {@code ShowCustomerWindow}.
     */
    private void showCustomerDetails() {
        String customerIdStr = JOptionPane.showInputDialog(this, "Enter Customer ID:", "View Customer Details", JOptionPane.PLAIN_MESSAGE);
        if (customerIdStr != null && !customerIdStr.trim().isEmpty()) {
            try {
                int customerId = Integer.parseInt(customerIdStr.trim());
                new ShowCustomerWindow(customerId, fbs);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid Customer ID format.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Displays a window with a list of customers.
     * This method switches the center panel to display a list of customers using {@code ListCustomerPanel}.
     */
    private void displayListCustomerWindow() {
        switchToPanel(new ListCustomerPanel(fbs));
    }

    /**
     * Displays a window with a list of bookings.
     * This method switches the center panel to display a list of bookings using {@code ListBookingPanel}.
     */
    void displayBookings() {
        switchToPanel(new ListBookingPanel(fbs));
    }

    /**
     * Displays a window with a list of flights.
     * This method switches the center panel to display a list of flights using {@code ListFlightPanel}.
     */
    public void displayFlights() {
        switchToPanel(new ListFlightPanel(fbs));
    }
    
    /**
     * Opens a dialog window to cancel a booking.
     */
    private void openCancelBookingDialog() {
        CancelBookingPanel cancelBookingPanel = new CancelBookingPanel(this, fbs);
        cancelBookingPanel.display();
    }

    /**
     * Switches the center panel to display a specified panel.
     * This method removes any existing panel from the center and sets the new panel for display.
     *
     * @param panel The panel to be displayed in the center.
     */
    private void switchToPanel(JPanel panel) {
        centerPanel.removeAll();
        centerPanel.add(panel, BorderLayout.CENTER);
        centerPanel.revalidate();
        centerPanel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle any actions if needed
    }
    
    /**
     * Main entry point of the Flight Booking System application.
     * This method loads the flight booking system data from a file, initializes the main window,
     * and displays it on the screen using SwingUtilities.invokeLater for thread safety.
     * It handles IOException and FlightBookingSystemException by displaying error messages
     * in dialog boxes if data loading or initialization fails.
     *
     * @param args The command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        try {
            FlightBookingSystem fbs = FlightBookingSystemData.load();
            SwingUtilities.invokeLater(() -> new MainWindow(fbs));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error loading data from file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (FlightBookingSystemException ex) {
            JOptionPane.showMessageDialog(null, "Flight Booking System Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}