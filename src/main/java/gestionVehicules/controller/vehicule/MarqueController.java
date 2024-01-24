package gestionVehicules.controller.vehicule;

import gestionVehicules.model.vehicule.Couleur;
import gestionVehicules.model.vehicule.Marque;
import gestionVehicules.repository.CouleurRepository;
import gestionVehicules.repository.MarqueRepository;
import gestionVehicules.repository.sequence.SequenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@CrossOrigin()
@RestController
@RequestMapping("/marque")
public class MarqueController {
    @Autowired
    MarqueRepository marqueRepository;
    @Autowired
    private SequenceRepository sequenceRepository;

    @PostMapping
    public Object insertMarque(@RequestBody HashMap<String,Object> mar) throws Exception {
        HashMap<String,Object> returnType=new HashMap<>();
        try{
            String nom_marque= (String) mar.get("nom_marque");
            Marque marque=new Marque();
            marque.setNom_marque(nom_marque);
            marque.setId_marque(sequenceRepository.getSequence(3,"MRQ",Marque.getSequenceName()));
            marqueRepository.save(marque);
            returnType.put("statut",200);

        }
        catch (Exception e){

        returnType.put("statut",404);
        returnType.put("erreur",e);
        }
        return  returnType;
    }

    @GetMapping
    public Object getAllMarques(){
        List<Marque>marques= marqueRepository.marqueDispo();
        HashMap<String,Object> returnType=new HashMap<>();
        returnType.put("statut",200);
        returnType.put("erreur",null);
        returnType.put("donnee",marques);
        return  returnType;
    }

    @PutMapping("/{id}")
    public Marque updateMarque(@RequestBody Marque marque, @PathVariable String id) {
        return marqueRepository.findById(String.valueOf(id)).map(
                entity1 -> {
                    try {
                        entity1.setNom_marque(marque.getNom_marque());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    return marqueRepository.save(entity1);
                }
        ).orElseGet(() -> {
                    marque.setId_marque(String.valueOf(id));
                    return marqueRepository.save(marque);

                }
        );
    }

    @DeleteMapping("/{id}")
    public Object deleteMarque(@PathVariable String id) throws IllegalAccessException {
        marqueRepository.deleteById(id);
        HashMap<String,Object> returnType=new HashMap<>();
        returnType.put("statut",200);
        returnType.put("erreur",null);
        return  returnType;
    }

    @PutMapping("/updateEtat/{id}")
    public ResponseEntity<?> updateMarqueEtat(@PathVariable String id) {
        try {
            // Appel du service pour mettre à jour l'état de la catégorie avec l'ID spécifié.
            marqueRepository.updateMarqueEtat(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de la mise à jour de l'état de la marque.");
        }
    }
}
