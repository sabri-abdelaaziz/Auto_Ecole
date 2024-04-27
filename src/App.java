import auto_ecole.Constants.FrameConstantes;
import java.sql.*;
import java.util.*;

import auto_ecole.database.*;
import auto_ecole.gui.Home;
import auto_ecole.model.User;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingUtilities;

public class App  extends JFrame implements ActionListener{
    private JLabel passText;
    private JPanel mainPanel;
    private JPasswordField passwordField;
    private JButton verifyButton;
    private static final String CONSTANT_PASSWORD = "password123";

 
    
    public App(){
        
       // Create a JPanel with BoxLayout
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        //mainPanel.setMaximumSize(new Dimension(300, 70)); // Set maximum size

        // Set background color
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setOpaque(false);
        // Create components
      // Create components
        JLabel enterPasswordLabel = new JLabel("Enter Password:");
        enterPasswordLabel.setFont(new Font("Arial", Font.PLAIN, 26)); // Set font
        //enterPasswordLabel.setMaximumSize(new Dimension(300, 30)); // Set maximum size

        passwordField = new JPasswordField(2);
        passwordField.setMaximumSize(new Dimension(650, 60)); // Set maximum size
        passwordField.setFont(new Font("Arial", Font.PLAIN, 18)); // Set font

        verifyButton = new JButton("Verify");
        verifyButton.setMaximumSize(new Dimension(200, 50)); // Set maximum size
        verifyButton.setFont(new Font("Arial", Font.PLAIN, 16)); // Set font
        verifyButton.setBackground(Color.GREEN); // Set background color
        verifyButton.setForeground(Color.BLUE); // Set foreground color


        // Align components horizontally at the center
        enterPasswordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        verifyButton.setAlignmentX(Component.CENTER_ALIGNMENT);

      
        // Add mainPanel to content pane using BorderLayout.CENTER
        getContentPane().add(mainPanel, BorderLayout.CENTER);
         // Load the image
        ImageIcon backgroundImage = new ImageIcon("ressources/background.jpeg");
        JLabel backgroundLabel = new JLabel(backgroundImage);

// Add background label to content pane at the back
       

        // Add dummy components to other regions to fill remaining space
        JPanel northPanel = new JPanel();
        northPanel.setPreferredSize(new Dimension(1, 240)); // Set preferred size
        getContentPane().add(northPanel, BorderLayout.NORTH);

        JPanel southPanel = new JPanel();
        southPanel.setPreferredSize(new Dimension(1, 240)); // Set preferred size
        getContentPane().add(southPanel, BorderLayout.SOUTH);

        JPanel eastPanel = new JPanel();
        eastPanel.setPreferredSize(new Dimension(250, 100)); // Set preferred size
        getContentPane().add(eastPanel, BorderLayout.EAST);

        JPanel westPanel = new JPanel();
        westPanel.setPreferredSize(new Dimension(250, 100)); // Set preferred size
        getContentPane().add(westPanel, BorderLayout.WEST);

        // Action listener for verifyButton
        verifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String enteredPassword = new String(passwordField.getPassword());
                if (enteredPassword.equals(CONSTANT_PASSWORD)) {
                   JLabel successLabel = new JLabel("Password Verified");
                   // Create a custom JPanel for the success message
                    JPanel successPanel = new JPanel();
                    successPanel.setLayout(new BorderLayout());
                    successLabel.setForeground(Color.GREEN); // Set text color to white
                    successLabel.setHorizontalAlignment(JLabel.CENTER); // Center-align the text
                    successLabel.setFont(new Font("Arial", Font.BOLD, 26)); // Set font and style

                    // Add the success label to the success panel
                    successPanel.add(successLabel, BorderLayout.CENTER);

                    // Show the custom success message panel
                    JOptionPane.showMessageDialog(null, successPanel, "Success", JOptionPane.PLAIN_MESSAGE);
                    new Home().setVisible(true);
                    setVisible(false);
                     
                
            
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect Password", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
  // Add components to mainPanel
        mainPanel.add(Box.createVerticalStrut(50)); 
        mainPanel.add(enterPasswordLabel);
        
        mainPanel.add(Box.createRigidArea(new Dimension(0, 22)));
        mainPanel.add(passwordField);
         mainPanel.add(backgroundLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 22)));
        mainPanel.add(verifyButton);

        mainPanel.add(Box.createVerticalStrut(100));
        // Make the JFrame visible
        setVisible(true);
        
        // this is the constates patames for all JFRAMES in our project check it to see what is there
        new FrameConstantes(this,"Login Page");
     
      
    }
    
    
    
    
    public static void main(String[] args) throws Exception {
          SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new App().setVisible(true);
            }
        });
    
    
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new App().setVisible(true);
            }
        });
    
    
    }
}
