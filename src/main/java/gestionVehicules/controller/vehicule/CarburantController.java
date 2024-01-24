package gestionVehicules.controller.vehicule;

import gestionVehicules.model.annonce.Annonce;
import gestionVehicules.model.vehicule.Carburant;
import gestionVehicules.model.vehicule.Categorie;
import gestionVehicules.repository.CarburantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

import static org.antlr.v4.runtime.tree.xpath.XPath.findAll;

@CrossOrigin()
@RestController
@RequestMapping("/carburant")
public class CarburantController {
    @Autowired
    CarburantRepository carburantRepository;

    @PostMapping
    public Object insertCarburant(@RequestBody Carburant carburant){
        int id=carburantRepository.getNextval();
        carburant.setId_carburant(carburantRepository.getSequence(3,"CBR",id));
        carburantRepository.save(carburant);
        HashMap<String,Object> returnType=new HashMap<>();
        returnType.put("statuts",200);
        returnType.put("errreur",null);
        return  returnType;
    }

    @GetMapping
    public Object getAllCarburants(){
        List<Carburant> carburantList= carburantRepository.findAll();
        HashMap<String,Object> returnType=new HashMap<>();
        returnType.put("statuts",200);
        returnType.put("errreur",null);
        returnType.put("donnee",carburantList);

        return  returnType;
    }

//    @PutMapping("/{id}")
//    public Carburant updateCarburant(@RequestBody Carburant carburant, @PathVariable String id) {
//        return carburantRepository.findById(String.valueOf(id)).map(
//                carburant1 -> {
//                    carburant1.setNom_carburant(carburant.getNom_carburant());
//
//                    return carburantRepository.save(carburant1);
//                }
//        ).orElseGet(() -> {
//                    carburant.setId_carburant(String.valueOf(id));
//                    return carburantRepository.save(carburant);
//
//                }
//        );
//    }
    @PutMapping("/{id}")
    public Object updateCarburantbis(@RequestBody Carburant carburant, @PathVariable String id) {
        Carburant carburant1=new Carburant();
        HashMap<String, Object> returnValue=new HashMap<>();
        try{
            carburant1=UpdateCarburantObj(carburant,id);
            returnValue.put("erreur",null);
            returnValue.put("statut",404);
            returnValue.put("donnee",carburant1);
        }
        catch (Exception e){
            returnValue.put("erreur",e.getMessage());
            returnValue.put("statut",404);
            returnValue.put("donnee",null);

        }
        return returnValue;
    }

    public Carburant UpdateCarburantObj(Carburant carburant, String id){
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
    public Object deleteCarburant(@PathVariable String id) throws IllegalAccessException {
        carburantRepository.deleteById(id);
        HashMap<String,Object> returnType=new HashMap<>();
        returnType.put("statuts",200);
        returnType.put("errreur",null);
        return  returnType;
    }
}
