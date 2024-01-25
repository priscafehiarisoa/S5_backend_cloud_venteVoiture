package gestionVehicules.repository.annonce;

import gestionVehicules.model.annonce.Annonce;
import gestionVehicules.model.vehicule.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
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


    // recherche annonce
    @Query("select a from Annonce a " +
            " where a.prix between :prix1 and :prix2 " +
            " AND a.date_annonce between :date1 and :date2" +
            " and a.vehicule.annee_fabrication between :annee1 and :annee2" +
            " AND a.vehicule.modele.marque in :marque " +
            " AND a.vehicule.modele in :modele " +
            " and a.vehicule.kilometrage_vehicule between :km1 and :km2 " +
            " and a.vehicule.masse_vehicule between :poids1 and :poids2 " +
            " and a.vehicule.carburant in :carburant " +
            " and a.vehicule.boite in :boite " +
            " and a.vehicule.couleur in :couleur " +
            " and a.vehicule.moteur in :moteur" +
            " and a.vehicule.pays in :pays")
    List<Annonce> recherche_multi_critere_annonce(
            @Param("marque")List<Marque> marque,
            @Param("modele")List<Modele> modele,
            @Param("prix1") double prix1,
            @Param("prix2")double prix2,
            @Param("date1")LocalDateTime date1,
            @Param("date2")LocalDateTime date2,
            @Param("annee1")int annee1,
            @Param("annee2")int annee2,
            @Param("km1")int km1,
            @Param("km2")int km2,
            @Param("poids1")double poids1,
            @Param("poids2")double poids2,
            @Param("boite")List<Boite> boite,
            @Param("carburant")List<Carburant> carburant,
            @Param("couleur")List<Couleur> couleurs,
            @Param("moteur")List<Moteur> moteur,
            @Param("pays")List<Pays> pays
            );

    @Query("select a from Annonce a " +
            "where lower(a.vehicule.pays.nom_pays) like '%'|| :ref || '%' " +
            "or lower(a.Description)  like '%'|| :ref || '%' " +
            "or lower(a.vehicule.modele.nom_modele) like '%'|| :ref || '%' " +
            "or lower(a.vehicule.modele.marque.nom_marque) like '%'|| :ref || '%' " +
            "or lower(a.vehicule.moteur.nom_moteur) like '%'|| :ref || '%' " +
            "or lower(a.vehicule.carburant.nom_carburant) like '%'|| :ref || '%' " +
            "or lower(a.vehicule.boite.nom_boite) like '%'|| :ref || '%' " +
            "or lower(a.vehicule.categorie.nom_categorie) like '%'|| :ref || '%' " +
            "or lower(a.vehicule.couleur.nom_couleur) like '%'|| :ref || '%' " +
            "or lower(a.vehicule.immatricule) like '%'|| :ref || '%'")
    List<Annonce> rechercheQueryAnnonce(@Param("ref") String querySearch);






}
