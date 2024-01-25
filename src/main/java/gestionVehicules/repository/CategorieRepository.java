package gestionVehicules.repository;

import gestionVehicules.model.vehicule.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CategorieRepository extends JpaRepository<Categorie,String> {

    @Query(value = "select nextval('categorie_seq') ", nativeQuery = true)
    int getNextval();

    @Query(value = "select * from getsequence (:length , :prefix , :sequence)", nativeQuery = true)
    String getSequence(@Param("length") int length, @Param("prefix") String prefix, @Param("sequence") int sequence);

    @Query(value = "select c from Categorie c where c.etat=0")
    List<Categorie> categorieDispo();

    @Transactional
    @Modifying
    @Query("update Categorie c set c.etat = -1 where c.id_categorie = :categoryId")
    void updateCategorieEtat(@Param("categoryId") String categoryId);

}
