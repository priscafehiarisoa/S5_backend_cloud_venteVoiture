package gestionVehicules.repository.sequence;

import gestionVehicules.model.vehicule.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SequenceRepository extends JpaRepository<Vehicule, Long> {
    @Query(value = "SELECT getsequence(:length, :prefix, :sequence)", nativeQuery = true)
    String getSequence(
            @Param("length") int length,
            @Param("prefix") String prefix,
            @Param("sequence") String sequence
    );
}
