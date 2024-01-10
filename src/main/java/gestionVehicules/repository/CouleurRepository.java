package gestionVehicules.repository;

import gestionVehicules.model.vehicule.Couleur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouleurRepository extends JpaRepository<Couleur,String> {
}
