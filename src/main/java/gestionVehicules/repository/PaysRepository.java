package gestionVehicules.repository;

import gestionVehicules.model.vehicule.Couleur;
import gestionVehicules.model.vehicule.Pays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PaysRepository extends JpaRepository<Pays,String> {
    @Query(value = "select nextval('pays_seq') ",nativeQuery = true)
    int getNextval();
    @Query(value = "select * from getsequence (:length , :prefix , :sequence)", nativeQuery = true)
    String getSequence(@Param("length") int length, @Param("prefix") String prefix, @Param("sequence") int sequence);

    @Query(value = "select c from Pays c where c.etat=0")
    List<Pays> paysDispo();

    @Transactional
    @Modifying
    @Query("update Pays c set c.etat = -1 where c.id_pays = :id")
    void updatePaysEtat(@Param("id") String id);
}
