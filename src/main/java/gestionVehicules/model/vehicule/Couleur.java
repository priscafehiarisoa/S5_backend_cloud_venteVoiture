package gestionVehicules.model.vehicule;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Couleur {
    @Id
    @Column(name = "id_couleur", nullable = false)
    private String id_couleur;

    private String nom_couleur;

    public Couleur() {

    }

    public String getNom_couleur() {
        return nom_couleur;
    }

    public void setNom_couleur(String nom_couleur) {
        this.nom_couleur = nom_couleur;
    }

    public String getId_couleur() {
        return id_couleur;
    }

    public void setId_couleur(String id_couleur) {
        this.id_couleur = id_couleur;
    }

    public Couleur(String id_couleur, String nom_couleur) {
        this.setId_couleur(id_couleur);
        this.setNom_couleur(nom_couleur);
    }

    public Couleur(String nom_couleur) {
        this.setNom_couleur(nom_couleur);
    }

}
