package gestionVehicules.repository;

import gestionVehicules.model.vehicule.Couleur;
import gestionVehicules.model.vehicule.Moteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MoteurRepository extends JpaRepository<Moteur,String> {
    @Query(value = "select nextval('moteur_seq') ",nativeQuery = true)
    int getNextval();
    @Query(value = "select * from getsequence (:length , :prefix , :sequence)", nativeQuery = true)
    String getSequence(@Param("length") int length, @Param("prefix") String prefix, @Param("sequence") String sequence);

    @Query(value = "select c from Moteur c where c.etat=0")
    List<Moteur> moteurDispo();

    @Transactional
    @Modifying
    @Query("update Moteur c set c.etat = -1 where c.id_moteur = :id")
    void updateMoteurEtat(@Param("id") String id);
}
