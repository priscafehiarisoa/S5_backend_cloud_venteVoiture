package gestionVehicules.model.vehicule;

import jakarta.persistence.*;

@Entity
@SequenceGenerator(name = "couleur_seq_g", sequenceName = "couleur_seq", allocationSize = 1)

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

    public void setNom_couleur(String nom_couleur) throws Exception {
        if(!nom_couleur.isEmpty()){
            this.nom_couleur = nom_couleur;

        }
          else {
            throw new Exception("le champ ne peut pas etre nul");
        }
    }

    public String getId_couleur() {
        return id_couleur;
    }

    public void setId_couleur(String id_couleur) {
        this.id_couleur = id_couleur;
    }

    public Couleur(String id_couleur, String nom_couleur) throws Exception {
        this.setId_couleur(id_couleur);
        this.setNom_couleur(nom_couleur);
    }

    public Couleur(String nom_couleur) throws Exception {
        this.setNom_couleur(nom_couleur);
    }

    public static String getSequenceName(){
        return "couleur_seq";
    }
}
