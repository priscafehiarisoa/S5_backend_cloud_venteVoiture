package gestionVehicules.controller.vehicule;


import gestionVehicules.model.vehicule.Categorie;
import gestionVehicules.repository.CategorieRepository;
import gestionVehicules.repository.sequence.SequenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@CrossOrigin()
@RestController
@RequestMapping("/categorie")
public class CategorieController {
    @Autowired
    CategorieRepository categorieRepository;
    @Autowired
    private SequenceRepository sequenceRepository;

    @PostMapping
    public Object insertCategorie(@RequestBody Categorie categorie){
        int id=categorieRepository.getNextval();
        categorie.setId_categorie(sequenceRepository.getSequence(3,"CAT",Categorie.getSequenceName()));
        categorieRepository.save(categorie);
        HashMap<String,Object> returnType=new HashMap<>();
        returnType.put("statut",200);
        returnType.put("erreur",null);
        return  returnType;
    }

    @GetMapping
    public Object getAllCategories(){

    List<Categorie>categories= categorieRepository.findAll();
        HashMap<String,Object> returnType=new HashMap<>();
        returnType.put("donnee",categories);
        returnType.put("statut",200);
        returnType.put("erreur",null);
        return  returnType;
    }

    @PutMapping("/{id}")
    public Categorie updateCategorie(@RequestBody Categorie categorie, @PathVariable String id) {
        return categorieRepository.findById(String.valueOf(id)).map(
                categorie1 -> {
                    categorie1.setNom_categorie(categorie.getNom_categorie());

                    return categorieRepository.save(categorie1);
                }
        ).orElseGet(() -> {
                    categorie.setId_categorie(String.valueOf(id));
                    return categorieRepository.save(categorie);

                }
        );
    }

    @DeleteMapping("/{id}")
    public Object deleteCategorie(@PathVariable String id) throws IllegalAccessException {
        categorieRepository.deleteById(id);
        HashMap<String,Object> returnType=new HashMap<>();
        returnType.put("statut",200);
        returnType.put("erreur",null);
        return  returnType;
    }
}
