package gestionVehicules.model.annonce;

import gestionVehicules.repository.sequence.SequenceRepository;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.ToString;

@Entity
@SequenceGenerator(name = "commission_annonce_seq_g", sequenceName = "commission_annonce_seq", allocationSize = 1)
@ToString
public class CommissionAnnonce {
    @Id
    @Column(name = "id", nullable = false)
    private String id;
    private double debutMargePrixAnnonce;
    private double finMargePrixAnnonce;
    private double pourcentageCommission;

    public double getDebutMargePrixAnnonce() {
        return debutMargePrixAnnonce;
    }

    public void setDebutMargePrixAnnonce(double debutMargePrixAnnonce) {
        this.debutMargePrixAnnonce = debutMargePrixAnnonce;
    }

    public double getFinMargePrixAnnonce() {
        return finMargePrixAnnonce;
    }

    public void setFinMargePrixAnnonce(double finMargePrixAnnonce) {
        this.finMargePrixAnnonce = finMargePrixAnnonce;
    }

    public double getPourcentageCommission() {
        return pourcentageCommission;
    }

    public void setPourcentageCommission(double pourcentageCommission) {
        this.pourcentageCommission = pourcentageCommission;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrefixes(){
        return "COM_ANN";
    }
    public String getSequenceName(){
        return "commission_annonce_seq";
    }
    public String getId(SequenceRepository sequenceRepository){
        return sequenceRepository.getSequence(3,getPrefixes(),getSequenceName());
    }
}
