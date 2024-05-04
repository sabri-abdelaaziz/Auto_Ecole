/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package auto_ecole.model;

import java.util.Date;

public class Reservation {
    private int id;
    private int eleveId;
    private int coursId;
    private Date dateReservation;
    private String heureReservation;

    // Constructor
    public Reservation(int id, int eleveId, int coursId, Date dateReservation, String heureReservation) {
        this.id = id;
        this.eleveId = eleveId;
        this.coursId = coursId;
        this.dateReservation = dateReservation;
        this.heureReservation = heureReservation;
    }
    
     public Reservation(int eleveId, int coursId, Date dateReservation, String heureReservation) {
        this.eleveId = eleveId;
        this.coursId = coursId;
        this.dateReservation = dateReservation;
        this.heureReservation = heureReservation;
    }
     
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEleveId() {
        return eleveId;
    }

    public void setEleveId(int eleveId) {
        this.eleveId = eleveId;
    }

    public int getCoursId() {
        return coursId;
    }

    public void setCoursId(int coursId) {
        this.coursId = coursId;
    }

    public Date getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(Date dateReservation) {
        this.dateReservation = dateReservation;
    }

    public String getHeureReservation() {
        return heureReservation;
    }

    public void setHeureReservation(String heureReservation) {
        this.heureReservation = heureReservation;
    }
    
}

