package auto_ecole.model;

public class User {
    

private int id;
private String nom;
private String prenom;
private String adresse;	
private String telephone;

 public User(String nom1, String prenom1, String adresse1, int tele) {}
public User( String nom, String prenom, String adresse, String telephone) {
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.telephone = telephone;
    }
    // Constructeur avec paramètres
    public User(int id, String nom, String prenom, String adresse, String telephone) {
        this.id =id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.telephone = telephone;
    }
    
    
    // Getter pour l'ID
    public int getId() {
        return id;
    }

    // Setter pour l'ID
    public void setId(int id) {
        this.id = id;
    }

    // Getter pour le nom
    public String getNom() {
        return nom;
    }

    // Setter pour le nom
    public void setNom(String nom) {
        this.nom = nom;
    }

    // Getter pour le prénom
    public String getPrenom() {
        return prenom;
    }

    // Setter pour le prénom
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    // Getter pour l'adresse
    public String getAdresse() {
        return adresse;
    }

    // Setter pour l'adresse
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    // Getter pour le téléphone
    public String getTelephone() {
        return telephone;
    }

    // Setter pour le téléphone
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

}
