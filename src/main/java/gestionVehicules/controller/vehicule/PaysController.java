package gestionVehicules.controller.vehicule;

import gestionVehicules.model.vehicule.Moteur;
import gestionVehicules.model.vehicule.Pays;
import gestionVehicules.repository.PaysRepository;
import gestionVehicules.repository.sequence.SequenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@CrossOrigin()
@RestController
@RequestMapping("/pays")
public class PaysController {
    @Autowired
    PaysRepository paysRepository;
    @Autowired
    private SequenceRepository sequenceRepository;

    @PostMapping
    public Object insertPays(@RequestBody HashMap<String,Object> pay) throws Exception {
        HashMap<String,Object> returnType=new HashMap<>();
        try {
            Pays pays = new Pays();
            String nom_pays = (String) pay.get("nom_pays");
            pays.setNom_pays(nom_pays);
            pays.setId_pays(sequenceRepository.getSequence(3, "PYS", Pays.getSequenceName()));
            paysRepository.save(pays);
            returnType.put("statut", 200);

        }
        catch (Exception e) {
            returnType.put("statut", 404);

            returnType.put("erreur", e);
        }
        return  returnType;
    }

    @GetMapping
    public Object getAllPays(){
        List<Pays>paysList= paysRepository.paysDispo();
        HashMap<String,Object> returnType=new HashMap<>();
        returnType.put("statut",200);
        returnType.put("erreur",null);
        returnType.put("donnee",paysList);
        return  returnType;
    }

    @PutMapping("/{id}")
    public Pays updatePays(@RequestBody Pays pays, @PathVariable String id) {
        return paysRepository.findById(String.valueOf(id)).map(
                entity1 -> {
                    try {
                        entity1.setNom_pays(pays.getNom_pays());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    return paysRepository.save(entity1);
                }
        ).orElseGet(() -> {
                    pays.setId_pays(String.valueOf(id));
                    return paysRepository.save(pays);

                }
        );
    }

    @DeleteMapping("/{id}")
    public Object deletePays(@PathVariable String id) throws IllegalAccessException {
        paysRepository.deleteById(id);
        HashMap<String,Object> returnType=new HashMap<>();
        returnType.put("statut",200);
        returnType.put("erreur",null);
        return  returnType;
    }

    @PutMapping("/updateEtat/{id}")
    public ResponseEntity<?> updatePaysEtat(@PathVariable String id) {
        try {
            // Appel du service pour mettre à jour l'état de la catégorie avec l'ID spécifié.
            paysRepository.updatePaysEtat(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de la mise à jour de l'état du pays.");
        }
    }
}
