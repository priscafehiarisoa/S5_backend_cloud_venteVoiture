package gestionVehicules.controller.vehicule;

import gestionVehicules.model.vehicule.Marque;
import gestionVehicules.model.vehicule.Moteur;
import gestionVehicules.repository.MoteurRepository;
import gestionVehicules.repository.sequence.SequenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.HashMap;
import java.util.List;

@CrossOrigin()
@RestController
@RequestMapping("/moteur")
public class MoteurController {
    @Autowired
    MoteurRepository moteurRepository;
    @Autowired
    private SequenceRepository sequenceRepository;

    @PostMapping
    public Object insertMoteur(@RequestBody HashMap<String,Object> mot) throws Exception {
        HashMap<String,Object> returnType=new HashMap<>();
        try {
            Moteur moteur = new Moteur();
            String nom_moteur = (String) mot.get("nom_moteur");
            double puissance = (double) mot.get("puissance");
            moteur.setPuissance(puissance);
            moteur.setNom_moteur(nom_moteur);
            moteur.setId_moteur(sequenceRepository.getSequence(3, "MTR", Moteur.getSequenceName()));
            moteurRepository.save(moteur);
            returnType.put("statut",200);

        }
        catch (Exception e){
            returnType.put("statut",404);
            returnType.put("erreur",e);
        }

        return  returnType;
    }

    @GetMapping
    public Object getAllMoteurs(){
        List<Moteur>moteurs= moteurRepository.findAll();
        HashMap<String,Object> returnType=new HashMap<>();
        returnType.put("statut",200);
        returnType.put("erreur",null);
        returnType.put("donnee",moteurs);
        return  returnType;
    }

    @PutMapping("/{id}")
    public Moteur updateMoteur(@RequestBody Moteur moteur, @PathVariable String id) {
        return moteurRepository.findById(String.valueOf(id)).map(
                entity1 -> {
                    try {
                        entity1.setNom_moteur(moteur.getNom_moteur());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        entity1.setPuissance(moteur.getPuissance());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    return moteurRepository.save(entity1);
                }
        ).orElseGet(() -> {
                    moteur.setId_moteur(String.valueOf(id));
                    return moteurRepository.save(moteur);

                }
        );
    }

    @DeleteMapping("/{id}")
    public Object deleteMoteur(@PathVariable String id) throws IllegalAccessException {
        moteurRepository.deleteById(id);
        HashMap<String,Object> returnType=new HashMap<>();
        returnType.put("statut",200);
        returnType.put("erreur",null);
        return  returnType;
    }
}
