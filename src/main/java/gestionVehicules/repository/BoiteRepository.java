package gestionVehicules.repository;

import gestionVehicules.model.vehicule.Boite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoiteRepository extends JpaRepository<Boite,String> {
}
