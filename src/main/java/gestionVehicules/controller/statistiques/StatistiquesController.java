package gestionVehicules.controller.statistiques;

import gestionVehicules.model.Statistique;
import gestionVehicules.model.vehicule.Marque;
import gestionVehicules.repository.MarqueRepository;
import gestionVehicules.repository.VenteAnnonceRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@CrossOrigin()
@RestController
@RequestMapping("/statistiques")
public class StatistiquesController {
    private final StatistiqueService statistiqueService;
    private final MarqueRepository marqueRepository;
    private final VenteAnnonceRepository venteAnnonceRepository;

    public StatistiquesController(StatistiqueService statistiqueService, MarqueRepository marqueRepository,
                                  VenteAnnonceRepository venteAnnonceRepository) {
        this.statistiqueService = statistiqueService;
        this.marqueRepository = marqueRepository;
        this.venteAnnonceRepository = venteAnnonceRepository;
    }

    @GetMapping("/totalCommission")
    public HashMap<String,Object> gettotalCommission(){
        HashMap<String,Object> returnValue=new HashMap<>();
        try {
            double total = statistiqueService.totalCommissions();
            returnValue.put("statut", 200);
            returnValue.put("erreur", "");
            returnValue.put("donnee", total);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return returnValue;
    }

    @PostMapping("/totalCommissionMoisAnnee")
    public HashMap<String,Object> gettotalCommissionMoisAnnee(@RequestBody HashMap<String,Object> request){
        HashMap<String,Object> returnValue=new HashMap<>();
        // get from form
        int mois= (int) request.get("mois");
        int annee= (int) request.get("annee");
        double total=statistiqueService.commissionparMoisEtAnnee(mois,annee);
        returnValue.put("statut",200);
        returnValue.put("erreur","");
        returnValue.put("donnee",total);
        return returnValue;
    }

    @PostMapping("/statistiqueVenteParMois")
    public HashMap<String,Object> statistiqueVenteParMois(@RequestBody HashMap<String,Object> anneeHM){
        int annee= Integer.valueOf(String.valueOf(anneeHM.get("annee")));
        System.out.println(" | "+annee);

        List<Statistique> venteParMois=statistiqueService.getStatistiquesParVentesMois(annee);
        HashMap<String,Object> donnee = new HashMap<>();
        donnee.put("title","statistique représentant les ventes par année");
        donnee.put("statistique",venteParMois);
        donnee.put("annee",annee);

        HashMap<String ,Object> returnValue=new HashMap<>();
        returnValue.put("erreur","");
        returnValue.put("statut",200);
        returnValue.put("donnee",donnee);
        return returnValue;
    }

    @PostMapping("/statistiqueVenteParMarque")
    public HashMap<String,Object> statistiqueVenteParMarque(@RequestBody HashMap<String,Object> marque_annee){
        int annee= Integer.valueOf(String.valueOf(marque_annee.get("annee")));
        String idmarque= (String.valueOf(marque_annee.get("marque")));
        System.out.println(idmarque+" | "+annee);

        HashMap<String ,Object> returnValue=new HashMap<>();

        try {
            Marque marque=new Marque().getMarqueById(idmarque,marqueRepository);
            List<Statistique> venteParMois = statistiqueService.nombreDeVenteParMarque(idmarque,annee);
            HashMap<String, Object> donnee = new HashMap<>();
            donnee.put("title", "statistique représentant les ventes par Marque année");
            donnee.put("statistique", venteParMois);
            donnee.put("annee", annee);
            donnee.put("marque", marque);
            returnValue.put("erreur","");
            returnValue.put("donnee",donnee);


        }catch (Exception e){
            returnValue.put("erreur",e.getMessage());

        }
        returnValue.put("statut",200);
        return returnValue;
    }
    @GetMapping("/marqueLePlusVendu")
    public HashMap<String,Object> getmarqueLePlusVendu(){
        Pageable pageable = PageRequest.of(0, 1);
        List<Marque> optional=venteAnnonceRepository.marqueLePlusVendu(pageable);
        HashMap<String,Object> returnValue=new HashMap<>();
        if(!optional.isEmpty()) {
            returnValue.put("donnee",optional.get(0));
            returnValue.put("statut",200);

        }
        else{
            returnValue.put("erreur","marque introuvable");
            returnValue.put("statut",200);
        }
        return returnValue;

    }
    @PostMapping("/classementMarqueVendues")
    public HashMap<String,Object> classementParMarque(@RequestBody HashMap<String,Object> request){
        // obtenir la limite de pages à afficher
        int limite=Integer.valueOf(String.valueOf(request.get("pages")));
        HashMap<String, Object> returnValue = new HashMap<>();

        try {
            List<HashMap<String, Object>> hashMaps = statistiqueService.classementParMarque(limite);
            returnValue.put("erreur", "");
            returnValue.put("donnee", hashMaps);
            returnValue.put("statut", 200);
        }
        catch (Exception e){
            returnValue.put("erreur", e.getMessage());
            returnValue.put("donnee", null);
            returnValue.put("statut", 404);
        }
        return returnValue;
    }
    @PostMapping("/classementCategorieVendues")
    public HashMap<String,Object> classementCategorieVendues(@RequestBody HashMap<String,Object> request){
        // obtenir la limite de pages à afficher
        int limite=Integer.valueOf(String.valueOf(request.get("pages")));
        HashMap<String, Object> returnValue = new HashMap<>();

        try {
            List<HashMap<String, Object>> hashMaps = statistiqueService.classementParCategorie(limite);
            returnValue.put("erreur", "");
            returnValue.put("donnee", hashMaps);
            returnValue.put("statut", 200);
        }
        catch (Exception e){
            returnValue.put("erreur", e.getMessage());
            returnValue.put("donnee", null);
            returnValue.put("statut", 404);
        }
        return returnValue;
    }
    @GetMapping("/nombreAnnoncesVendues/{nombreJours}")
    public HashMap<String,Object> nombreAnnoncesVendues (@PathVariable("nombreJours") int nombreJours){
        double resultat= venteAnnonceRepository.nombreAnnoncesVenduesEn_N_Days(nombreJours);
        HashMap<String, Object> returnValue = new HashMap<>();
        returnValue.put("erreur", null);
        returnValue.put("donnee", resultat);
        returnValue.put("statut", 200);
        return returnValue;
    }
    @GetMapping("/nombreAnnoncesNonVendues/{nombreJours}")
    public HashMap<String,Object> nombreAnnoncesNOnVendues (@PathVariable("nombreJours") int nombreJours){
        double resultat= venteAnnonceRepository.nombreAnnoncesnonVenduesEn_N_Days(nombreJours);
        HashMap<String, Object> returnValue = new HashMap<>();
        returnValue.put("erreur", null);
        returnValue.put("donnee", resultat);
        returnValue.put("statut", 200);
        return returnValue;
    }
}
