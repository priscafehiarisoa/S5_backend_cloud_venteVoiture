package gestionVehicules.repository;

import gestionVehicules.model.vehicule.Carburant;
import gestionVehicules.model.vehicule.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CarburantRepository extends JpaRepository<Carburant,String> {
    @Query(value = "select nextval('carburant_seq') ",nativeQuery = true)
    int getNextval();
    @Query(value = "select * from getsequence (:length , :prefix , :sequence)", nativeQuery = true)
    String getSequence(@Param("length") int length, @Param("prefix") String prefix, @Param("sequence") int sequence);

    @Query(value = "select c from Carburant c where c.etat=0")
    List<Carburant> carburantDispo();

    @Transactional
    @Modifying
    @Query("update Carburant c set c.etat = -1 where c.id_carburant = :carburantId")
    void updateCarburantEtat(@Param("carburantId") String carburantId);

}
