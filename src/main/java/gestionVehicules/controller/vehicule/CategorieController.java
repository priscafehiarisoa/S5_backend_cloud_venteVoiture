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
    public Object insertCategorie(@RequestBody HashMap<String,Object> cat) throws Exception {
        HashMap<String,Object> returnType=new HashMap<>();

        try {

            String nom_categorie= (String) cat.get("nom_categorie");
            System.out.println(nom_categorie);
            Categorie categorie=new Categorie();
            categorie.setId_categorie(sequenceRepository.getSequence(3,"CAT",Categorie.getSequenceName()));
            categorie.setNom_categorie(nom_categorie);
            categorieRepository.save(categorie);
            returnType.put("statut",200);

        }
        catch (Exception e)
        {
            returnType.put("erreur",e);
            returnType.put("statut",404);

        }
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
                    try {
                        categorie1.setNom_categorie(categorie.getNom_categorie());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

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
