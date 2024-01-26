package gestionVehicules.repository.annonce;

import gestionVehicules.model.annonce.Annonce;
import gestionVehicules.model.annonce.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, String> {

    List<Image>getImageByAnnonce(Annonce annonce);
}