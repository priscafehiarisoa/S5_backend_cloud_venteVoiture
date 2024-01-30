package gestionVehicules.controller.statistiques;

import gestionVehicules.model.Statistique;
import gestionVehicules.model.vehicule.Categorie;
import gestionVehicules.model.vehicule.Marque;
import gestionVehicules.repository.CategorieRepository;
import gestionVehicules.repository.MarqueRepository;
import gestionVehicules.repository.VenteAnnonceRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatistiqueService {

    private final MarqueRepository marqueRepository;
    private final VenteAnnonceRepository venteAnnonceRepository;
    private final CategorieRepository categorieRepository;

    public StatistiqueService(MarqueRepository marqueRepository,
                              VenteAnnonceRepository venteAnnonceRepository,
                              CategorieRepository categorieRepository) {
        this.marqueRepository = marqueRepository;
        this.venteAnnonceRepository = venteAnnonceRepository;
        this.categorieRepository = categorieRepository;
    }

    public List<Statistique> nombreDeVehiculeVenduesParAnnee(){
        List<Statistique> retour=venteAnnonceRepository.getVenteParAnnee();

        return retour;
    }

    public List<Statistique> nombreDeVenteParMarque(String idMarque,int annee) throws Exception {
        List<Object[]> result = venteAnnonceRepository.getVenteParMarqueEtAnnee(idMarque,annee);

        return result.stream()
                .map(row -> new Statistique( Integer.valueOf(String.valueOf(row[0])),Integer.valueOf(String.valueOf(row[1]))))
                .collect(Collectors.toList());
    }
    public List<Statistique> nombreDeVenteParCategorie(String idCategorie) throws Exception {
        Categorie categorie=new Categorie().getCategorieById(idCategorie,categorieRepository);
        List<Statistique> retour=venteAnnonceRepository.getVenteParCategorie(categorie);
        return retour;
    }

    public int nombresDAnnoncesVenduesEnNJours(int nombreJours){
        return venteAnnonceRepository.nombreAnnoncesVenduesEn_N_Days(nombreJours);
    }

    public int nombresDAnnoncesNOnVenduesEnNJours(int nombreJours){
        return venteAnnonceRepository.nombreAnnoncesnonVenduesEn_N_Days(nombreJours);
    }

    public List<Statistique> getStatistiquesParVentesMois(int annee) {
        List<Object[]> result = venteAnnonceRepository.getVenteParMoisAnnee(annee);

        return result.stream()
                .map(row -> new Statistique( Integer.valueOf(String.valueOf(row[1])),Integer.valueOf(String.valueOf(row[0]))))
                .collect(Collectors.toList());
    }

    public List<HashMap<String,Object>> classementParMarque(int limit) throws Exception {
        List<Object[]> result = venteAnnonceRepository.classementMarque(limit);
        List<HashMap<String,Object>> resultatFinal= new ArrayList<>();
        for (int i = 0; i < result.size(); i++) {
            Marque marque= new Marque().getMarqueById(String.valueOf(result.get(i)[0]),marqueRepository);
            HashMap<String,Object> hashmap=new HashMap<>();
            hashmap.put("marque",(Marque) marque);
            hashmap.put("nombre",  result.get(i)[1]);
            resultatFinal.add(hashmap);
        }
        return resultatFinal;
    }
    public List<HashMap<String,Object>> classementParCategorie(int limit) throws Exception {
        List<Object[]> result = venteAnnonceRepository.classementCategorie(limit);
        List<HashMap<String,Object>> resultatFinal= new ArrayList<>();
        for (int i = 0; i < result.size(); i++) {
            Categorie marque= new Categorie().getCategorieById(String.valueOf(result.get(i)[0]),categorieRepository);
            HashMap<String,Object> hashmap=new HashMap<>();
            hashmap.put("categorie", marque);
            hashmap.put("nombre",  result.get(i)[1]);
            resultatFinal.add(hashmap);
        }
        return resultatFinal;
    }
    public double commissionparMoisEtAnnee(int mois, int annee){
        return venteAnnonceRepository.totalCommissionsParMoisEtAnnee(mois,annee);
    }
    public double totalCommissions(){
        return venteAnnonceRepository.totalCommissionsObtenues();
    }
}
