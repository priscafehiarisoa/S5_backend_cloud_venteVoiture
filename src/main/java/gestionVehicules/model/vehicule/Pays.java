package gestionVehicules.model.vehicule;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Pays {
    @Id
    @Column(name = "id_pays", nullable = false)
    private String id_pays;
    private String nom_pays;

    public Pays() {

    }

    public String getNom_pays() {
        return nom_pays;
    }

    public void setNom_pays(String nom_pays) {
        this.nom_pays = nom_pays;
    }

    public String getId_pays() {
        return id_pays;
    }

    public void setId_pays(String id_pays) {
        this.id_pays = id_pays;
    }

    public Pays(String id_pays, String nom_pays) {
        this.setId_pays(id_pays);
        this.setNom_pays(nom_pays);
    }

    public Pays(String nom_pays) {
        this.setNom_pays(nom_pays);
    }

}
