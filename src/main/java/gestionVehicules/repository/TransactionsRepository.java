package gestionVehicules.repository;

import gestionVehicules.model.transactionsBanquaires.Transactions;
import gestionVehicules.model.user.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionsRepository extends JpaRepository<Transactions, String> {
    @Query("select coalesce(sum(t.multiplicateur*t.montantTransaction),0) from Transactions t where t.utilisateur=:utilisateur")
    double getSoldeClient(Utilisateur utilisateur);

    List<Transactions> getTransactionsByUtilisateurOrderByDateTransactionAsc(Utilisateur utilisateur);
}