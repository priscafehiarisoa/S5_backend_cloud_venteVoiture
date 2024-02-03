package gestionVehicules.repository;

import gestionVehicules.model.Statistique;
import gestionVehicules.model.annonce.Annonce;
import gestionVehicules.model.annonce.VenteAnnonce;
import gestionVehicules.model.vehicule.Categorie;
import gestionVehicules.model.vehicule.Marque;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

//int nombre;
//int moisOuAnnee;
public interface VenteAnnonceRepository extends JpaRepository<VenteAnnonce, String> {
    @Query("select new gestionVehicules.model.Statistique(CAST(COUNT(s.id) AS java.lang.Integer), extract(year from s.dateVente)) from VenteAnnonce s group by extract(year from s.dateVente)")
    List<Statistique> getVenteParAnnee();

    @Query(value = "select  month,max(vente.nombre) from " +
            "                                    ( " +
            "select EXTRACT(MONTH FROM vente_annonce.date_vente) AS month " +
            "      , COUNT(vente_annonce.id) as nombre from vente_annonce " +
            "where extract(year from date_vente)=:annee " +
            "  group by month " +
            "UNION ALL " +
            "select m.mois,0 from (SELECT 1 AS mois" +
            "                          UNION SELECT 2" +
            "                          UNION SELECT 3" +
            "                          UNION SELECT 4" +
            "                          UNION SELECT 5" +
            "                          UNION SELECT 6" +
            "                          UNION SELECT 7" +
            "                          UNION SELECT 8" +
            "                          UNION SELECT 9" +
            "                          UNION SELECT 10" +
            "                          UNION SELECT 11" +
            "                          UNION SELECT 12) as m) as vente " +
            "group by vente.month" +
            " order by month" , nativeQuery = true)
    List<Object[]> getVenteParMoisAnnee(@Param("annee") int annee);
    // nombre de vehicules vendues pa

    // vente par marque
    @Query("select new gestionVehicules.model.Statistique(CAST(COUNT(s.id) AS java.lang.Integer)," +
            " extract(year from s.dateVente)) from VenteAnnonce s " +
            " where s.annonce.vehicule.modele.marque=:marque group by extract(year from s.dateVente)")
    List<Statistique> getVenteParMarque(@Param("marque") Marque marque);
    @Query(value = "select max(nombre),month         from" +
            "(select COUNT(s.id) as nombre," +
            "             extract(Month from s.date_vente) as month from vente_annonce as  s" +
            "                                              join public.annonce a on a.id_annonce = s.annonce_id_annonce" +
            "                                              join public.vehicule v on v.id_vehicule = a.id_vehicule" +
            "                                              join modele on v.id_modele = modele.id_modele " +
            "             where modele.id_marque=:marque and extract(Year from s.date_vente)=:annee group by extract(Month from s.date_vente) " +
            "UNION ALL " +
            "select 0,m.mois from (SELECT 1 AS mois " +
            "                      UNION SELECT 2" +
            "                      UNION SELECT 3" +
            "                      UNION SELECT 4" +
            "                      UNION SELECT 5" +
            "                      UNION SELECT 6" +
            "                      UNION SELECT 7" +
            "                      UNION SELECT 8" +
            "                      UNION SELECT 9" +
            "                      UNION SELECT 10" +
            "                      UNION SELECT 11" +
            "                      UNION SELECT 12) as m) as venteMarque " +
            "group by month " +
            "order by month ", nativeQuery = true)
    List<Object[]> getVenteParMarqueEtAnnee(@Param("marque") String idmarque,int annee);

    // vente par categorie
    @Query("select new gestionVehicules.model.Statistique(CAST(COUNT(s.id) AS java.lang.Integer), extract(year from s.dateVente)) from VenteAnnonce s where s.annonce.vehicule.categorie=:categorie group by extract(year from s.dateVente)")
    List<Statistique> getVenteParCategorie(@Param("categorie") Categorie marque);

    // nombre d'annonces non Vendues en n jours
    @Query(value = "select count(a) from Annonce a where a.etat=10 and FUNCTION('DATEDIFF', DAY, a.date_annonce, CURRENT_DATE) >=:days ")
    int nombreAnnoncesnonVenduesEn_N_Days(@Param("days")int days);
    @Query(value = "select count(a) from Annonce a where a.etat=20 and FUNCTION('DATEDIFF', DAY, a.date_annonce, CURRENT_DATE) >=:days ")
    int nombreAnnoncesVenduesEn_N_Days(@Param("days")int days);

    @Query(value = "select a from Annonce a where a.etat=10 and FUNCTION('DATEDIFF', DAY, a.date_annonce, CURRENT_DATE) >=:days ")
    List<Annonce> listeAnnoncesNonVenduresEn_N_jours(@Param("days")int days);

    // annonces les plus aimés

    // total commissions Obtenues
    @Query("select coalesce(sum(s.montantCommission),0) from Commissions s")
    public double totalCommissionsObtenues();

    @Query("select coalesce(sum(s.montantCommission),0) from Commissions s where extract(month from s.dateCommsission) = :mois and extract(year from s.dateCommsission)=:year ")
    double totalCommissionsParMoisEtAnnee(int mois,int year);

    @Query("select v.annonce.vehicule.modele.marque  ,count(v) as nombre from VenteAnnonce v where v.annonce.vehicule.modele.marque.etat=0  group by v.annonce.vehicule.modele.marque order by nombre ")
    List<Marque> marqueLePlusVendu(Pageable pageable);
    @Query(value = "select id_marque,max(nombre)from( " +
            "                                     select marque.id_marque,0 as nombre from marque " +
            "                                     union all " +
            "                                     select m2.id_marque  ,count(v) as nombre from vente_annonce v " +
            "                                                                                       join public.annonce a on a.id_annonce = v.annonce_id_annonce" +
            "                                                                                       join public.vehicule v2 on a.id_vehicule = v2.id_vehicule" +
            "                                                                                       join public.modele m on v2.id_modele = m.id_modele" +
            "                                                                                       join public.marque m2 on m.id_marque = m2.id_marque " +
            "                                     where m2.etat=0  group by m2, m2.id_marque order by nombre " +
            "                                 ) as imnimn " +
            "group by id_marque " +
            "order by max(nombre) desc limit :limit ",nativeQuery = true)
    List<Object[]> classementMarque(int limit);
    @Query(value = "select id_categorie,max(nombre)from( " +
            "                                     select categorie.id_categorie,0 as nombre from categorie " +
            "                                     union all " +
            "                                     select c.id_categorie  ,count(v) as nombre from vente_annonce v " +
            "                                                                                       join public.annonce a on a.id_annonce = v.annonce_id_annonce" +
            "                                                                                       join public.vehicule v2 on a.id_vehicule = v2.id_vehicule " +
            "                                                                                       join public.categorie c on c.id_categorie = v2.id_categorie" +
            "                                     where c.etat=0  group by  c.id_categorie order by nombre " +
            "                                 ) as imnimn " +
            "group by id_categorie " +
            "order by max(nombre) desc limit :limit ",nativeQuery = true)
    List<Object[]> classementCategorie(int limit);

}