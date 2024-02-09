package gestionVehicules.controller.transaction;

import gestionVehicules.model.transactionsBanquaires.Transactions;
import gestionVehicules.model.user.Utilisateur;
import gestionVehicules.repository.TransactionsRepository;
import gestionVehicules.repository.user.UtilisateurRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@CrossOrigin()
@RestController
public class TransactionsController {

    private final TransactionsRepository transactionsRepository;
    private final UtilisateurRepository utilisateurRepository;

    public TransactionsController(TransactionsRepository transactionsRepository,
                                  UtilisateurRepository utilisateurRepository) {
        this.transactionsRepository = transactionsRepository;
        this.utilisateurRepository = utilisateurRepository;
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
}
