package auto_ecole.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginApp extends JFrame {

    private JPanel mainPanel;
    private JPasswordField passwordField;
    private JButton verifyButton;
    private static final String CONSTANT_PASSWORD = "123";

    public LoginApp() {
        setTitle("S'authentifier");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(50, 50, 50, 50); // Marge autour du centrePanel

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);

        // Partie droite : Image
        ImageIcon logoIcon = new ImageIcon("./src/driver.jpg");
        JLabel logoLabel = new JLabel(logoIcon);
        centerPanel.add(logoLabel, BorderLayout.EAST);

        // Partie gauche : Label "Enter Password" et champ de mot de passe
        JPanel passwordPanel = new JPanel(new GridBagLayout());
        passwordPanel.setBackground(Color.WHITE);

        JLabel enterPasswordLabel = new JLabel("Entrer Mot de passe :");
        enterPasswordLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14)); // Utilisation de Times New Roman
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 10, 0);
        passwordPanel.add(enterPasswordLabel, gbc);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Times New Roman", Font.PLAIN, 18)); // Utilisation de Times New Roman
        passwordField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    verifyPassword();
                }
            }
        });
        gbc.insets = new Insets(0, 0, 10, 0); // Ajustement de la marge pour laisser un espace vide
        passwordPanel.add(passwordField, gbc);

        gbc.gridy++; // Ajout d'une ligne supplémentaire
        verifyButton = new JButton("Vérifer");
        verifyButton.setFont(new Font("Times New Roman", Font.PLAIN, 16)); 
        verifyButton.setBackground(new Color(173, 216, 230));
        verifyButton.setOpaque(true);
        verifyButton.setBorderPainted(false);
        verifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verifyPassword();
            }
        });
        passwordPanel.add(verifyButton, gbc);

        centerPanel.add(passwordPanel, BorderLayout.CENTER);

        mainPanel.add(centerPanel, gbc);
        setContentPane(mainPanel);
        setVisible(true);
    }

    private void verifyPassword() {
        String enteredPassword = new String(passwordField.getPassword());
        if (enteredPassword.equals(CONSTANT_PASSWORD)) {

            JLabel successLabel = new JLabel("Mot de passe verifié ..");
            JPanel successPanel = new JPanel();
            //successPanel.setBackground(Color.WHITE);
            successPanel.setLayout(new BorderLayout());

            //successLabel.setForeground(Color.GREEN);
            successLabel.setForeground(new Color(0, 128, 255)); // Un bleu ciel légèrement plus foncé
            successLabel.setHorizontalAlignment(JLabel.CENTER); // Center-align the text
            successLabel.setFont(new Font("Times New Roman", Font.BOLD, 16)); // Set font and style

            // Add the success label to the success panel
            successPanel.add(successLabel, BorderLayout.CENTER);

            // Show the custom success message panel
            verifyButton.setText("Chargement des données...");
            JOptionPane.showMessageDialog(null, successPanel, "Mot de passe Correct", JOptionPane.PLAIN_MESSAGE);
            try {
                new Home().setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(LoginApp.class.getName()).log(Level.SEVERE, null, ex);
            }
            setVisible(false);

        } else {
            JOptionPane.showMessageDialog(null, "Incorrect Password", "Mot de passe Incorrect", JOptionPane.ERROR_MESSAGE);
        }
    }
}
