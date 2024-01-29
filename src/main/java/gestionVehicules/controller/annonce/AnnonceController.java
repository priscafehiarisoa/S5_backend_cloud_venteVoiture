package gestionVehicules.controller.annonce;

import gestionVehicules.model.annonce.Annonce;
import gestionVehicules.model.annonce.CommissionAnnonce;
import gestionVehicules.model.annonce.Image;
import gestionVehicules.model.transactionsBanquaires.Commissions;
import gestionVehicules.model.transactionsBanquaires.Transactions;
import gestionVehicules.model.user.Utilisateur;
import gestionVehicules.model.vehicule.Couleur;
import gestionVehicules.model.vehicule.Vehicule;
import gestionVehicules.repository.CommissionAnnonceRepository;
import gestionVehicules.repository.CommissionsRepository;
import gestionVehicules.repository.TransactionsRepository;
import gestionVehicules.repository.annonce.AnnonceRepository;
import gestionVehicules.repository.annonce.ImageRepository;
import gestionVehicules.repository.sequence.SequenceRepository;
import gestionVehicules.repository.user.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
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
    private ImageRepository imageRepository;

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
        returnType.put("donnee", annonceList);
        returnType.put("statut", 200);
        returnType.put("erreur", null);
        return returnType;
    }

    @GetMapping("/getAnnoncesEnCoursDeValidation")
    public Object getAnnoncesEnCoursDeValidation() {
        List<Annonce> annonceList = annonceRepository.getAnnoncesEnCoursDeValidation();
        HashMap<String, Object> returnType = new HashMap<>();
        returnType.put("statut", 200);
        returnType.put("erreur", null);
        returnType.put("donnee", annonceList);
        return returnType;
    }

    @GetMapping("/getAnnoncesValidees")
    public Object getAnnoncesValidees() {
        List<Annonce> annonceList = annonceRepository.getAnnoncesValidees();
        HashMap<String, Object> returnType = new HashMap<>();
        returnType.put("statut", 200);
        returnType.put("erreur", null);
        returnType.put("donnee", annonceList);
        return returnType;
    }


    @GetMapping("/getAnnoncesVendues")
    public Object getAnnoncesVendues() {
        List<Annonce> annonceList = annonceRepository.getAnnoncesVendues();
        HashMap<String, Object> returnType = new HashMap<>();
        returnType.put("statut", 200);
        returnType.put("erreur", null);
        returnType.put("donnee", annonceList);
        return returnType;

    }

    @GetMapping("/getAnnoncesRefusees")
    public Object getAnnoncesRefusees() {
        List<Annonce> annonceList = annonceRepository.getAnnoncesRefusees();
        HashMap<String, Object> returnType = new HashMap<>();
        returnType.put("statut", 200);
        returnType.put("erreur", null);
        returnType.put("donnee", annonceList);
        return returnType;
    }

    @PutMapping("/validerAnnonce/{id}")
    public Object validerAnnonce(@PathVariable String id) {
        annonceRepository.validerAnnonce(id);
        HashMap<String, Object> returnType = new HashMap<>();
        returnType.put("statut", 200);
        returnType.put("erreur", null);
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
    public Object refuserAnnonce(@PathVariable String id) {
        annonceRepository.refuserAnnonce(id);
        HashMap<String, Object> returnType = new HashMap<>();
        returnType.put("statut", 200);
        returnType.put("erreur", null);
        return returnType;
    }

    @PutMapping("/vendreAnnonce/{id}")
    public Object vendreAnnonce(@PathVariable String id) {
        annonceRepository.vendreAnnonce(id);
        HashMap<String, Object> returnType = new HashMap<>();
        returnType.put("statut", 200);
        returnType.put("erreur", null);
        return returnType;
    }

    @Transactional
    public Object venteVehicule(@RequestBody HashMap<String, Object> venteAeffectuer) {
        HashMap<String, Object> returnValue = new HashMap<>();
        try {
            // set datas
            Utilisateur utilisateur = Utilisateur.getOptionalUserById(String.valueOf(venteAeffectuer.get("utilisateur")), utilisateurRepository);
            Annonce annonce = Annonce.getAnnonceById(String.valueOf(venteAeffectuer.get("annonce")), annonceRepository);
            // 1 - verifier le solde du client
            double soldeClient = transactionsRepository.getSoldeClient(utilisateur);
            if (annonce.getPrixVehiculeAvecCommission() > soldeClient) {
                returnValue.put("erreur", "solde insuffisant");
                returnValue.put("statut", 404);
                returnValue.put("donnee", null);
                return returnValue;
            } else {
                //effectuer la transaction
                //=> (-) compte acheteur =>(+) compte vendeur => (+) commission
                // 1-compte acheteur
                String descriptif = "achat du vehicule n* " + annonce.getVehicule().getId_vehicule() + " immatricule :" + annonce.getVehicule().getImmatricule() + "Annonce :" + annonce.getId_annonce();
                Transactions transactionsAcheteur = new Transactions(utilisateur, annonce, -1, descriptif, annonce.getPrixVehiculeAvecCommission(), sequenceRepository);
                transactionsRepository.save(transactionsAcheteur);

                // compte vendeur
                descriptif = "achat du vehicule n* " + annonce.getVehicule().getId_vehicule() + " immatricule :" + annonce.getVehicule().getImmatricule() + "Annonce :" + annonce.getId_annonce();
                Transactions transactionVendeur = new Transactions(annonce.getUtilisateur(), annonce, 1, descriptif, annonce.getPrix(), sequenceRepository);
                transactionsRepository.save(transactionVendeur);

                //enregistrement des commissions
                double commission = annonce.getTotalCommission();
                Commissions commissions = new Commissions(commission, annonce, sequenceRepository);
                commissionsRepository.save(commissions);

                // declarer un vehicule comme vendu
                annonceRepository.vendreAnnonce(annonce.getId_annonce());

                returnValue.put("erreur", null);
                returnValue.put("statut", 404);
                returnValue.put("donnee", "vendu avec succes");
            }

        } catch (Exception e) {
            returnValue.put("erreur", e.getMessage());
            returnValue.put("statut", 404);
            returnValue.put("donnee", null);
            return returnValue;
        }
        return returnValue;

    }

    @PostMapping("/image")
    public Object insertimage(@RequestBody HashMap<String, Object> img) throws Exception {
        HashMap<String, Object> returnType = new HashMap<>();

        try {
            Image image = new Image();
            String url = (String) img.get("imgUrl");
            List<Annonce>annonceList=annonceRepository.findAll();

            Annonce annonce =annonceList.get(0);

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
}
