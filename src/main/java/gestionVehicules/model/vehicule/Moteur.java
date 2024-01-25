package gestionVehicules.model.vehicule;

import gestionVehicules.repository.MoteurRepository;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.ToString;

import java.util.Optional;
import jakarta.persistence.*;

@Entity
@ToString

@SequenceGenerator(name = "moteur_seq_g", sequenceName = "moteur_seq", allocationSize = 1)

public class Moteur {
    @Id
    @Column(name = "id_moteur", nullable = false)

    private String id_moteur;
    private String nom_moteur;

    private double puissance;
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

    public double getPuissance() {
        return puissance;
    }

    public void setPuissance(double puissance) throws Exception {
        if (puissance>0){
            this.puissance = puissance;

        }
        else {
            throw new Exception("la puissance ne peut pas etre negative");
        }
    }

    public Moteur() {

    }

    public String getNom_moteur() {
        return nom_moteur;
    }

    public void setNom_moteur(String nom_moteur) throws Exception {
        if (!nom_moteur.isEmpty()) {
            this.nom_moteur = nom_moteur;
        }
        else {
            throw new Exception("le champ ne peut pas etre vide");
        }
    }

    public String getId_moteur() {
        return id_moteur;
    }

    public void setId_moteur(String id_moteur) {
        this.id_moteur = id_moteur;
    }

    public Moteur(String id_moteur, String nom_moteur,double puissance) throws Exception {
        this.setId_moteur(id_moteur);
        this.setNom_moteur(nom_moteur);
        this.setPuissance(puissance);
        this.setEtat();
    }

    public Moteur(String nom_moteur,double puissance) throws Exception {
        this.setNom_moteur(nom_moteur);
        this.setPuissance(puissance);
        this.setEtat();

    }
    public static String getSequenceName(){
        return "moteur_seq";
    }
    public Moteur getMoteurById(String id, MoteurRepository moteurRepository) throws Exception {
        Optional<Moteur> optional=moteurRepository.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }
        throw new Exception("le type de Moteur avec un id : "+id+" n'existe pas");
    }
}
