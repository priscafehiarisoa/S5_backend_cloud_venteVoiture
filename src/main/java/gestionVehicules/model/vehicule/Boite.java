package gestionVehicules.model.vehicule;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Boite {
    @Id
    @Column(name = "id_boite", nullable = false)
    private String id_boite;
    private String nom_boite;

    public Boite() {

    }

    public String getNom_boite() {
        return nom_boite;
    }

    public void setNom_boite(String nom_boite) {
        this.nom_boite = nom_boite;
    }

    public String getId_boite() {
        return id_boite;
    }

    public void setId_boite(String id_boite) {
        this.id_boite = id_boite;
    }

    public Boite(String id_boite, String nom_boite) {
        this.setId_boite(id_boite);
        this.setNom_boite(nom_boite);
    }

    public Boite(String nom_boite) {
        this.setNom_boite(nom_boite);
    }
}
