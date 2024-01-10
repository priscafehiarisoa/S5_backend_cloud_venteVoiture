package gestionVehicules.model.vehicule;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Moteur {
    @Id
    @Column(name = "id_moteur", nullable = false)
    private String id_moteur;
    private String nom_moteur;

    public Moteur() {

    }

    public String getNom_moteur() {
        return nom_moteur;
    }

    public void setNom_moteur(String nom_moteur) {
        this.nom_moteur = nom_moteur;
    }

    public String getId_moteur() {
        return id_moteur;
    }

    public void setId_moteur(String id_moteur) {
        this.id_moteur = id_moteur;
    }

    public Moteur(String id_moteur, String nom_moteur) {
        this.setId_moteur(id_moteur);
        this.setNom_moteur(nom_moteur);
    }

    public Moteur(String nom_moteur) {
        this.setNom_moteur(nom_moteur);
    }
}
