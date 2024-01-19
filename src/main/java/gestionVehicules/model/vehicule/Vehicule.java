package gestionVehicules.model.vehicule;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@SequenceGenerator(name = "vehicule_seq_g", sequenceName = "vehicule_seq", allocationSize = 1)
public class Vehicule {
    @Id
    @Column(name = "id_vehicule", nullable = false)
    private String id_vehicule;
    private String immatricule;
    private int annee_fabrication;
    private double kilometrage_vehicule;
    private int nombre_sieges;
    private double masse_vehicule;

    public String getId_vehicule() {
        return id_vehicule;
    }

    public void setId_vehicule(String id_vehicule) {
        this.id_vehicule = id_vehicule;
    }

    @ManyToOne
    @JoinColumn(name = "id_boite")
    private Boite boite;

    @ManyToOne
    @JoinColumn(name = "id_carburant")
    private Carburant carburant;

    @ManyToOne
    @JoinColumn(name = "id_categorie")
    private Categorie categorie;

    @ManyToOne
    @JoinColumn(name = "id_couleur")
    private Couleur couleur;


    @ManyToOne
    @JoinColumn(name = "id_modele")
    private Modele modele;

    @ManyToOne
    @JoinColumn(name = "id_moteur")
    private Moteur moteur;

    @ManyToOne
    @JoinColumn(name = "id_pays")
    private Pays pays;

    public Pays getPays() {
        return pays;
    }

    public void setPays(Pays pays) {
        this.pays = pays;
    }

    public Vehicule() {

    }

    public String getImmatricule() {
        return immatricule;
    }

    public void setImmatricule(String immatricule) throws Exception {
        if (!immatricule.isEmpty()) {
            this.immatricule = immatricule;
        }
        else {
            throw new Exception("le champ ne peut pas etre vide");
        }
    }

    public int getAnnee_fabrication() {
        return annee_fabrication;
    }

    public void setAnnee_fabrication(int annee_fabrication) throws Exception {
        if(annee_fabrication>=1700 & annee_fabrication<= LocalDateTime.now().getYear()) {
            this.annee_fabrication = annee_fabrication;
        }
        else {
            throw new Exception("annee de fabrication invalide");
        }
    }

    public double getKilometrage_vehicule() {
        return kilometrage_vehicule;
    }

    public void setKilometrage_vehicule(double kilometrage_vehicule) throws Exception {
        if (kilometrage_vehicule>=0) {
            this.kilometrage_vehicule = kilometrage_vehicule;
        }
        else {
            throw new Exception("kilometrage invalide, ne peut pas etre negatif");
        }
    }

    public int getNombre_sieges() {
        return nombre_sieges;
    }

    public void setNombre_sieges(int nombre_sieges) throws Exception {
        if(nombre_sieges>0) {
            this.nombre_sieges = nombre_sieges;
        }
        else {
            throw new Exception("nombre de sieges invalide,ne peut pas etre moins de 1");
        }
    }

    public double getMasse_vehicule() {
        return masse_vehicule;
    }

    public void setMasse_vehicule(double masse_vehicule) throws Exception {
        if(masse_vehicule>0) {
            this.masse_vehicule = masse_vehicule;
        }
        else {
            throw new Exception("masse invalide, ne peut pas etre negative ou nulle");
        }
    }

    public Boite getBoite() {
        return boite;
    }

    public void setBoite(Boite boite) {
        this.boite = boite;
    }

    public Carburant getCarburant() {
        return carburant;
    }

    public void setCarburant(Carburant carburant) {
        this.carburant = carburant;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Couleur getCouleur() {
        return couleur;
    }

    public void setCouleur(Couleur couleur) {
        this.couleur = couleur;
    }


    public Modele getModele() {
        return modele;
    }

    public void setModele(Modele modele) {
        this.modele = modele;
    }

    public Moteur getMoteur() {
        return moteur;
    }

    public void setMoteur(Moteur moteur) {
        this.moteur = moteur;
    }

    public Vehicule(String id_vehicule, String immatricule, int annee_fabrication, double kilometrage_vehicule, int nombre_sieges, double masse_vehicule, Boite boite, Carburant carburant, Categorie categorie, Couleur couleur,Modele modele, Moteur moteur, Pays pays) throws Exception {
        this.setId_vehicule(id_vehicule);
        this.setImmatricule(immatricule);
        this.setAnnee_fabrication(annee_fabrication);
        this.setKilometrage_vehicule(kilometrage_vehicule);
        this.setNombre_sieges(nombre_sieges);
        this.setMasse_vehicule(masse_vehicule);
        this.setBoite(boite);
        this.setCarburant(carburant);
        this.setCategorie(categorie);
        this.setCouleur(couleur);
        this.setModele(modele);
        this.setMoteur(moteur);
        this.setPays(pays);
    }

    public Vehicule(String immatricule, int annee_fabrication, double kilometrage_vehicule, int nombre_sieges, double masse_vehicule, Boite boite, Carburant carburant, Categorie categorie, Couleur couleur, Modele modele, Moteur moteur, Pays pays) throws Exception {
        this.setImmatricule(immatricule);
        this.setAnnee_fabrication(annee_fabrication);
        this.setKilometrage_vehicule(kilometrage_vehicule);
        this.setNombre_sieges(nombre_sieges);
        this.setMasse_vehicule(masse_vehicule);
        this.setBoite(boite);
        this.setCarburant(carburant);
        this.setCategorie(categorie);
        this.setCouleur(couleur);
        this.setModele(modele);
        this.setMoteur(moteur);
        this.setPays(pays);
    }
    public static String getSequenceName(){
        return "vehicule_seq";
    }
}
