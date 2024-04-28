package auto_ecole.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Moniteurs extends JPanel {
    private JPanel candidats;
    
    public Moniteurs(String title) {
        JLabel label = new JLabel(title);
        add(label);
    }
}
