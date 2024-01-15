package gestionVehicules.controller.vehicule;

import gestionVehicules.model.vehicule.Marque;
import gestionVehicules.model.vehicule.Moteur;
import gestionVehicules.repository.MoteurRepository;
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

    @PostMapping
    public Object insertMoteur(@RequestBody Moteur moteur){
        int id=moteurRepository.getNextval();
        moteur.setId_moteur(moteurRepository.getSequence(3,"MTR",id));
        moteurRepository.save(moteur);
        HashMap<String,Object> returnType=new HashMap<>();
        returnType.put("statuts",200);
        returnType.put("errreur",null);
        return  returnType;
    }

    @GetMapping
    public Object getAllMoteurs(){
        List<Moteur>moteurs= moteurRepository.findAll();
        HashMap<String,Object> returnType=new HashMap<>();
        returnType.put("statuts",200);
        returnType.put("errreur",null);
        returnType.put("donnee",moteurs);
        return  returnType;
    }

    @PutMapping("/{id}")
    public Moteur updateMoteur(@RequestBody Moteur moteur, @PathVariable String id) {
        return moteurRepository.findById(String.valueOf(id)).map(
                entity1 -> {
                    entity1.setNom_moteur(moteur.getNom_moteur());
                    entity1.setPuissance(moteur.getPuissance());

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
        returnType.put("statuts",200);
        returnType.put("errreur",null);
        return  returnType;
    }
}
