package gestionVehicules.repository;

import gestionVehicules.model.vehicule.Couleur;
import gestionVehicules.model.vehicule.Modele;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ModeleRepository extends JpaRepository<Modele,String> {
    @Query(value = "select nextval('modele_seq') ",nativeQuery = true)
    int getNextval();
    @Query(value = "select * from getsequence (:length , :prefix , :sequence)", nativeQuery = true)
    String getSequence(@Param("length") int length, @Param("prefix") String prefix, @Param("sequence") int sequence);

    @Query(value = "select c from Modele c where c.etat=0")
    List<Modele> modeleDispo();

    @Transactional
    @Modifying
    @Query("update Modele c set c.etat = -1 where c.id_modele = :id")
    void updateModeleEtat(@Param("id") String id);
}
