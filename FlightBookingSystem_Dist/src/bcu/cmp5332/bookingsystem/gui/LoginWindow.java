package bcu.cmp5332.bookingsystem.gui;

import javax.swing.*;

import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginWindow extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton forgotPasswordButton; // New button for forgot password

    public LoginWindow() {
        setTitle("Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 248, 255)); // Light blue background

        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Login to Your Account");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(70, 130, 180)); // Dark blue color

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(titleLabel, gbc);

        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        loginPanel.add(usernameLabel, gbc);

        usernameField = new JTextField(15);
        usernameField.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        loginPanel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        loginPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(15);
        passwordField.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        loginPanel.add(passwordField, gbc);

        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(70, 130, 180)); // Dark blue color
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 0, 0, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(loginButton, gbc);

        // Add forgot password button
        forgotPasswordButton = new JButton("Forgot Password?");
        forgotPasswordButton.setForeground(Color.BLUE);
        forgotPasswordButton.setBorderPainted(false);
        forgotPasswordButton.setContentAreaFilled(false); // Make it transparent
        forgotPasswordButton.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 0, 0, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(forgotPasswordButton, gbc);

        // Action listener for forgot password button
        forgotPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // You can implement your logic here to handle forgot password functionality
                // For demonstration, showing a dialog with admin's password
                JOptionPane.showMessageDialog(LoginWindow.this,
                        "Username: admin\nPassword: admin",
                        "Forgot Password",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Action listener for login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (isValidAdminLogin(username, password)) {
                    // Simulate loading process and then open main window
                    LoadingWindow loadingWindow = new LoadingWindow();
                    loadingWindow.setVisible(true);
                    loadingWindow.startLoadingProcess();

                    // For demonstration, dispose of LoginWindow after successful login
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(LoginWindow.this,
                            "Invalid username or password",
                            "Login Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        mainPanel.add(loginPanel, BorderLayout.CENTER);
        add(mainPanel);
    }

    private boolean isValidAdminLogin(String username, String password) {
        // Simulated login validation logic
        return username.equals("admin") && password.equals("admin");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginWindow().setVisible(true);
            }
        });
    }
}