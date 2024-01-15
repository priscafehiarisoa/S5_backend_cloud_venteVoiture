package gestionVehicules.repository.annonce;

import gestionVehicules.model.annonce.Annonce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnnonceRepository extends JpaRepository<Annonce,String> {
    @Query(value = "select nextval('annonce_seq') ",nativeQuery = true)
    int getNextval();
    @Query(value = "select * from getsequence (:length , :prefix , :sequence)", nativeQuery = true)
    String getSequence(@Param("length") int length, @Param("prefix") String prefix, @Param("sequence") int sequence);

    @Query(value = "select a from Annonce a where a.etat=0")
    List<Annonce> getAnnoncesEnCoursDeValidation();

    @Modifying
    @Query(value = "update  Annonce a set a.etat=10 where a.id_annonce=:annonce ")
    void validerAnnonce(@Param("annonce")String id_annonce);

    @Query(value = "select a from Annonce a where a.etat=10")
    List<Annonce> getAnnoncesValidees();

    @Modifying
    @Query(value = "update  Annonce a set a.etat=20 where a.id_annonce=:annonce ")
    void vendreAnnonce(@Param("annonce")String id_annonce);

    @Query(value = "select a from Annonce a where a.etat=20")
    List<Annonce> getAnnoncesVendues();

    @Modifying
    @Query(value = "update  Annonce a set a.etat= -10 where a.id_annonce=:annonce ")
    void refuserAnnonce(@Param("annonce")String id_annonce);

    @Query(value = "select a from Annonce a where a.etat=-10")
    List<Annonce> getAnnoncesRefusees();






}
