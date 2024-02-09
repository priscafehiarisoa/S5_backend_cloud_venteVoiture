package gestionVehicules.model.annonce;

import gestionVehicules.model.user.Utilisateur;
import gestionVehicules.repository.sequence.SequenceRepository;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class VenteAnnonce {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    private LocalDateTime dateVente;
    @Getter
    @ManyToOne
    @JoinColumn(name = "annonce_id_annonce")
    private Annonce annonce;

    @ManyToOne
    @JoinColumn(name = "acheteur_id_user")
    private Utilisateur acheteur;

    public String getPrefixes(){
        return "VEN";
    }
    public String getSequenceName(){
        return "vente_seq";
    }
    public String getId(SequenceRepository sequenceRepository){
        return sequenceRepository.getSequence(3,getPrefixes(),getSequenceName());
    }

    public VenteAnnonce(Annonce annonce, Utilisateur acheteur,SequenceRepository sequenceRepository) {
        setAnnonce(annonce);
        setAcheteur(acheteur);
        setDateVente(LocalDateTime.now());
        setId(getId(sequenceRepository));
    }
}
