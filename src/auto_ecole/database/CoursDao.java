/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package auto_ecole.database;

import java.sql.*;
import java.util.*;

import auto_ecole.model.Cours;

public interface CoursDao {
    void createCours(Cours cours);
    Cours getCoursById(int id);
    List<Cours> getAllCours();
    void updateCours(Cours cours);
    void deleteCours(int id);
}

