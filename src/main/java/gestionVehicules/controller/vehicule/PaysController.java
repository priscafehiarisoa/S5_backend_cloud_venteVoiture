package gestionVehicules.controller.vehicule;

import gestionVehicules.model.vehicule.Moteur;
import gestionVehicules.model.vehicule.Pays;
import gestionVehicules.repository.PaysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@CrossOrigin()
@RestController
@RequestMapping("/pays")
public class PaysController {
    @Autowired
    PaysRepository paysRepository;
    @PostMapping
    public Object insertPays(@RequestBody Pays pays){
        int id=paysRepository.getNextval();
        pays.setId_pays(paysRepository.getSequence(3,"PYS",id));
        paysRepository.save(pays);
        HashMap<String,Object> returnType=new HashMap<>();
        returnType.put("statuts",200);
        returnType.put("errreur",null);
        return  returnType;
    }

    @GetMapping
    public Object getAllPays(){
        List<Pays>paysList= paysRepository.findAll();
        HashMap<String,Object> returnType=new HashMap<>();
        returnType.put("statuts",200);
        returnType.put("errreur",null);
        returnType.put("donnee",paysList);
        return  returnType;
    }

    @PutMapping("/{id}")
    public Pays updatePays(@RequestBody Pays pays, @PathVariable String id) {
        return paysRepository.findById(String.valueOf(id)).map(
                entity1 -> {
                    entity1.setNom_pays(pays.getNom_pays());

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
        returnType.put("statuts",200);
        returnType.put("errreur",null);
        return  returnType;
    }
}
