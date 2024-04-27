/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package auto_ecole.Constants;

import java.awt.Color;
import java.awt.Container;
import javax.swing.JFrame;

/**
 *
 * @author air
 */
public class FrameConstantes {
    public FrameConstantes(JFrame j,String Title ){
        // add title to frame
        j.setTitle(Title);
        // set the size width and height 
        j.setSize(1100,900);
        // set the color to your Jframe
        j.setBackground(Color.GREEN);
        //  to set background color to your jframe it's require to add a container
         Container contentPane = j.getContentPane();
         //creat a costumized color rgb
        Color customColor = new Color(0x11, 0x99, 0x87);

        // Set the background color of the content pane
        contentPane.setBackground(customColor);
       
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        j.setLocationRelativeTo(null);
        j.setVisible(true);
    }
}
