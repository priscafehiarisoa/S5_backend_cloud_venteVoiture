package gestionVehicules.model.vehicule;

import jakarta.persistence.*;

@Entity
@SequenceGenerator(name = "pays_seq_g", sequenceName = "pays_seq", allocationSize = 1)
public class Pays {
    @Id
    @Column(name = "id_pays", nullable = false)

    private String id_pays;
    private String nom_pays;
    private int etat;

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }
    public void setEtat() {
        this.etat = 1;
    }

    public Pays() {

    }

    public String getNom_pays() {
        return nom_pays;
    }

    public void setNom_pays(String nom_pays) throws Exception {
        if (!nom_pays.isEmpty()) {
            this.nom_pays = nom_pays;
        }
        else {
            throw new Exception("le champ ne peut pas etre vide");
        }
    }

    public String getId_pays() {
        return id_pays;
    }

    public void setId_pays(String id_pays) {
        this.id_pays = id_pays;
    }

    public Pays(String id_pays, String nom_pays) throws Exception {
        this.setId_pays(id_pays);
        this.setNom_pays(nom_pays);
        this.setEtat();
    }

    public Pays(String nom_pays) throws Exception {
        this.setNom_pays(nom_pays);
        this.setEtat();
    }

    public static String getSequenceName(){
        return "pays_seq";
    }

}
