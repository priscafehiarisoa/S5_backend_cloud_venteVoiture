package gestionVehicules.model.transactionsBanquaires;

import gestionVehicules.model.annonce.Annonce;
import gestionVehicules.model.user.Utilisateur;
import gestionVehicules.repository.sequence.SequenceRepository;
import jakarta.persistence.*;
import lombok.ToString;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;

@Entity
@SequenceGenerator(name = "transactions_seq_g", sequenceName = "transactions_seq", allocationSize = 1)
@ToString
public class Transactions {
    @Id
    @Column(name = "id", nullable = false)
    private String id;
    @ManyToOne
    @JoinColumn(name = "utilisateur_id_user")
    private Utilisateur utilisateur;
    @ManyToOne
    @JoinColumn(name = "annonce_id_annonce")
    private Annonce annonce;
    private String descriptifTransaction;
    private double montantTransaction;
    private LocalDateTime dateTransaction;
    private int multiplicateur;

    public int getMultiplicateur() {
        return multiplicateur;
    }

    public void setMultiplicateur(int multiplicateur) {
        this.multiplicateur = multiplicateur;
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

    public String getDescriptifTransaction() {
        return descriptifTransaction;
    }

    public void setDescriptifTransaction(String descriptifTransaction) {
        this.descriptifTransaction = descriptifTransaction;
    }

    public double getMontantTransaction() {
        return montantTransaction;
    }

    public void setMontantTransaction(double montantTransaction) {
        this.montantTransaction = montantTransaction;
    }

    public LocalDateTime getDateTransaction() {
        return dateTransaction;
    }

    public void setDateTransaction(LocalDateTime dateTransaction) {
        this.dateTransaction = dateTransaction;
    }

    public Transactions() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getPrefixes(){
        return "TRANS";
    }
    public String getSequenceName(){
        return "transactions_seq";
    }
    public String getId(SequenceRepository sequenceRepository){
        return sequenceRepository.getSequence(3,getPrefixes(),getSequenceName());
    }

    public Transactions(String id, Utilisateur utilisateur, Annonce annonce, String descriptifTransaction,int multiplicateur, double montantTransaction, LocalDateTime dateTransaction) {
        setId(id);
        setUtilisateur(utilisateur);
        setAnnonce(annonce);
        setDescriptifTransaction(descriptifTransaction);
        setMontantTransaction(montantTransaction);
        setDateTransaction(dateTransaction);
        setMultiplicateur(multiplicateur);
    }

    public Transactions(Utilisateur utilisateur, Annonce annonce, int multiplicateur,String descriptifTransaction, double montantTransaction,SequenceRepository sequenceRepository) {
        setId(getId(sequenceRepository));
        setUtilisateur(utilisateur);
        setAnnonce(annonce);
        setDescriptifTransaction(descriptifTransaction);
        setMontantTransaction(montantTransaction);
        setDateTransaction(LocalDateTime.now());
        setMultiplicateur(multiplicateur);
    }

    public HashMap<String,Object> getFormatedTRansaction(){
        HashMap<String,Object> returnHashmap=new HashMap<>();
        returnHashmap.put("id",getId());
        returnHashmap.put("annonce",getAnnonce().getAnnoncemodifie());
        returnHashmap.put("dateTransaction",getDateTransaction());
        returnHashmap.put("montant",getMontantTransaction()*getMultiplicateur());
        returnHashmap.put("description",getDescriptifTransaction());
        returnHashmap.put("utilisateur",getAnnonce().getAnnoncemodifie());
        return returnHashmap;
    }




}
