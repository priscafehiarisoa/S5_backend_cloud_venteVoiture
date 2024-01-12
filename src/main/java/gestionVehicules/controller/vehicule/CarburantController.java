package gestionVehicules.controller.vehicule;

import gestionVehicules.model.vehicule.Carburant;
import gestionVehicules.model.vehicule.Categorie;
import gestionVehicules.repository.CarburantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin()
@RestController
@RequestMapping("/carburant")
public class CarburantController {
    @Autowired
    CarburantRepository carburantRepository;

    @PostMapping
    public void insertCarburant(@RequestBody Carburant carburant){
        int id=carburantRepository.getNextval();
        carburant.setId_carburant(carburantRepository.getSequence(3,"CBR",id));
        carburantRepository.save(carburant);
    }

    @GetMapping
    public List<Carburant> getAllCarburants(){
        return carburantRepository.findAll();
    }

    @PutMapping("/{id}")
    public Carburant updateCarburant(@RequestBody Carburant carburant, @PathVariable String id) {
        return carburantRepository.findById(String.valueOf(id)).map(
                carburant1 -> {
                    carburant1.setNom_carburant(carburant.getNom_carburant());

                    return carburantRepository.save(carburant1);
                }
        ).orElseGet(() -> {
                    carburant.setId_carburant(String.valueOf(id));
                    return carburantRepository.save(carburant);

                }
        );
    }

    @DeleteMapping("/{id}")
    public void deleteCarburant(@PathVariable String id) throws IllegalAccessException {
        carburantRepository.deleteById(id);
    }
}
