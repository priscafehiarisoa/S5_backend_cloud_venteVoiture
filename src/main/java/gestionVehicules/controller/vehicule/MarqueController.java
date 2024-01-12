package gestionVehicules.controller.vehicule;

import gestionVehicules.model.vehicule.Couleur;
import gestionVehicules.model.vehicule.Marque;
import gestionVehicules.repository.CouleurRepository;
import gestionVehicules.repository.MarqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin()
@RestController
@RequestMapping("/marque")
public class MarqueController {
    @Autowired
    MarqueRepository marqueRepository;

    @PostMapping
    public void insertMarque(@RequestBody Marque marque){
        int id=marqueRepository.getNextval();
        marque.setId_marque(marqueRepository.getSequence(3,"MRQ",id));
        marqueRepository.save(marque);
    }

    @GetMapping
    public List<Marque> getAllMarques(){
        return marqueRepository.findAll();
    }

    @PutMapping("/{id}")
    public Marque updateMarque(@RequestBody Marque marque, @PathVariable String id) {
        return marqueRepository.findById(String.valueOf(id)).map(
                entity1 -> {
                    entity1.setNom_marque(marque.getNom_marque());

                    return marqueRepository.save(entity1);
                }
        ).orElseGet(() -> {
                    marque.setId_marque(String.valueOf(id));
                    return marqueRepository.save(marque);

                }
        );
    }

    @DeleteMapping("/{id}")
    public void deleteMarque(@PathVariable String id) throws IllegalAccessException {
        marqueRepository.deleteById(id);
    }
}
