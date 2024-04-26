// package auto_ecole.controller;

// import java.sql.*;
// import java.util.*;

// import auto_ecole.database.*;
// import auto_ecole.model.*;

// public class UserController {

// try (Connection connection = DatabaseConnector.connect()) {
// UserDao userDao = new UserDao(connection);
// List<User> userList = userDao.getAll();
// if (userList != null) {
// for (User user : userList) {
// System.out.println(user.getUsername());
// }
// } else {
// System.out.println("User not found");
// }
// } catch (SQLException e) {
// e.printStackTrace();
// }
// }
