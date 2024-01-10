package gestionVehicules.repository;

import gestionVehicules.model.vehicule.Moteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoteurRepository extends JpaRepository<Moteur,String> {
}
