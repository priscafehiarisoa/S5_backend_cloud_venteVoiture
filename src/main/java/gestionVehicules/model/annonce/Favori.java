package gestionVehicules.model.annonce;

import gestionVehicules.model.user.Utilisateur;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Favori {
    @Id
    @Column(name = "id_favori", nullable = false)
    private String id_favori;

    @ManyToOne
    @JoinColumn(name = "id_utilisateur")
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name= "id_annonce")
    private Annonce annonce;

    private LocalDate dateAjout;

    int etat;

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }
    public void setEtat() {
        this.etat = 1;
    }

    public Favori() {

    }

    public String getId_favori() {
        return id_favori;
    }

    public void setId_favori(String id_favori) {
        this.id_favori = id_favori;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Annonce getAnnonce() {
        return annonce;
    }

    public void setAnnonce(Annonce annonce) {
        this.annonce = annonce;
    }

    public LocalDate getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(LocalDate dateAjout) {
        this.dateAjout = dateAjout;
    }

    public Favori(String id_favori, Utilisateur utilisateur, Annonce annonce, LocalDate dateAjout) {
        this.setId_favori(id_favori);
        this.setUtilisateur(utilisateur);
        this.setAnnonce(annonce);
        this.setDateAjout(dateAjout);
        this.setEtat();
    }

    public Favori(Utilisateur utilisateur, Annonce annonce, LocalDate dateAjout, int etat) {
        this.setUtilisateur(utilisateur);
        this.setAnnonce(annonce);
        this.setDateAjout(dateAjout);
        this.setEtat();
    }

    public Favori(String id_favori, Utilisateur utilisateur, Annonce annonce, LocalDate dateAjout,int etat) {
        this.setId_favori(id_favori);
        this.setUtilisateur(utilisateur);
        this.setAnnonce(annonce);
        this.setDateAjout(dateAjout);
        this.setEtat(etat);
    }
    public static String getSequenceName(){
        return "favori_seq";
    }

}
