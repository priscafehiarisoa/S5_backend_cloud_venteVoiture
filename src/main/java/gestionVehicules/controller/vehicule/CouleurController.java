package gestionVehicules.controller.vehicule;

import gestionVehicules.model.vehicule.Carburant;
import gestionVehicules.model.vehicule.Couleur;
import gestionVehicules.repository.CarburantRepository;
import gestionVehicules.repository.CouleurRepository;
import gestionVehicules.repository.sequence.SequenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@CrossOrigin()
@RestController
@RequestMapping("/couleur")
public class CouleurController {
    @Autowired
    CouleurRepository couleurRepository;
    @Autowired
    private SequenceRepository sequenceRepository;

    @PostMapping
    public Object insertCouleur(@RequestBody HashMap<String,Object> coul) throws Exception {
        HashMap<String,Object> returnType=new HashMap<>();

        try {
            Couleur couleur = new Couleur();
            String nom_couleur = (String) coul.get("nom_couleur");
            couleur.setNom_couleur(nom_couleur);
            couleur.setId_couleur(sequenceRepository.getSequence(3, "CLR", Couleur.getSequenceName()));
            couleurRepository.save(couleur);
            returnType.put("statut",200);

        }
        catch (Exception e) {
            returnType.put("erreur", e);
            returnType.put("statut",404);

        }
        return  returnType;
    }

    @GetMapping
    public Object getAllCouleurs(){
        List<Couleur>couleurs= couleurRepository.findAll();
        HashMap<String,Object> returnType=new HashMap<>();
        returnType.put("statut",200);
        returnType.put("erreur",null);
        returnType.put("donnee",couleurs);
        return  returnType;
    }

    @PutMapping("/{id}")
    public Couleur updateCouleur(@RequestBody Couleur couleur, @PathVariable String id) {
        return couleurRepository.findById(String.valueOf(id)).map(
                entity1 -> {
                    try {
                        entity1.setNom_couleur(couleur.getNom_couleur());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    return couleurRepository.save(entity1);
                }
        ).orElseGet(() -> {
                    couleur.setId_couleur(String.valueOf(id));
                    return couleurRepository.save(couleur);

                }
        );
    }

    @DeleteMapping("/{id}")
    public Object deleteCouleur(@PathVariable String id) throws IllegalAccessException {
        couleurRepository.deleteById(id);
        HashMap<String,Object> returnType=new HashMap<>();
        returnType.put("statut",200);
        returnType.put("erreur",null);
        return  returnType;
    }
}
