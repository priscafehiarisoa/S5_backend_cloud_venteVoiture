package gestionVehicules.controller.vehicule;

import gestionVehicules.model.vehicule.Marque;
import gestionVehicules.model.vehicule.Modele;
import gestionVehicules.model.vehicule.Pays;
import gestionVehicules.repository.MarqueRepository;
import gestionVehicules.repository.ModeleRepository;
import gestionVehicules.repository.sequence.SequenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@CrossOrigin()
@RestController
@RequestMapping("/modele")
public class ModeleController {
    @Autowired
    ModeleRepository modeleRepository;
    @Autowired
    private SequenceRepository sequenceRepository;
    @Autowired
    private MarqueRepository marqueRepository;

    @PostMapping
    public Object insertModele(@RequestBody HashMap<String,Object> mod){
        HashMap<String,Object> returnType=new HashMap<>();

        try{
            Modele modele=new Modele();
            String nom_modele= (String) mod.get("nom_modele");
            String id_marque= (String) mod.get("id_marque");
            Optional<Marque> marqueOptional= marqueRepository.findById(id_marque);
            Marque marque=null;
            if (marqueOptional.isPresent()){
                marque=marqueOptional.get();
            }
            modele.setNom_modele(nom_modele);
            modele.setMarque(marque);
            modele.setId_modele(sequenceRepository.getSequence(3,"MDL",Modele.getSequenceName()));
            modeleRepository.save(modele);
            returnType.put("statut",200);

        }
        catch (Exception e){
            returnType.put("statut",404);
            returnType.put("erreur",e);
        }


        return  returnType;
    }
    @GetMapping
    public Object getAllModele(){
        List<Modele>modeles= modeleRepository.findAll();
        HashMap<String,Object> returnType=new HashMap<>();
        returnType.put("statut",200);
        returnType.put("erreur",null);
        returnType.put("donnee",modeles);
        return  returnType;
    }

    @PutMapping("/{id}")
    public Modele updateModele(@RequestBody Modele modele, @PathVariable String id) {
        return modeleRepository.findById(String.valueOf(id)).map(
                entity1 -> {
                    try {
                        entity1.setNom_modele(modele.getNom_modele());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
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
        returnType.put("statut",200);
        returnType.put("erreur",null);
        return  returnType;
    }
}
