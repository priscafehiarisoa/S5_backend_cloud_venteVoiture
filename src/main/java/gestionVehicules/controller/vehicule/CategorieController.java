package gestionVehicules.controller.vehicule;


import gestionVehicules.model.vehicule.Categorie;
import gestionVehicules.repository.CategorieRepository;
import gestionVehicules.repository.sequence.SequenceRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

import static org.springframework.security.authorization.AuthorityReactiveAuthorizationManager.hasAuthority;

@CrossOrigin()
@RestController
@RequestMapping("/categorie")
public class CategorieController {
    @Autowired
    CategorieRepository categorieRepository;
    @Autowired
    private SequenceRepository sequenceRepository;

    @PostMapping
    public ResponseEntity<Object> insertCategorie(@RequestBody HashMap<String,Object> cat, HttpServletRequest request) throws Exception {
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
        return ResponseEntity.status((Integer) returnType.get("statut")).body(returnType);
    }

    @GetMapping
    public Object getAllCategories(){

    List<Categorie>categories= categorieRepository.categorieDispo();
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

    @PutMapping("/updateEtat/{id}")
    public ResponseEntity<?> updateCategorieEtat(@PathVariable String id) {
        try {
            // Appel du service pour mettre à jour l'état de la catégorie avec l'ID spécifié.
            categorieRepository.updateCategorieEtat(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de la mise à jour de l'état de la catégorie.");
        }
    }
}
