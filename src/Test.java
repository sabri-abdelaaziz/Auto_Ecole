
import auto_ecole.database.DatabaseConnector;
import auto_ecole.database.UserDao;
import auto_ecole.model.User;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author air
 */
public class Test {
    
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
  public static void main(String[] args) throws Exception {
                new Test();
    }
}
