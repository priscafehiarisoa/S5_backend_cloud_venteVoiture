package gestionVehicules.controller.annonce;

import gestionVehicules.model.annonce.Annonce;
import gestionVehicules.model.annonce.Favori;
import gestionVehicules.model.user.Utilisateur;
import gestionVehicules.model.vehicule.Marque;
import gestionVehicules.model.vehicule.Modele;
import gestionVehicules.repository.annonce.AnnonceRepository;
import gestionVehicules.repository.annonce.FavoriRepository;
import gestionVehicules.repository.sequence.SequenceRepository;
import gestionVehicules.repository.user.UtilisateurRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@CrossOrigin()
@RestController
@RequestMapping("/favori")
public class FavoriController {

    private final UtilisateurRepository utilisateurRepository;
    private final AnnonceRepository annonceRepository;
    private final SequenceRepository sequenceRepository;
    private final FavoriRepository favoriRepository;

    public FavoriController(UtilisateurRepository utilisateurRepository,
                            AnnonceRepository annonceRepository,
                            SequenceRepository sequenceRepository,
                            FavoriRepository favoriRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.annonceRepository = annonceRepository;
        this.sequenceRepository = sequenceRepository;
        this.favoriRepository = favoriRepository;
    }

    @PostMapping("/{idutilisateur}/{idannonce}")
    public Object ajouterFavori(@PathVariable String idutilisateur,@PathVariable String idannonce) {
        HashMap<String,Object> returnType=new HashMap<>();

        try {
            Favori favori = new Favori();
            Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findById(idutilisateur);
            Optional<Annonce> annonceOptional = annonceRepository.findById(idannonce);
            Utilisateur utilisateur = new Utilisateur();
            Annonce annonce = new Annonce();
            if (utilisateurOptional.isPresent() && annonceOptional.isPresent()) {
                utilisateur = utilisateurOptional.get();
                annonce = annonceOptional.get();
            }
            LocalDate localDate = LocalDate.now();
            favori.setDateAjout(localDate);
            favori.setUtilisateur(utilisateur);
            favori.setAnnonce(annonce);
            favori.setId_favori(sequenceRepository.getSequence(3, "FAV", Favori.getSequenceName()));
            favoriRepository.save(favori);
            returnType.put("statut",200);

        }
        catch (Exception e)
        {
            returnType.put("statut",404);
            returnType.put("erreur",e);

        }
        return returnType;
    }

    @PutMapping("/updateEtat/{id}")
    public ResponseEntity<?> updateModeleEtat(@PathVariable String id) {
        try {
            // Appel du service pour mettre à jour l'état de la catégorie avec l'ID spécifié.
            favoriRepository.updateFavoriEtat(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de la mise à jour de l'état du favori.");
        }
    }

    @GetMapping("/{idutilisateur}")
    public Object getFavorisParUtilisateur(@PathVariable String idutilisateur){
        List<Annonce> annonceList= favoriRepository.getFavoriDispoParUtilisateur(idutilisateur);
        List<HashMap> refinedAnnonce=new ArrayList<>();
        for (int i = 0; i < annonceList.size(); i++) {
            annonceList.get(i).setInFavorites(annonceList.get(i).checkIFInFavorites(favoriRepository));
            refinedAnnonce.add(annonceList.get(i).getAnnoncemodifie());
        };
        HashMap<String,Object> returnType=new HashMap<>();
        returnType.put("statut",200);
        returnType.put("erreur",null);
        returnType.put("donnee",refinedAnnonce);
        return  returnType;
    }

    @GetMapping("/nombrefavori/{idAnnonce}")
    public Object getFavorisnombre(@PathVariable String idAnnonce){
        double nbfavoris= favoriRepository.nombreFavori(idAnnonce);
        HashMap<String,Object> returnType=new HashMap<>();
        returnType.put("statut",200);
        returnType.put("erreur",null);
        returnType.put("donnee",nbfavoris);
        return  returnType;
    }

}
