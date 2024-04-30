package auto_ecole.model;
import java.util.Date;

public class Seance {
    
    private int id;
    private int coursId;
    private Date dateSeance;
    private String heureDebut;
    private String heureFin;

    public Seance() {}

    public Seance(int coursId, Date dateSeance, String heureDebut, String heureFin) {
        this.coursId = coursId;
        this.dateSeance = dateSeance;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
    }

    public Seance(int id, int coursId, Date dateSeance, String heureDebut, String heureFin) {
        this.id = id;
        this.coursId = coursId;
        this.dateSeance = dateSeance;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
    }
    
   
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCoursId() {
        return coursId;
    }

    public void setCoursId(int coursId) {
        this.coursId = coursId;
    }

    public Date getDateSeance() {
        return dateSeance;
    }

    public void setDateSeance(Date dateSeance) {
        this.dateSeance = dateSeance;
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
}
