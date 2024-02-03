package gestionVehicules.model.annonce;

import gestionVehicules.model.user.Utilisateur;
import gestionVehicules.model.utils.Utils;
import gestionVehicules.model.vehicule.Carburant;
import gestionVehicules.model.vehicule.Vehicule;
import gestionVehicules.repository.annonce.AnnonceRepository;
import gestionVehicules.repository.sequence.SequenceRepository;
import jakarta.persistence.*;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;

@Entity
@ToString
@SequenceGenerator(name = "annonce_seq_g", sequenceName = "annonce_seq", allocationSize = 1)

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
    private double commission=0;
    @Transient
    private boolean isInFavorites=true;

    public Annonce() {

    }

    public boolean isInFavorites() {
        return isInFavorites;
    }


    public void setInFavorites(boolean inFavorites) {
        isInFavorites = inFavorites;
    }

    public double getTotalCommission() {
        return (getPrix()*getCommission())/100;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

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
    public void setDate_annonce(String date_annonce) {
        setDate_annonce(Utils.convertToDateTime(date_annonce));
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

     public HashMap<String,Object> getAnnoncemodifie(){
         HashMap<String,Object> annonce=new HashMap<>();
         annonce.put("vehicule",this.getVehicule());
         annonce.put("description",this.getDescription());
         annonce.put("prix",this.getPrix());
         annonce.put("etat",this.getEtat());
         annonce.put("inFavorites",this.isInFavorites());
         annonce.put("prixVehiculeAvecCommission",this.getPrixVehiculeAvecCommission());
         annonce.put("utilisateur",this.getUtilisateur().getNometPrenomIdUtilisateur());
         annonce.put("date_annonce",this.getDate_annonce());
         return annonce;

     }

    public static Annonce getAnnonceById(String id, AnnonceRepository annonceRepository) throws Exception {
        Optional<Annonce> annonce=annonceRepository.findById(id);
        if(annonce.isPresent()){
            return annonce.get();
        }
        throw new Exception("annonce Introuvable");
    }

    public String getPrefixes(){
        return "ANN";
    }
    public String getSequenceName(){
        return "annonce_seq";
    }
    public String getId(SequenceRepository sequenceRepository){
        return sequenceRepository.getSequence(3,getPrefixes(),getSequenceName());
    }

    public double getPrixVehiculeAvecCommission(){
        double commission=(getCommission()*getPrix())/100;
        return getPrix()+commission;
    }

    public Annonce(String id_annonce, Utilisateur utilisateur, Vehicule vehicule, String description, LocalDateTime date_annonce, double prix, int etat, double commission) throws Exception {
        this.setId_annonce(id_annonce);
        this.setUtilisateur(utilisateur);
        this.setVehicule(vehicule);
        this.setDescription(description);
        this.setDate_annonce(date_annonce);
        this.setPrix(prix);
        this.setEtat(etat);
        this.setCommission(commission);
    }

    public Annonce(Utilisateur utilisateur, Vehicule vehicule, String description, LocalDateTime date_annonce, double prix, int etat, double commission) throws Exception {
        this.setUtilisateur(utilisateur);
        this.setVehicule(vehicule);
        this.setDescription(description);
        this.setDate_annonce(date_annonce);
        this.setPrix(prix);
        this.setEtat(etat);
        this.setCommission(commission);
    }
}
