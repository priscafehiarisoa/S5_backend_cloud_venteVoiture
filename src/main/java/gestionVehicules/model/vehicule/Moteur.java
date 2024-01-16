package gestionVehicules.model.vehicule;

import jakarta.persistence.*;

@Entity
@SequenceGenerator(name = "moteur_seq_g", sequenceName = "moteur_seq", allocationSize = 1)

public class Moteur {
    @Id
    @Column(name = "id_moteur", nullable = false)

    private String id_moteur;
    private String nom_moteur;

    private double puissance;

    public double getPuissance() {
        return puissance;
    }

    public void setPuissance(double puissance) {
        this.puissance = puissance;
    }

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

    public Moteur(String id_moteur, String nom_moteur,double puissance) {
        this.setId_moteur(id_moteur);
        this.setNom_moteur(nom_moteur);
        this.setPuissance(puissance);
    }

    public Moteur(String nom_moteur,double puissance) {
        this.setNom_moteur(nom_moteur);
        this.setPuissance(puissance);

    }
    public static String getSequenceName(){
        return "moteur_seq";
    }
}
