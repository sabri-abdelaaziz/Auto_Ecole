/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package auto_ecole.gui;

import auto_ecole.Constants.FrameConstantes;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 *
 * @author air
 */
public class Home extends JFrame implements ActionListener {
    
    public Home(){
        new FrameConstantes(this,"Home");
        ImageIcon backgroundImage = new ImageIcon("background.jpeg");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        add(backgroundLabel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
 
    }
    
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                new Home().setVisible(true);
            }
        });
    }
    
}
