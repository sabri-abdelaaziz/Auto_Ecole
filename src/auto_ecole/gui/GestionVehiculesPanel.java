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

public class GestionVehiculesPanel extends JPanel {
    public GestionVehiculesPanel() {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(400, 300));
        JLabel label = new JLabel("Gestion Véhicules");
        label.setFont(new Font("Arial", Font.BOLD, 20));
        add(label, BorderLayout.CENTER);
    }
}

