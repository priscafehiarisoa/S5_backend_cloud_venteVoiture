package gestionVehicules.repository;

import gestionVehicules.model.vehicule.Marque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarqueRepository extends JpaRepository<Marque,String> {
}
