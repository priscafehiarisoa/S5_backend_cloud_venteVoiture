package gestionVehicules.repository;

import gestionVehicules.model.vehicule.Carburant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarburantRepository extends JpaRepository<Carburant,String> {
}
