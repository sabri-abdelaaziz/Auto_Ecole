
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.AbstractBorder;
import javax.swing.border.LineBorder;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author HP
 */
public class Home extends JFrame implements ActionListener{
    private JPanel buttonPanel;
    private JButton[][] buttons;

    public Home() {
        setTitle("Home");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Création du JPanel centré
        buttonPanel = new JPanel(new GridLayout(3, 3, 10, 10)); // Espacement de 10 pixels entre les boutons
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Bordure vide de 20 pixels
        add(buttonPanel, BorderLayout.CENTER);

        buttons = new JButton[3][3];
        
        // Couleurs pour chaque bouton
        //Color[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.ORANGE, Color.CYAN, Color.MAGENTA, Color.PINK, Color.LIGHT_GRAY};
        // Couleurs pour chaque bouton
        Color[] colors = {
                new Color(144, 238, 144), // Vert clair
                new Color(152, 251, 152), // Vert pâle
                new Color(107, 142, 35),  // Vert olive
                new Color(46, 139, 87),   // Vert émeraude
                new Color(127, 255, 0),   // Vert chartreuse
                new Color(154, 205, 50),  // Vert jaune
                new Color(34, 139, 34),   // Vert forêt
                new Color(0, 128, 0),     // Vert moyen
                new Color(0, 100, 0)      // Vert foncé
        };



        // Texte pour chaque bouton
        String[] texts = {"Candidats", "Véhicules", "Moniteurs", "Séances", "Cours", "Examens", "Factures", "Réservations", "Statistiques"};

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                
                buttons[i][j] = new JButton("<html><div style='text-align:center'><b>Gestion<br>" + texts[i * 3 + j] + "</b></div></html>");
                buttons[i][j].setFont(new Font("Times New Roman", Font.BOLD, 16)); // Police en gras et en Times New Roman
                buttons[i][j].setPreferredSize(new Dimension(150, 100)); // Taille préférée des boutons
                buttons[i][j].setBorder(new RoundedBorder(10)); // Coins arrondis avec un rayon de 10 pixels
                buttons[i][j].setOpaque(true);
                buttons[i][j].setBackground(colors[i * 3 + j]); // Affecter une couleur différente à chaque bouton
                buttons[i][j].setForeground(Color.BLACK); // Couleur du texte en noir
                //buttons[i][j].setContentAreaFilled(false); // Supprimer le remplissage de la zone du bouton
                buttonPanel.add(buttons[i][j]);
            }
        }
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].addActionListener(this);
            }
        }
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    
    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton buttonClicked = (JButton)e.getSource();
            // Implement your action for button click here
        }
    }
    private class RoundedBorder extends AbstractBorder {
        private int radius;

        public RoundedBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            super.paintBorder(c, g, x, y, width, height);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2d.dispose();
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton buttonClicked = (JButton) e.getSource();
    String buttonText = buttonClicked.getText(); // Obtenez le texte du bouton cliqué
    // Supprimer les balises HTML du texte du bouton
    String plainText = buttonText.replaceAll("<[^>]*>", "");
    
    // Ouvrir une nouvelle fenêtre avec le titre égal au texte du bouton
    JFrame newFrame = new JFrame(plainText);
    newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fermer seulement cette fenêtre
    newFrame.setSize(500, 400); // Taille de la nouvelle fenêtre
    newFrame.setLocationRelativeTo(null); // Centrer la fenêtre sur l'écran
    newFrame.setVisible(true);
    }
   
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Home());
    }
    
}
