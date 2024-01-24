package gestionVehicules.repository;

import gestionVehicules.model.vehicule.Couleur;
import gestionVehicules.model.vehicule.Marque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MarqueRepository extends JpaRepository<Marque,String> {
    @Query(value = "select nextval('marque_seq') ",nativeQuery = true)
    int getNextval();
    @Query(value = "select * from getsequence (:length , :prefix , :sequence)", nativeQuery = true)
    String getSequence(@Param("length") int length, @Param("prefix") String prefix, @Param("sequence") int sequence);

    @Query(value = "select c from Marque c where c.etat=0")
    List<Marque> marqueDispo();

    @Transactional
    @Modifying
    @Query("update Marque c set c.etat = -1 where c.id_marque = :id")
    void updateMarqueEtat(@Param("id") String id);
}
