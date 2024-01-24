package gestionVehicules.model.vehicule;

import jakarta.persistence.*;

@Entity
@SequenceGenerator(name = "modele_seq_g", sequenceName = "modele_seq", allocationSize = 1)

public class Modele {
    @Id
    @Column(name = "id_modele", nullable = false)

    private String id_modele;

    private String nom_modele;
    @ManyToOne
    @JoinColumn(name = "id_marque")
    private Marque marque;

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

    public Marque getMarque() {
        return marque;
    }

    public void setMarque(Marque marque) {
        this.marque = marque;
    }

    public Modele() {

    }

    public String getNom_modele() {
        return nom_modele;
    }

    public void setNom_modele(String nom_modele) throws Exception {
        if (!nom_modele.isEmpty()) {
            this.nom_modele = nom_modele;
        }
        else {
            throw new Exception("le champ ne peut pas etre vide");
        }
    }

    public String getId_modele() {
        return id_modele;
    }

    public void setId_modele(String id_modele) {
        this.id_modele = id_modele;
    }

    public Modele(String id_modele, String nom_modele,Marque marque) throws Exception {
        this.setId_modele(id_modele);
        this.setNom_modele(nom_modele);
        this.setMarque(marque);
        this.setEtat();
    }

    public Modele(String nom_modele,Marque marque) throws Exception {
        this.setNom_modele(nom_modele);
        this.setMarque(marque);
        this.setEtat();

    }
    public static String getSequenceName(){
        return "modele_seq";
    }
}
