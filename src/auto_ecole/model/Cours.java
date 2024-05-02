package auto_ecole.model;

import java.util.*;

public class Cours {
    private int id;
    private String titre;
    private Date dateDebut;
    private Date dateFin;
    private String heureDebut;
    private String heureFin;
    private int vehiculeId;
    
    public Cours(String titre, Date dateDebut, Date dateFin, String heureDebut, String heureFin, int vehiculeId) {
        this.titre = titre;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.vehiculeId = vehiculeId;
    }

    public Cours(int id, String titre, Date dateDebut, Date dateFin, String heureDebut, String heureFin, int vehiculeId) {

   this.id = id;
        this.titre = titre;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.vehiculeId = vehiculeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }
    public void setDateFin(Date dateDebut) {
        this.dateFin = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

   
    

   


    public String getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(String heureDebut) {
        this.heureDebut = heureDebut;
    }

    public String getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(String heureFin) {
        this.heureFin = heureFin;
    }

    public int getVehiculeId() {
        return vehiculeId;
    }

    public void setVehiculeId(int vehiculeId) {
        this.vehiculeId = vehiculeId;
    }
}
