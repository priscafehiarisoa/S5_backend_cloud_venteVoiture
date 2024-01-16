package gestionVehicules.model.vehicule;

import jakarta.persistence.*;

@Entity
@SequenceGenerator(name = "marque_seq_g", sequenceName = "marque_seq", allocationSize = 1)

public class Marque {
    @Id
    @Column(name = "id_marque", nullable = false)

    private String id_marque;
    private String nom_marque;

    public Marque() {

    }

    public String getNom_marque() {
        return nom_marque;
    }

    public void setNom_marque(String nom_marque) {
        this.nom_marque = nom_marque;
    }

    public String getId_marque() {
        return id_marque;
    }

    public void setId_marque(String id_marque) {
        this.id_marque = id_marque;
    }

    public Marque(String id_marque, String nom_marque) {
        this.setId_marque(id_marque);
        this.setNom_marque(nom_marque);
    }

    public Marque(String nom_marque) {
        this.setNom_marque(nom_marque);
    }
    public static String getSequenceName(){
        return "marque_seq";
    }
}
