/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package auto_ecole.model;

/**
 *
 * @author HP
 */
public class Vehicule {
    private int id;
    private String marque;
    private String modele;
    private int anneeFabrication;	
    
    // Constructeur par défaut
    public Vehicule() {
    }

    // Constructeur avec tous les paramètres sauf l'ID (peut être utile lors de l'insertion dans la base de données)
    public Vehicule(String marque, String modele, int anneeFabrication) {
        this.marque = marque;
        this.modele = modele;
        this.anneeFabrication = anneeFabrication;
        
    }

    // Constructeur avec tous les paramètres (y compris l'ID)
    public Vehicule(int id, String marque, String modele, int anneeFabrication) {
        this.id = id;
        this.marque = marque;
        this.modele = modele;
        this.anneeFabrication = anneeFabrication;
        
    }

    // Getter et setter pour l'ID
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter et setter pour la marque
    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    // Getter et setter pour le modèle
    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    // Getter et setter pour l'année de fabrication
    public int getAnneeFabrication() {
        return anneeFabrication;
    }

    public void setAnneeFabrication(int anneeFabrication) {
        this.anneeFabrication = anneeFabrication;
    }

    
}

