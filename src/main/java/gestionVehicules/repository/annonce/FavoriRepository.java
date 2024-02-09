package gestionVehicules.repository.annonce;

import gestionVehicules.model.annonce.Annonce;
import gestionVehicules.model.annonce.Favori;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriRepository extends JpaRepository<Favori,String> {

    @Transactional
    @Modifying
    @Query("update Favori c set c.etat = -1 where c.id_favori = :id")
    void updateFavoriEtat(@Param("id") String id);


    @Query("SELECT f.annonce FROM Favori f WHERE f.etat = 0 AND f.utilisateur.id_user = :idutilisateur")
    List<Annonce> getFavoriDispoParUtilisateur(@Param("idutilisateur") String idutilisateur);


    @Query("SELECT COUNT(c) FROM Favori c WHERE c.etat = 0 AND c.id_favori = :idfavori")
    double nombreFavori(@Param("idfavori") String idfavori);

    Optional<Favori> getFavoriByAnnonce(Annonce annonce);

    int countFavoriByAnnonce(Annonce annonce);


}
