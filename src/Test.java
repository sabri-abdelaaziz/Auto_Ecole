
import auto_ecole.database.DatabaseConnector;
import auto_ecole.database.UserDao;
import auto_ecole.model.User;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JFrame;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author air
 */
public class Test extends JFrame {
    
  public Test() throws SQLException{
            UserDao userDao = new UserDao();
            List<User> userList = userDao.getAll();
            if (userList != null) {
                for (User user : userList) {
                    System.out.println(user.getNom());
                }
            } else {
                System.out.println("User not found");
            }
     
         
}
    public static void main(String[] args) throws URISyntaxException {
        // Get the URL of the resource
        URL url = Test.class.getResource("ads.png");
        
        // Check if the resource exists
        if (url != null) {
            // Get the file path by converting URL to URI
            String path = url.toURI().getPath();
            
            // Print the path to the console
            System.out.println("Path to ads.png: " + path);
        } else {
            System.err.println("Resource ads.png not found");
        }
    
    }}
