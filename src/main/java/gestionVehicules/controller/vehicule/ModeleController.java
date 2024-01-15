package gestionVehicules.controller.vehicule;

import gestionVehicules.model.vehicule.Modele;
import gestionVehicules.model.vehicule.Pays;
import gestionVehicules.repository.ModeleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@CrossOrigin()
@RestController
@RequestMapping("/modele")
public class ModeleController {
    @Autowired
    ModeleRepository modeleRepository;

    @PostMapping
    public Object insertModele(@RequestBody Modele modele){
        int id=modeleRepository.getNextval();
        modele.setId_modele(modeleRepository.getSequence(3,"MDL",id));
        modeleRepository.save(modele);
        HashMap<String,Object> returnType=new HashMap<>();
        returnType.put("statuts",200);
        returnType.put("errreur",null);
        return  returnType;
    }
    @GetMapping
    public Object getAllModele(){
        List<Modele>modeles= modeleRepository.findAll();
        HashMap<String,Object> returnType=new HashMap<>();
        returnType.put("statuts",200);
        returnType.put("errreur",null);
        returnType.put("donnee",modeles);
        return  returnType;
    }

    @PutMapping("/{id}")
    public Modele updateModele(@RequestBody Modele modele, @PathVariable String id) {
        return modeleRepository.findById(String.valueOf(id)).map(
                entity1 -> {
                    entity1.setNom_modele(modele.getNom_modele());
                    entity1.setMarque(modele.getMarque());

                    return modeleRepository.save(entity1);
                }
        ).orElseGet(() -> {
                    modele.setId_modele(String.valueOf(id));
                    return modeleRepository.save(modele);

                }
        );
    }

    @DeleteMapping("/{id}")
    public Object deleteModele(@PathVariable String id) throws IllegalAccessException {
        System.out.println("Deleting modele with id: " + id);
        modeleRepository.deleteById(id);
        HashMap<String,Object> returnType=new HashMap<>();
        returnType.put("statuts",200);
        returnType.put("errreur",null);
        return  returnType;
    }
}
