package gestionVehicules.controller.vehicule;


import gestionVehicules.model.vehicule.Categorie;
import gestionVehicules.repository.CategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin()
@RestController
@RequestMapping("/categorie")
public class CategorieController {
    @Autowired
    CategorieRepository categorieRepository;

    @PostMapping
    public void insertCategorie(@RequestBody Categorie categorie){
        int id=categorieRepository.getNextval();
        categorie.setId_categorie(categorieRepository.getSequence(3,"CAT",id));
        categorieRepository.save(categorie);
    }

    @GetMapping
    public List<Categorie> getAllCategories(){
    return categorieRepository.findAll();
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
    public void deleteCategorie(@PathVariable String id) throws IllegalAccessException {
        categorieRepository.deleteById(id);
    }
}
