package auto_ecole.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class AccueilPanel extends JPanel {

    private BufferedImage image;

    public AccueilPanel() {
        // Load the image
        try {
            image = ImageIO.read(new File("./src/accuiel.jpg")); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the image if it's not null
        if (image != null) {
            g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        // Set the preferred size based on the image dimensions
        if (image != null) {
            return new Dimension(image.getWidth(), image.getHeight());
        } else {
            return super.getPreferredSize();
        }
    }
}
