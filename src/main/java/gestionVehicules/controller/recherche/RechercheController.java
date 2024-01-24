package gestionVehicules.controller.recherche;

import gestionVehicules.model.annonce.Annonce;
import gestionVehicules.model.utils.Utils;
import gestionVehicules.model.vehicule.*;
import gestionVehicules.repository.*;
import gestionVehicules.repository.annonce.AnnonceRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin()
public class RechercheController {

    private final ModeleRepository modeleRepository;
    private final MarqueRepository marqueRepository;
    private final BoiteRepository boiteRepository;
    private final CarburantRepository carburantRepository;
    private final CouleurRepository couleurRepository;
    private final MoteurRepository moteurRepository;
    private final PaysRepository paysRepository;
    private final AnnonceRepository annonceRepository;

    public RechercheController(ModeleRepository modeleRepository,
                               MarqueRepository marqueRepository,
                               BoiteRepository boiteRepository,
                               CarburantRepository carburantRepository,
                               CouleurRepository couleurRepository,
                               MoteurRepository moteurRepository,
                               PaysRepository paysRepository, AnnonceRepository annonceRepository) {
        this.modeleRepository = modeleRepository;
        this.marqueRepository = marqueRepository;
        this.boiteRepository = boiteRepository;
        this.carburantRepository = carburantRepository;
        this.couleurRepository = couleurRepository;
        this.moteurRepository = moteurRepository;
        this.paysRepository = paysRepository;
        this.annonceRepository = annonceRepository;
    }

    @PostMapping("/form_recherche_multicritere")
    public Object form_recherche_multicritere(@RequestBody HashMap <String,Object> recherche_multi){
        HashMap<String , Object> returnType=new HashMap<>();
        try {
            // set Modele
            String idModele = String.valueOf(recherche_multi.get("idModele")).trim();
            List<Modele> modeles=new ArrayList<>();
            if(idModele.trim().equals("tous")){
                modeles=modeleRepository.findAll();
            }
            else {
                modeles.add(new Modele().getModeleById(idModele, modeleRepository)) ;
            }

            // set Marque
            String idMarque=String.valueOf(recherche_multi.get("idMarque")).trim();
            List<Marque> marques =new ArrayList<>();
            if(idMarque.toLowerCase().equals("tous")){
                marques=marqueRepository.findAll();
            }
            else {
                marques.add(new Marque().getMarqueById(idMarque, marqueRepository));
            }

            // price range
            double prix1=Double.valueOf(String.valueOf(recherche_multi.get("prix1")));
            double prix2=Double.valueOf(String.valueOf(recherche_multi.get("prix2")));

            // year range
            int anne1=Integer.valueOf(String.valueOf(recherche_multi.get("annee1")));
            int anne2=Integer.valueOf(String.valueOf(recherche_multi.get("annee2")));

            // km range
            int km1=Integer.valueOf(String.valueOf(recherche_multi.get("km1")));
            int km2=Integer.valueOf(String.valueOf(recherche_multi.get("km2")));

            // weight range
            double poids1=Double.valueOf(String.valueOf(recherche_multi.get("poids1")));
            double poids2=Double.valueOf(String.valueOf(recherche_multi.get("poids2")));

            // dates
            LocalDateTime date1= Utils.convertToDateTime(String.valueOf(recherche_multi.get("date1")));
            LocalDateTime date2= Utils.convertToDateTime(String.valueOf(recherche_multi.get("date2")));

            // boite
            List<Boite> boites=new ArrayList<>();
            String idBoite=String.valueOf(recherche_multi.get("idBoite"));
            if(idBoite.toLowerCase().equals("tous")){
                boites=boiteRepository.findAll();
            }
            else {
                boites.add(new Boite().getBoiteById(idBoite, boiteRepository));
            }

            // carburant
            String idCarburant=String.valueOf(recherche_multi.get("idCarburant"));
            List<Carburant> carburants=new ArrayList<>();
            if(idCarburant.toLowerCase().equals("tous")){
                carburants=carburantRepository.findAll();
            }
            else {
                carburants.add( new Carburant().getCarburantById(idCarburant, carburantRepository));
            }

            // couleur
            List<Couleur> couleurs=new ArrayList<>();
            String idCouleur=String.valueOf(recherche_multi.get("idCouleur"));
            if(idCouleur.toLowerCase().equals("tous")){
                couleurs=couleurRepository.findAll();
            }
            else {
                couleurs.add( new Couleur().getCouleurById(idCouleur, couleurRepository));
            }
            // moteur
            String idMoteur=String.valueOf(recherche_multi.get("idMoteur"));
            List<Moteur> moteurs=new ArrayList<>();
            if(idMoteur.toLowerCase().equals("tous")){
                moteurs=moteurRepository.findAll();
            }
            else {
                moteurs.add( new Moteur().getMoteurById(idMoteur, moteurRepository));
            }

            //pays
            String idPays=String.valueOf(recherche_multi.get("idPays"));
            List<Pays> pays=new ArrayList<>();
            if(idPays.toLowerCase().equals("tous")){
                pays=paysRepository.findAll();
            }
            else {
                pays.add( new Pays().getPaysById(idPays, paysRepository));
            }

            List<Annonce> annonces =annonceRepository.recherche_multi_critere_annonce(
                    marques,
                    modeles,
                    prix1
                    ,prix2,
                    date1,
                    date2,
                    anne1,
                    anne2,
                    km1,
                    km2,
                    poids1,
                    poids2,
                    boites,
                    carburants,
                    couleurs,
                    moteurs,
                    pays);

            returnType.put("donnee",annonces);
            returnType.put("erreur","");
            returnType.put("statut",200);


        }catch (Exception e){
            returnType.put("erreur",e.getMessage());
        }
        return returnType;

    }

    @GetMapping("/simpleSearch/{searchQuery}")
    public Object simpleSearch(@PathVariable() String searchQuery){
        List<Annonce> annonces=annonceRepository.rechercheQueryAnnonce((searchQuery.toLowerCase().trim()));
        HashMap<String,Object> returnType=new HashMap<>();
        returnType.put("donnee",annonces);
        returnType.put("erreur","");
        returnType.put("statut",200);
        return returnType;
    }

    @GetMapping("/getSearchPage")
    public Object getSearchPage (){
        HashMap<String,Object> returnType=new HashMap<>();
        try {
            List<Marque> marques = marqueRepository.findAll();
            List<Modele> modeles = modeleRepository.findAll();
            List<Boite> boites = boiteRepository.findAll();
            List<Carburant> carburants = carburantRepository.findAll();
            List<Couleur> couleurs = couleurRepository.findAll();
            List<Moteur> moteurs = moteurRepository.findAll();
            List<Pays> pays = paysRepository.findAll();
            HashMap<String, Object> donnee = new HashMap<>();
            donnee.put("marque", marques);
            donnee.put("models", modeles);
            donnee.put("boite", boites);
            donnee.put("carburant", carburants);
            donnee.put("couleurs", couleurs);
            donnee.put("moteur", moteurs);
            donnee.put("pays", pays);
            returnType.put("erreur",null);
            returnType.put("statut",404);
            returnType.put("donnee",donnee);
        }catch (Exception e){
            returnType.put("erreur",e.getMessage());
            returnType.put("statut",404);
            returnType.put("donnee",null);
        }
       return returnType;

    }


}
