/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package auto_ecole.model;

import java.util.Date;

public class Examen {
    private int id;
    private Date dateExamen;
    private String heureExamen;
    private int vehiculeId;
    private int instructeurId;

    // Constructeur avec paramètres
    public Examen(int id, Date dateExamen, String heureExamen, int vehiculeId, int instructeurId) {
        this.id = id;
        this.dateExamen = dateExamen;
        this.heureExamen = heureExamen;
        this.vehiculeId = vehiculeId;
        this.instructeurId = instructeurId;
    }
    public Examen( Date dateExamen, String heureExamen, int vehiculeId, int instructeurId) {
        this.dateExamen = dateExamen;
        this.heureExamen = heureExamen;
        this.vehiculeId = vehiculeId;
        this.instructeurId = instructeurId;
    }
    
     // Constructeur sans paramètres
    public Examen() {
        this.id = 0;
        this.dateExamen = new Date();
        this.heureExamen = "";
        this.vehiculeId = 0;
        this.instructeurId = 0;
    }
    
    // Getters
    public int getId() {
        return id;
    }

    public Date getDateExamen() {
        return dateExamen;
    }

    public String getHeureExamen() {
        return heureExamen;
    }

    public int getVehiculeId() {
        return vehiculeId;
    }

    public int getInstructeurId() {
        return instructeurId;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setDateExamen(Date dateExamen) {
        this.dateExamen = dateExamen;
    }

    public void setHeureExamen(String heureExamen) {
        this.heureExamen = heureExamen;
    }

    public void setVehiculeId(int vehiculeId) {
        this.vehiculeId = vehiculeId;
    }

    public void setInstructeurId(int instructeurId) {
        this.instructeurId = instructeurId;
    }
}

