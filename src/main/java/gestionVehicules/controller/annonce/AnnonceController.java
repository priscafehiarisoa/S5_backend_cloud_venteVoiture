package gestionVehicules.controller.annonce;

import gestionVehicules.model.annonce.Annonce;
import gestionVehicules.model.annonce.CommissionAnnonce;
import gestionVehicules.model.annonce.Image;
import gestionVehicules.model.annonce.VenteAnnonce;
import gestionVehicules.model.transactionsBanquaires.Commissions;
import gestionVehicules.model.transactionsBanquaires.Transactions;
import gestionVehicules.model.user.Utilisateur;
import gestionVehicules.model.vehicule.Couleur;
import gestionVehicules.model.vehicule.Vehicule;
import gestionVehicules.repository.CommissionAnnonceRepository;
import gestionVehicules.repository.CommissionsRepository;
import gestionVehicules.repository.TransactionsRepository;
import gestionVehicules.repository.VenteAnnonceRepository;
import gestionVehicules.repository.annonce.AnnonceRepository;
import gestionVehicules.repository.annonce.FavoriRepository;
import gestionVehicules.repository.annonce.ImageRepository;
import gestionVehicules.repository.sequence.SequenceRepository;
import gestionVehicules.repository.user.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin()
@RestController
@RequestMapping("/annonce")
public class AnnonceController {
    @Autowired
    AnnonceRepository annonceRepository;
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private TransactionsRepository transactionsRepository;
    @Autowired
    private SequenceRepository sequenceRepository;
    @Autowired
    private CommissionAnnonceRepository commissionAnnonceRepository;
    @Autowired
    private CommissionsRepository commissionsRepository;
    @Autowired
    private VenteAnnonceRepository venteAnnonceRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private FavoriRepository favoriRepository;

    @PostMapping
    public void insertAnnonce(@RequestBody Annonce annonce) {
        annonce.setId_annonce(annonce.getId(sequenceRepository));
        CommissionAnnonce commissionAnnonce = commissionAnnonceRepository.getCommissionAnnonceByPrixAnnonce(annonce.getPrix());
        annonce.setCommission(commissionAnnonce.getPourcentageCommission());
        annonceRepository.save(annonce);
    }

    @GetMapping
    public Object getAllAnnonce() {
        HashMap<String, Object> returnType = new HashMap<>();
        List<Annonce> annonceList = annonceRepository.findAll();
        for (int i = 0; i < annonceList.size(); i++) {
            annonceList.get(i).setInFavorites(annonceList.get(i).checkIFInFavorites(favoriRepository));
        };
        returnType.put("donnee", annonceList);
        returnType.put("statut", 200);
        returnType.put("erreur", null);
        return returnType;
    }

    @GetMapping("/getAnnoncesEnCoursDeValidation")
    public Object getAnnoncesEnCoursDeValidation(){
        List<Annonce>annonceList= annonceRepository.getAnnoncesEnCoursDeValidation();
        for (int i = 0; i < annonceList.size(); i++) {
            annonceList.get(i).setInFavorites(annonceList.get(i).checkIFInFavorites(favoriRepository));
        };
        HashMap<String,Object> returnType=new HashMap<>();
        returnType.put("statut",200);
        returnType.put("erreur",null);
        returnType.put("donnee",annonceList);
        return returnType;
    }

    @GetMapping("/getAnnoncesValidees")
    public Object getAnnoncesValidees(){
        List<Annonce>annonceList= annonceRepository.getAnnoncesValidees();
        List<HashMap> refinedAnnonce=new ArrayList<>();
        for (int i = 0; i < annonceList.size(); i++) {
            annonceList.get(i).setInFavorites(annonceList.get(i).checkIFInFavorites(favoriRepository));
            refinedAnnonce.add(annonceList.get(i).getAnnoncemodifie());
        };
        HashMap<String,Object> returnType=new HashMap<>();
        returnType.put("statut",200);
        returnType.put("erreur",null);
        returnType.put("donnee",refinedAnnonce);
        return returnType;
    }



