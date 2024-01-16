package gestionVehicules.model.vehicule;

import jakarta.persistence.*;

@Entity
@SequenceGenerator(name = "pays_seq_g", sequenceName = "pays_seq", allocationSize = 1)
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

    public static String getSequenceName(){
        return "pays_seq";
    }

}
