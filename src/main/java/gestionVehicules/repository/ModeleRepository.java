package gestionVehicules.repository;

import gestionVehicules.model.vehicule.Modele;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModeleRepository extends JpaRepository<Modele,String> {
}
