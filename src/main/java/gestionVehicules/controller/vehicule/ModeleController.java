package gestionVehicules.controller.vehicule;

import gestionVehicules.model.vehicule.Marque;
import gestionVehicules.model.vehicule.Modele;
import gestionVehicules.model.vehicule.Pays;
import gestionVehicules.repository.MarqueRepository;
import gestionVehicules.repository.ModeleRepository;
import gestionVehicules.repository.sequence.SequenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
        List<Modele>modeles= modeleRepository.modeleDispo();
        HashMap<String,Object> returnType=new HashMap<>();
        returnType.put("statut",200);
        returnType.put("erreur",null);
        returnType.put("donnee",modeles);
        return  returnType;
    }

    @PutMapping("/{id}")
    public Modele updateModele(@RequestBody HashMap<String,Object> mod, @PathVariable String id) {
        Modele entity1=new Modele();
                    try {

                        String nom= String.valueOf(mod.get("nom_modele"));
                        String idmodele= String.valueOf(mod.get("id_modele"));
                        entity1.setId_modele(idmodele);
                        entity1.setNom_modele(nom);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    String idmarque= String.valueOf(mod.get("id_marque"));
                    Optional<Marque> marque=marqueRepository.findById(idmarque);
                    Marque marque1=new Marque();
                    if(marque.isPresent()){
                        marque1=marque.get();
                    }
                    entity1.setMarque(marque1);

                    return modeleRepository.save(entity1);


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

    @PutMapping("/updateEtat/{id}")
    public ResponseEntity<?> updateModeleEtat(@PathVariable String id) {
        try {
            // Appel du service pour mettre à jour l'état de la catégorie avec l'ID spécifié.
            modeleRepository.updateModeleEtat(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de la mise à jour de l'état du modele.");
        }
    }
}
