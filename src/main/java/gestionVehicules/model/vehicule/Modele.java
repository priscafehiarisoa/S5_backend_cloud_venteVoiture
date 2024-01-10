package gestionVehicules.model.vehicule;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Modele {
    @Id
    @Column(name = "id_modele", nullable = false)
    private String id_modele;

    private String nom_modele;

    public Modele() {

    }

    public String getNom_modele() {
        return nom_modele;
    }

    public void setNom_modele(String nom_modele) {
        this.nom_modele = nom_modele;
    }

    public String getId_modele() {
        return id_modele;
    }

    public void setId_modele(String id_modele) {
        this.id_modele = id_modele;
    }

    public Modele(String id_modele, String nom_modele) {
        this.setId_modele(id_modele);
        this.setNom_modele(nom_modele);
    }

    public Modele(String nom_modele) {
        this.setNom_modele(nom_modele);
    }
}
