package gestionVehicules.controller.transaction;

import gestionVehicules.model.annonce.Annonce;
import gestionVehicules.model.transactionsBanquaires.Transactions;
import gestionVehicules.model.user.Utilisateur;
import gestionVehicules.repository.TransactionsRepository;
import gestionVehicules.repository.annonce.AnnonceRepository;
import gestionVehicules.repository.sequence.SequenceRepository;
import gestionVehicules.repository.user.UtilisateurRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@CrossOrigin()
@RestController
public class TransactionsController {

    private final TransactionsRepository transactionsRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final AnnonceRepository annonceRepository;
    private final SequenceRepository sequenceRepository;

    public TransactionsController(TransactionsRepository transactionsRepository,
                                  UtilisateurRepository utilisateurRepository,
                                  AnnonceRepository annonceRepository,
                                  SequenceRepository sequenceRepository) {
        this.transactionsRepository = transactionsRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.annonceRepository = annonceRepository;
        this.sequenceRepository = sequenceRepository;
    }

    @GetMapping("/getSoldeUtilisateur/{id}")
    public ResponseEntity<HashMap<String,Object>> getSoldeUtilisateur(@PathVariable("id") String id){
        HashMap<String, Object> returningObject = new HashMap<>();
        try {
            Utilisateur utilisateur = Utilisateur.getOptionalUserById(id, utilisateurRepository);
            double solde = transactionsRepository.getSoldeClient(utilisateur);
            HashMap hashMap= new HashMap<>();
            hashMap.put("solde",solde);
            returningObject.put("donnee",hashMap);
            returningObject.put("statuts",200);

        }catch (Exception e){
            returningObject.put("statuts",404);
            returningObject.put("erreur","impossible de trouver l'utilisateur");
            return ResponseEntity.ok(returningObject);
        }
        return ResponseEntity.ok(returningObject);
    }

    @GetMapping("/getListeTransactions/{id}")
    public ResponseEntity<HashMap<String,Object>> getListeTransactions(@PathVariable("id") String id){
        HashMap<String, Object> returningObject = new HashMap<>();
        try {
            Utilisateur utilisateur = Utilisateur.getOptionalUserById(id, utilisateurRepository);
            List<Transactions> listeTransactions = transactionsRepository.getTransactionsByUtilisateurOrderByDateTransactionAsc(utilisateur);
            List<HashMap<String,Object>> transact= new ArrayList<>();
            for (int i = 0; i < listeTransactions.size(); i++) {
                transact.add(listeTransactions.get(i).getFormatedTRansaction());
            }
            HashMap hashMap= new HashMap<>();
            hashMap.put("transactions",transact);
            returningObject.put("donnee",hashMap);
            returningObject.put("statuts",200);
        }catch (Exception e){
            returningObject.put("statuts",404);
            returningObject.put("erreur","impossible de trouver l'utilisateur");
            return ResponseEntity.ok(returningObject);
        }
        return ResponseEntity.ok(returningObject);
    }

    @PostMapping("/approvisionner")
    public ResponseEntity<Object> addTransaction(@RequestBody HashMap<String,Object> body){
        String idUser= String.valueOf(body.get("user"));
        HashMap<String, Object> returningObject = new HashMap<>();

        double montant= Double.parseDouble(String.valueOf(body.get("montant")));
        String desctiption = "approvisionnement de compte";
        try {
            Annonce annonce= annonceRepository.findAll().get(0);
            Utilisateur utilisateur= Utilisateur.getOptionalUserById(idUser,utilisateurRepository);
            Transactions transactions = new Transactions(utilisateur,annonce,1,desctiption,montant,sequenceRepository);
            transactionsRepository.save(transactions);
        }catch (Exception e){
            returningObject.put("statuts",404);
            returningObject.put("erreur","une erreur est survenue");
        }
        return ResponseEntity.ok(returningObject);
    }
}
