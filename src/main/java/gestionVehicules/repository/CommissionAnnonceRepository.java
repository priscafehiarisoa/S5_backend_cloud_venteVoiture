package gestionVehicules.repository;

import gestionVehicules.model.annonce.Annonce;
import gestionVehicules.model.annonce.CommissionAnnonce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommissionAnnonceRepository extends JpaRepository<CommissionAnnonce, String> {

    @Query("select ca from CommissionAnnonce ca where :prixAnnonce between ca.debutMargePrixAnnonce and ca.finMargePrixAnnonce")
    CommissionAnnonce getCommissionAnnonceByPrixAnnonce(@Param("prixAnnonce") double annonce);
}