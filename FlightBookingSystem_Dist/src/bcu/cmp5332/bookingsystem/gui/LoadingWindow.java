package bcu.cmp5332.bookingsystem.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The {@code LoadingWindow} class represents a splash screen with a circular loading indicator.
 * It is displayed while the main application is loading. This class extends {@code JFrame}
 * and customizes the window to be full-screen with a background image and logo.
 * 
 * <p>The circular loading bar is displayed at the bottom of the screen, and it shows the
 * loading progress as a percentage. Once the loading is complete, the main application
 * window is opened.</p>
 * 
 * @author Bibek
 * @author Solomon
 * @version 1.0
 * @since 2024-06-15
 */
public class LoadingWindow extends JFrame {
    private CircularProgressBar progressBar;
    private Timer timer;

    /**
     * Constructs a {@code LoadingWindow} and initializes its graphical components,
     * including the background image, logo, and circular loading bar.
     */
    public LoadingWindow() {
        setTitle("Loading...");
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        JLabel background = new JLabel(new ImageIcon("resources/images/Home.png"));
        background.setLayout(new GridBagLayout());
        add(background);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 50, 0); 
        gbc.anchor = GridBagConstraints.SOUTH;

        // Circular loading bar at the bottom
        progressBar = new CircularProgressBar();
        progressBar.setPreferredSize(new Dimension(100, 100)); 

        gbc.gridy = 1;
        gbc.insets = new Insets(50, 0, 50, 0); 
        background.add(progressBar, gbc);

        simulateLoading();
    }

    /**
     * Simulates the loading process by incrementing the progress bar value over a
     * specified duration. Once the loading is complete, the main application window
     * is opened.
     */
    private void simulateLoading() {
        int duration = 5000; 
        int delay = 100; 
        int increment = 100 * delay / duration; 

        timer = new Timer(delay, new ActionListener() {
            int progress = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                progress += increment;
                progressBar.setProgress(progress);

                if (progress >= 100) {
                    timer.stop();
                    dispose();

                    new MainWindow(null).setVisible(true);
                }
            }
        });
        timer.start();
    }

    /**
     * The main method to demonstrate the {@code LoadingWindow} in a standalone frame.
     * 
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoadingWindow().setVisible(true);
        });
    }

	public void startLoadingProcess() {
		// TODO Auto-generated method stub
		
	}
}

/**
 * The {@code CircularProgressBar} class represents a custom circular progress bar component.
 * It extends {@code JComponent} and provides functionality to display a circular progress
 * indicator with a percentage text.
 * 
 */
class CircularProgressBar extends JComponent {
    private int progress = 0;

    /**
     * Sets the progress value for the circular progress bar and repaints the component.
     * 
     * @param progress The progress value (0-100).
     */
    public void setProgress(int progress) {
        this.progress = progress;
        repaint();
    }

    /**
     * Paints the circular progress bar, including the background circle, progress arc,
     * and percentage text.
     * 
     * @param g The {@code Graphics} object used for painting.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int size = Math.min(getWidth(), getHeight());
        int x = (getWidth() - size) / 2;
        int y = (getHeight() - size) / 2;
        int thickness = 10;
        
        g2d.setColor(new Color(230, 230, 230));
        g2d.fillOval(x, y, size, size);
        
        g2d.setColor(new Color(70, 130, 180));
        g2d.setStroke(new BasicStroke(thickness));
        int angle = (int) (360 * (progress / 100.0));
        g2d.drawArc(x + thickness / 2, y + thickness / 2, size - thickness, size - thickness, 90, -angle);
        
        String text = progress + "%";
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.setColor(Color.BLACK);
        g2d.drawString(text, getWidth() / 2 - textWidth / 2, getHeight() / 2 + textHeight / 4);
        
        g2d.dispose();
    }
}