package gestionVehicules.controller.annonce;

import gestionVehicules.model.annonce.Annonce;
import gestionVehicules.model.vehicule.Vehicule;
import gestionVehicules.repository.annonce.AnnonceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@CrossOrigin()
@RestController
@RequestMapping("/annonce")
public class AnnonceController {
    @Autowired
    AnnonceRepository annonceRepository;

    @PostMapping
    public void insertAnnonce(@RequestBody Annonce annonce){
        int id=annonceRepository.getNextval();
        annonce.setId_annonce(annonceRepository.getSequence(3,"ANC",id));
        annonceRepository.save(annonce);
    }

    @GetMapping
    public Object getAllAnnonce(){
        HashMap<String,Object> returnType=new HashMap<>();
        List<Annonce>annonceList=annonceRepository.findAll();
        returnType.put("donnee",annonceList);
        returnType.put("statut",200);
        returnType.put("erreur",null);
        return returnType;
    }

    @GetMapping("/getAnnoncesEnCoursDeValidation")
    public Object getAnnoncesEnCoursDeValidation(){
        List<Annonce>annonceList= annonceRepository.getAnnoncesEnCoursDeValidation();
        HashMap<String,Object> returnType=new HashMap<>();
        returnType.put("statut",200);
        returnType.put("erreur",null);
        returnType.put("donnee",annonceList);
        return returnType;
    }

    @GetMapping("/getAnnoncesValidees")
    public Object getAnnoncesValidees(){
        List<Annonce>annonceList= annonceRepository.getAnnoncesValidees();
        HashMap<String,Object> returnType=new HashMap<>();
        returnType.put("statut",200);
        returnType.put("erreur",null);
        returnType.put("donnee",annonceList);
        return returnType;
    }



    @GetMapping("/getAnnoncesVendues")
    public Object getAnnoncesVendues(){
        List<Annonce>annonceList= annonceRepository.getAnnoncesVendues();
        HashMap<String,Object> returnType=new HashMap<>();
        returnType.put("statut",200);
        returnType.put("erreur",null);
        returnType.put("donnee",annonceList);
        return returnType;

    }
    @GetMapping("/getAnnoncesRefusees")
    public Object getAnnoncesRefusees(){
        List<Annonce>annonceList= annonceRepository.getAnnoncesRefusees();
        HashMap<String,Object> returnType=new HashMap<>();
        returnType.put("statut",200);
        returnType.put("erreur",null);
        returnType.put("donnee",annonceList);
        return returnType;
    }

    @PutMapping("/validerAnnonce/{id}")
    public Object validerAnnonce(@PathVariable String id){
        annonceRepository.validerAnnonce(id);
        HashMap<String,Object> returnType=new HashMap<>();
        returnType.put("statut",200);
        returnType.put("erreur",null);
        return returnType;
    }

    @PutMapping("/refuserAnnonce/{id}")
    public Object refuserAnnonce(@PathVariable String id){
        annonceRepository.refuserAnnonce(id);
        HashMap<String,Object> returnType=new HashMap<>();
        returnType.put("statut",200);
        returnType.put("erreur",null);
        return returnType;
    }

    @PutMapping("/vendreAnnonce/{id}")
    public Object vendreAnnonce(@PathVariable String id){
        annonceRepository.vendreAnnonce(id);
        HashMap<String,Object> returnType=new HashMap<>();
        returnType.put("statut",200);
        returnType.put("erreur",null);
        return returnType;
    }





}
