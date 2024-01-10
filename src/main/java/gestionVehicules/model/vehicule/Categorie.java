package gestionVehicules.model.vehicule;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Categorie {
    @Id
    @Column(name = "id_categorie", nullable = false)
    private String id_categorie;
    private String nom_categorie;

    public Categorie() {

    }

    public String getNom_categorie() {
        return nom_categorie;
    }

    public void setNom_categorie(String nom_categorie) {
        this.nom_categorie = nom_categorie;
    }

    public String getId_categorie() {
        return id_categorie;
    }

    public void setId_categorie(String id_categorie) {
        this.id_categorie = id_categorie;
    }

    public Categorie(String id_categorie, String nom_categorie) {
        this.setId_categorie(id_categorie);
        this.setNom_categorie(nom_categorie);
    }

    public Categorie(String nom_categorie) {
        this.setNom_categorie(nom_categorie);
    }

}
