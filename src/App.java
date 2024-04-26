import java.sql.*;
import java.util.*;

import auto_ecole.database.*;
import auto_ecole.model.User;

public class App {
    public static void main(String[] args) throws Exception {
        String username = "admin";
        String password = "123";
        try (Connection connection = DatabaseConnector.connect()) {
            UserDao userDao = new UserDao(connection);
            List<User> userList = userDao.getAll();
            if (userList != null) {
                for (User user : userList) {
                    System.out.println(user.getUsername());
                }
            } else {
                System.out.println("User not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
