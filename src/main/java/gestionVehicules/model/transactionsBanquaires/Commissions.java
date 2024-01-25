package gestionVehicules.model.transactionsBanquaires;

import gestionVehicules.model.annonce.Annonce;
import gestionVehicules.model.user.Utilisateur;
import gestionVehicules.model.vehicule.Modele;
import gestionVehicules.repository.sequence.SequenceRepository;
import jakarta.persistence.*;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@SequenceGenerator(name = "commission_seq_g", sequenceName = "commission_seq", allocationSize = 1)
@ToString
public class Commissions {
    @Id
    @Column(name = "id", nullable = false)
    private String id;
    private double montantCommission;
    @ManyToOne
    @JoinColumn(name = "annonce_id_annonce")
    private Annonce annonce;
    private LocalDateTime dateCommsission;


    public double getMontantCommission() {

        return montantCommission;
    }

    public void setMontantCommission(double montantCommission) throws Exception {
        if(montantCommission<0){
            throw new Exception("le montant d'une commission ne peut être negative");
        }
        this.montantCommission = montantCommission;
    }

    public LocalDateTime getDateCommsission() {
        return dateCommsission;
    }

    public void setDateCommsission(LocalDateTime dateCommsission) {
        this.dateCommsission = dateCommsission;
    }
    public void setDateCommsission() {
        setDateCommsission(LocalDateTime.now());
    }

    public Annonce getAnnonce() {
        return annonce;
    }

    public void setAnnonce(Annonce annonce) {
        this.annonce = annonce;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getPrefixes(){
        return "COMM";
    }
    public String getSequenceName(){
        return "commission_seq";
    }
    public String getId(SequenceRepository sequenceRepository){
        return sequenceRepository.getSequence(3,getPrefixes(),getSequenceName());
    }

    public Commissions(String id, double montantCommission, Annonce annonce, LocalDateTime dateCommsission) throws Exception {
        setId(id);
        setMontantCommission(montantCommission);
        setAnnonce(annonce);
        setDateCommsission(dateCommsission);
    } public Commissions(double montantCommission, Annonce annonce, LocalDateTime dateCommsission,SequenceRepository sequenceRepository) throws Exception {
        setId(getId(sequenceRepository));
        setMontantCommission(montantCommission);
        setAnnonce(annonce);
        setDateCommsission(dateCommsission);
    }public Commissions(double montantCommission, Annonce annonce,SequenceRepository sequenceRepository) throws Exception {
        setId(getId(sequenceRepository));
        setMontantCommission(montantCommission);
        setAnnonce(annonce);
        setDateCommsission();
    }

    public Commissions() {
    }
}
