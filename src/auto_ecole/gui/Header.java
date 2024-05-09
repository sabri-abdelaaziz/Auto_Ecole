package auto_ecole.gui;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Header extends JPanel {
    private JLabel titleLabel;
    private JLabel welcomeLabel;
    private JLabel timeLabel;
    private JLabel logoLabel;

    public Header() {
        // Set the layout to horizontal
        setLayout(new BorderLayout());
        setBackground(Color.BLACK); // Changement de la couleur de fond en noir

        
        // Load your logo image
        ImageIcon logoIcon = null;
        try {
            ImageIcon originalIcon = new ImageIcon("./src/logo.png");
            Image originalImage = originalIcon.getImage();
            
            // Scale the image
            Image scaledImage = originalImage.getScaledInstance(200, 80, Image.SCALE_SMOOTH);

            // Create ImageIcon from scaled image
            logoIcon = new ImageIcon(scaledImage);
        } catch (NullPointerException e) {
            System.err.println("Header, Image file not found: " + e.getMessage());
        }
        logoLabel = new JLabel(logoIcon);
        add(logoLabel, BorderLayout.WEST);

    
        // Create and add the welcome label
        welcomeLabel = new JLabel();
        welcomeLabel.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
        //welcomeLabel.setForeground(new Color(30, 144, 255)); // Couleur bleue vive
        welcomeLabel.setText("Route vers la Réussite: Auto-École Horizon. ");
        welcomeLabel.setForeground(Color.white);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(25, 5, 25, 5));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER); // Set text alignment to center
      
        add(welcomeLabel, BorderLayout.CENTER);

        // Create and add the time label
        timeLabel = new JLabel();
        updateTime();
        timeLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        timeLabel.setForeground(Color.white);
        timeLabel.setBorder(BorderFactory.createEmptyBorder(25, 5, 25, 5));
        add(timeLabel, BorderLayout.EAST);

        // Create a timer to update the time label every second
        Timer timer = new Timer(1000, e -> updateTime());
        timer.start();
    }

    // Method to update the time label
    private void updateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String currentTime = dateFormat.format(new Date());
        timeLabel.setText("Temps actuel : " + currentTime);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Header");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 100); // Adjusted height

        // Add the header panel to the frame
        frame.add(new Header());

        frame.setVisible(true);
    }
}
