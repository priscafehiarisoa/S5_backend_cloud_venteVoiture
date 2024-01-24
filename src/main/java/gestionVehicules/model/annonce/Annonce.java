package gestionVehicules.model.annonce;

import gestionVehicules.model.user.Utilisateur;
import gestionVehicules.model.vehicule.Carburant;
import gestionVehicules.model.vehicule.Vehicule;
import jakarta.persistence.*;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@ToString
public class Annonce {
    @Id
    @Column(name = "id_annonce", nullable = false)
    private String id_annonce;

    @ManyToOne
    @JoinColumn(name = "id_utilisateur")
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "id_vehicule")
    private Vehicule vehicule;

    private String Description;

    private LocalDateTime date_annonce;

    private double prix;

    private int etat;

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Vehicule getVehicule() {
        return vehicule;
    }

    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public LocalDateTime getDate_annonce() {
        return date_annonce;
    }

    public void setDate_annonce(LocalDateTime date_annonce) {
        this.date_annonce = date_annonce;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) throws Exception {
        if(prix>=0) {
            this.prix = prix;
        }
        else {
            throw new Exception("le prix ne peut pas etre nul ou negatif");
        }
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public String getId_annonce() {
        return id_annonce;
    }

    public void setId_annonce(String id_annonce) {
        this.id_annonce = id_annonce;
    }
}