    @GetMapping("/getAnnoncesVendues")
    public Object getAnnoncesVendues(){
        List<Annonce>annonceList= annonceRepository.getAnnoncesVendues();
        List<HashMap> refinedAnnonce=new ArrayList<>();

        for (int i = 0; i < annonceList.size(); i++) {
            annonceList.get(i).setInFavorites(annonceList.get(i).checkIFInFavorites(favoriRepository));
            refinedAnnonce.add(annonceList.get(i).getAnnoncemodifie());

        };
        HashMap<String,Object> returnType=new HashMap<>();
        returnType.put("statut",200);
        returnType.put("erreur",null);
        returnType.put("donnee",refinedAnnonce);
        return returnType;

    }

    @GetMapping("/getAnnoncesRefusees")
    public Object getAnnoncesRefusees() {
        List<Annonce> annonceList = annonceRepository.getAnnoncesRefusees();
        List<HashMap> refinedAnnonce=new ArrayList<>();

        for (int i = 0; i < annonceList.size(); i++) {
            annonceList.get(i).setInFavorites(annonceList.get(i).checkIFInFavorites(favoriRepository));
            refinedAnnonce.add(annonceList.get(i).getAnnoncemodifie());

        };
        HashMap<String, Object> returnType = new HashMap<>();
        returnType.put("statut", 200);
        returnType.put("erreur", null);
        returnType.put("donnee",refinedAnnonce);
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

    @PutMapping("/validateAnnonce/{id}")
    public ResponseEntity<?> updateannonceEtat(@PathVariable String id) {
        try {
            // Appel du service pour mettre à jour l'état de la catégorie avec l'ID spécifié.
            annonceRepository.validerAnnonce(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de la mise à jour de l'état de la catégorie.");
        }
    }
    @PutMapping("/refuserAnnonce/{id}")
    public ResponseEntity<?> refuserAnnonce2(@PathVariable String id) {
        try {
            annonceRepository.refuserAnnonce(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors du refus de l'annonce.");
        }
    }

//    @PutMapping("/refuserAnnonce/{id}")
//    public Object refuserAnnonce(@PathVariable String id){
//        annonceRepository.refuserAnnonce(id);
//        HashMap<String,Object> returnType=new HashMap<>();
//        returnType.put("statut",200);
//        returnType.put("erreur",null);
//        return returnType;
//    }

    @PutMapping("/vendreAnnonce/{id}")
    public Object vendreAnnonce(@PathVariable String id){
        annonceRepository.vendreAnnonce(id);
        HashMap<String,Object> returnType=new HashMap<>();
        returnType.put("statut",200);
        returnType.put("erreur",null);
        return returnType;
    }

    @Transactional
    public Object venteVehicule(@RequestBody HashMap<String , Object> venteAeffectuer)  {
        HashMap<String,Object> returnValue=new HashMap<>();
        try {
            // set datas
            Utilisateur utilisateur = Utilisateur.getOptionalUserById(String.valueOf(venteAeffectuer.get("utilisateur")), utilisateurRepository);
            Annonce annonce= Annonce.getAnnonceById(String.valueOf(venteAeffectuer.get("annonce")), annonceRepository);
            // 1 - verifier le solde du client
            double soldeClient= transactionsRepository.getSoldeClient(utilisateur);
            if(annonce.getPrixVehiculeAvecCommission()>soldeClient){
                returnValue.put("erreur","solde insuffisant");
                returnValue.put("statut",404);
                returnValue.put("donnee",null);
                return returnValue;
            }
            else{
                //effectuer la transaction
                //=> (-) compte acheteur =>(+) compte vendeur => (+) commission
                // 1-compte acheteur
                String descriptif="achat du vehicule n* "+annonce.getVehicule().getId_vehicule()+" immatricule :"+annonce.getVehicule().getImmatricule() +"Annonce :"+annonce.getId_annonce();
                Transactions transactionsAcheteur=new Transactions(utilisateur,annonce,-1,descriptif,annonce.getPrixVehiculeAvecCommission(),sequenceRepository);
                transactionsRepository.save(transactionsAcheteur);

                // compte vendeur
                descriptif="achat du vehicule n* "+annonce.getVehicule().getId_vehicule()+" immatricule :"+annonce.getVehicule().getImmatricule() +"Annonce :"+annonce.getId_annonce();
                Transactions transactionVendeur=new Transactions(annonce.getUtilisateur(),annonce,1,descriptif,annonce.getPrix(),sequenceRepository);
                transactionsRepository.save(transactionVendeur);

                //enregistrement des commissions
                double commission=annonce.getTotalCommission();
                Commissions commissions=new Commissions(commission,annonce,sequenceRepository);
                commissionsRepository.save(commissions);

                // declarer un vehicule comme vendu
                annonceRepository.vendreAnnonce(annonce.getId_annonce());

                // enregistrer dans la table vendeur
                VenteAnnonce venteAnnonce= new VenteAnnonce(annonce,utilisateur,sequenceRepository);
                venteAnnonceRepository.save(venteAnnonce);

                returnValue.put("erreur",null);
                returnValue.put("statut",404);
                returnValue.put("donnee","vendu avec succes");
            }

        }catch (Exception e){
            returnValue.put("erreur",e.getMessage());
            returnValue.put("statut",404);
            returnValue.put("donnee",null);
            return returnValue;
        }
        return returnValue;

    }
    @GetMapping("/getAnnonceById/{id}")
    public Object getAnnonceById(@PathVariable("id") String id)  {
        HashMap<String,Object> hashMap=new HashMap<>();
        try {
            HashMap<String,Object> sybHashmap=new HashMap<>();
            Annonce annonce = Annonce.getAnnonceById(id, annonceRepository);
            annonce.setInFavorites(annonce.checkIFInFavorites(favoriRepository));
            List<Image> images =imageRepository.getImageByAnnonce(annonce) ;
            List<String> imageUrls = images.stream().map(Image::getImageUrl).collect(Collectors.toList());
            sybHashmap.put("annonce",annonce.getAnnoncemodifie());
            sybHashmap.put("imageUrl",imageUrls);
            hashMap.put("donnee",sybHashmap);
            hashMap.put("statut",200);

        }catch (Exception e){
            hashMap.put("erreur",e.getMessage());
        }

        return hashMap;
    }

    @PostMapping("/image")
    public Object insertimage(@RequestBody HashMap<String, Object> img) throws Exception {
        HashMap<String, Object> returnType = new HashMap<>();

        try {
            Image image = new Image();
            String url = (String) img.get("imgUrl");

//            todo : ovaina an'le annonce efa nampidirina
            List<Annonce>annonceList=annonceRepository.findAll();

            Annonce annonce =annonceList.get(1);

            image.setImageUrl(url);
            image.setAnnonce(annonce);
            image.setId_image(sequenceRepository.getSequence(3, "IMG", image.getSequenceName()));
            imageRepository.save(image);
            returnType.put("statut", 200);

        } catch (Exception e) {
            returnType.put("erreur", e);
            returnType.put("statut", 404);

        }
        return returnType;
    }

    @GetMapping("/getimages")
    public Object getAllphototest() {
        List<Image> images =imageRepository.findAll() ;
        List<String> imageUrls = images.stream().map(Image::getImageUrl).collect(Collectors.toList());
        HashMap<String, Object> returnType = new HashMap<>();
        returnType.put("statut", 200);
        returnType.put("erreur", null);
        returnType.put("donnee", imageUrls);
        return returnType;
    }


    @GetMapping("/getimagesbyId/{id}")
    public Object getAllphotobyidannonce(@PathVariable String id) {
        Optional<Annonce> annonceOptional=annonceRepository.findById(id);
        Annonce annonce=new Annonce();
        if(annonceOptional.isPresent())
        {
            annonce=annonceOptional.get();
        }
        List<Image> images =imageRepository.getImageByAnnonce(annonce) ;
        List<String> imageUrls = images.stream().map(Image::getImageUrl).collect(Collectors.toList());
        HashMap<String, Object> returnType = new HashMap<>();
        returnType.put("statut", 200);
        returnType.put("erreur", null);
        returnType.put("donnee", imageUrls);
        return returnType;
    }

    @GetMapping("/getannoncesbyIduser/{id}")
    public Object getAllAnnonceParuser(@PathVariable String id) {
        HashMap<String, Object> returnType = new HashMap<>();

        Utilisateur utilisateur=null;
        Optional<Utilisateur> optionalUtilisateur=utilisateurRepository.findById(id);
        if(optionalUtilisateur.isPresent())
        {
            utilisateur=optionalUtilisateur.get();
        }

        List<Annonce> annonceList = annonceRepository.findByUtilisateur(utilisateur);
        for (int i = 0; i < annonceList.size(); i++) {
            annonceList.get(i).setInFavorites(annonceList.get(i).checkIFInFavorites(favoriRepository));
        };
        returnType.put("donnee", annonceList);
        returnType.put("statut", 200);
        returnType.put("erreur", null);
        return returnType;
    }
}
