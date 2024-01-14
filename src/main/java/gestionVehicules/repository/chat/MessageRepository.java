package gestionVehicules.repository.chat;

import gestionVehicules.model.chat.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String> {

    @Query(value = "{ $or: [ { 'idRecepteur': ?0 }, { 'idExpediteur': ?0 } ] }", fields = "{'idExpediteur' : 1, 'idRecepteur' : 1}")
    List<Message> findSenderReceiverIds(String s1Id);

    List<Message> findByidRecepteurOrIdExpediteur(String idRecepteur, String idExpediteur);

    List<Message> findByIdExpediteurAndIdRecepteurOrIdExpediteurAndIdRecepteurOrderByDateEnvoiMessage(String idExpediteur, String idRecepteur, String idRecepteur2, String idExpediteur2);
}
 // Additional custom queries can be added here if needed
