package gestionVehicules.model.chat;

import gestionVehicules.model.UtilisateurTest;
import gestionVehicules.model.user.Utilisateur;
import gestionVehicules.repository.user.UtilisateurRepository;
import gestionVehicules.repository.chat.MessageRepository;
import gestionVehicules.repository.user.UtilisateurRepository;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Data
@Document(collection = "Message")
public class Message {
    @Id
    private String id;
    private LocalDateTime dateEnvoiMessage;
    private String idExpediteur;
    private String idRecepteur;
    private String contenuMesssage;

    public Message(String id, LocalDateTime dateEnvoiMessage, String idExpediteur, String idRecepteur, String contenuMesssage) {
        this.id = id;
        this.dateEnvoiMessage = dateEnvoiMessage;
        this.idExpediteur = idExpediteur;
        this.idRecepteur = idRecepteur;
        this.contenuMesssage = contenuMesssage;
    }

    public Message(LocalDateTime dateEnvoiMessage, String idExpediteur, String idRecepteur, String contenuMesssage) {
        setId(null);
        setDateEnvoiMessage(dateEnvoiMessage);
        setIdExpediteur(idExpediteur);
        setIdRecepteur(idRecepteur);
        setContenuMesssage(contenuMesssage);
    }
    public Message( String idExpediteur, String idRecepteur, String contenuMesssage) {
        setId(null);
        setDateEnvoiMessage(LocalDateTime.now());
        setIdExpediteur(idExpediteur);
        setIdRecepteur(idRecepteur);
        setContenuMesssage(contenuMesssage);
    }

    public Message() {
    }
    // fonction get Expediteur
    // fonction get recepteur

    public static List<String> getSenderReceiverIds(String s1Id, MessageRepository messageRepository) {
        List<Message> messages = messageRepository.findSenderReceiverIds(s1Id);

        List<String> senderReceiverIds = new ArrayList<>();
        for (Message message : messages) {
            senderReceiverIds.add(message.getIdExpediteur());
            senderReceiverIds.add(message.getIdRecepteur());
        }

        senderReceiverIds=senderReceiverIds.stream().distinct().collect(Collectors.toList());
        for (int i = 0; i < senderReceiverIds.size(); i++) {
            if(senderReceiverIds.get(i).equals(s1Id)){
                senderReceiverIds.remove(i);
            }
        }
        return senderReceiverIds ;
    }
    public static List<Utilisateur> getListUsers(List<String> ids, UtilisateurRepository utilisateurRepository){
        List<Utilisateur> utilisateurTests=new ArrayList<>();
        for (int i = 0; i < ids.size(); i++) {
            Optional<Utilisateur> utilisateurTestOptional=utilisateurRepository.findById(ids.get(i));
            if(utilisateurTestOptional.isPresent()){
                utilisateurTests.add(utilisateurTestOptional.get());
            }
        }
        return utilisateurTests;
    }

    public static List<Utilisateur> getListUserThatHaveSentAMessage(String idCurrentUser,UtilisateurRepository utilisateurRepository,MessageRepository messageRepository)
    {
        List<String> listUtilisateurIds=Message.getSenderReceiverIds(idCurrentUser,messageRepository);
        List<Utilisateur> listeUtilisateur=getListUsers(listUtilisateurIds,utilisateurRepository);
        return listeUtilisateur;
    }
    public static List<Message> getConversation(String idUtilisateur1, String idUtilisateur2,MessageRepository messageRepository) {
        return messageRepository.findByIdExpediteurAndIdRecepteurOrIdExpediteurAndIdRecepteurOrderByDateEnvoiMessage(idUtilisateur1,idUtilisateur2,idUtilisateur2,idUtilisateur1);
    }

}
