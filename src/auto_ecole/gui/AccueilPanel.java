/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package auto_ecole.gui;

/**
 *
 * @author Abdellatif
 */
import javax.swing.*;
import java.awt.*;

public class AccueilPanel extends JPanel {
    public AccueilPanel() {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(400, 300));
        JLabel label = new JLabel("Welcome to Accueil!");
        label.setFont(new Font("Arial", Font.BOLD, 20));
        add(label, BorderLayout.CENTER);
    }
}

