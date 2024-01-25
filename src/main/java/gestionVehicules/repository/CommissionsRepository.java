package gestionVehicules.repository;

import gestionVehicules.model.annonce.Annonce;
import gestionVehicules.model.transactionsBanquaires.Commissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CommissionsRepository extends JpaRepository<Commissions, String> {
    @Query("select coalesce(sum (c.montantCommission),0 ) as montantCommission from Commissions c")
    double getSumCommsiision();

    Optional<Commissions> getCommissionsByAnnonce(Annonce annonce);
}