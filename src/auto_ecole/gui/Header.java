package auto_ecole.gui;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.Image;

public class Header extends JPanel {
    private JLabel titleLabel;
    private JLabel welcomeLabel;
    private JLabel timeLabel;
    private JLabel logoLabel;

    public Header() {
        // Set the layout to horizontal
        setLayout(new BorderLayout());
       setBackground(new Color(0, 100, 0));
        // Load your logo image
        ImageIcon logoIcon = null;
        try {
            ImageIcon originalIcon = new ImageIcon(getClass().getResource("back2.jpeg"));
            Image originalImage = originalIcon.getImage();
            Image scaledImage = originalImage.getScaledInstance(200, 70, Image.SCALE_SMOOTH); // Adjust size here
            logoIcon = new ImageIcon(scaledImage);
        } catch (NullPointerException e) {
            System.err.println("Image file not found: " + e.getMessage());
        }
        logoLabel = new JLabel(logoIcon);
        add(logoLabel, BorderLayout.WEST);

    
        // Create and add the welcome label
        welcomeLabel = new JLabel("AUTO ECOLE!");
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 22));
       
        welcomeLabel.setForeground(Color.white);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
           welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER); // Set text alignment to center
      
        add(welcomeLabel, BorderLayout.CENTER);

        // Create and add the time label
        timeLabel = new JLabel();
        updateTime();
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 22));
        timeLabel.setForeground(Color.white);
        timeLabel.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
        add(timeLabel, BorderLayout.EAST);

        // Create a timer to update the time label every second
        Timer timer = new Timer(1000, e -> updateTime());
        timer.start();
    }

    // Method to update the time label
    private void updateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String currentTime = dateFormat.format(new Date());
        timeLabel.setText("Current Time: " + currentTime);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Header");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 60); // Adjusted height

        // Add the header panel to the frame
        frame.add(new Header());

        frame.setVisible(true);
    }
}
