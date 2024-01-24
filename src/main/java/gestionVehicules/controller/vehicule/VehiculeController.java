package gestionVehicules.controller.vehicule;

import gestionVehicules.model.vehicule.Modele;
import gestionVehicules.model.vehicule.Vehicule;
import gestionVehicules.repository.VehiculeRepository;
import gestionVehicules.repository.sequence.SequenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@CrossOrigin()
@RestController
@RequestMapping("/vehicule")
public class VehiculeController {
    @Autowired
    VehiculeRepository vehiculeRepository;
    @Autowired
    private SequenceRepository sequenceRepository;

    @PostMapping
    public Object insertVehicule(@RequestBody Vehicule vehicule){
        int id=vehiculeRepository.getNextval();
        vehicule.setId_vehicule(sequenceRepository.getSequence(3,"VHL",Vehicule.getSequenceName()));
        vehiculeRepository.save(vehicule);
        HashMap<String,Object> returnType=new HashMap<>();
        returnType.put("statut",200);
        returnType.put("erreur",null);
        return  returnType;
    }

    @GetMapping
    public Object getAllVehicule(){
        List<Vehicule> vehicules= vehiculeRepository.findAll();
        HashMap<String,Object> returnType=new HashMap<>();
        returnType.put("statut",200);
        returnType.put("erreur",null);
        returnType.put("donnee",vehicules);
        return  returnType;
    }

    @PutMapping("/{id}")
        public Vehicule updateVehicule(@RequestBody Vehicule vehicule, @PathVariable String id) throws Exception {
        return vehiculeRepository.findById(String.valueOf(id)).map(
                entity1 -> {
                    try {
                        entity1.setImmatricule(vehicule.getImmatricule());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        entity1.setAnnee_fabrication(vehicule.getAnnee_fabrication());
                        entity1.setKilometrage_vehicule(vehicule.getKilometrage_vehicule());
                        entity1.setNombre_sieges(vehicule.getNombre_sieges());
                        entity1.setMasse_vehicule(vehicule.getMasse_vehicule());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                        entity1.setBoite(vehicule.getBoite());
                        entity1.setCategorie(vehicule.getCategorie());
                        entity1.setCarburant(vehicule.getCarburant());
                        entity1.setCouleur(vehicule.getCouleur());
                        entity1.setModele(vehicule.getModele());
                        entity1.setMoteur(vehicule.getMoteur());
                        entity1.setPays(vehicule.getPays());



                    return vehiculeRepository.save(entity1);
                }
        ).orElseGet(() -> {
                    vehicule.setId_vehicule(String.valueOf(id));
                    return vehiculeRepository.save(vehicule);

                }
        );
    }

    @DeleteMapping("/{id}")
    public Object deleteVehicule(@PathVariable String id) throws IllegalAccessException {
        vehiculeRepository.deleteById(id);
        HashMap<String,Object> returnType=new HashMap<>();
        returnType.put("statut",200);
        returnType.put("erreur",null);
        return  returnType;
    }



}
