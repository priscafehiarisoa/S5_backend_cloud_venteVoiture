package gestionVehicules.repository;

import gestionVehicules.model.vehicule.Carburant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CarburantRepository extends JpaRepository<Carburant,String> {
    @Query(value = "select nextval('carburant_seq') ",nativeQuery = true)
    int getNextval();
    @Query(value = "select * from getsequence (:length , :prefix , :sequence)", nativeQuery = true)
    String getSequence(@Param("length") int length, @Param("prefix") String prefix, @Param("sequence") int sequence);

}
