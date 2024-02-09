package gestionVehicules.controller.chat;

import gestionVehicules.model.UtilisateurTest;
import gestionVehicules.model.user.Utilisateur;
import gestionVehicules.repository.UtilisateurTestRepository;
import gestionVehicules.repository.chat.MessageRepository;
import gestionVehicules.model.chat.Message;
import gestionVehicules.repository.user.UtilisateurRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@CrossOrigin("")
@RestController
public class ChatController {

    private final MessageRepository messageRepository;
    private final UtilisateurTestRepository utilisateurTestRepository;
    private final UtilisateurRepository utilisateurRepository;

    public ChatController(MessageRepository messageRepository,
                          UtilisateurTestRepository utilisateurTestRepository, UtilisateurRepository utilisateurRepository) {
        this.messageRepository = messageRepository;
        this.utilisateurTestRepository = utilisateurTestRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    @PostMapping("/SendMessage")
    public Object getMessages (@RequestBody HashMap<String,String> messageEnvoye){
        // structure message :
            // "idExpediteur
            // "idRecepteur
            //  message
            //  token
        try {
            String idExpediteur = (String) messageEnvoye.get("idExpediteur");
            String idRecepteur = (String) messageEnvoye.get("idRecepteur");
            String messageEnvoyeString = (String) messageEnvoye.get("contenuMesssage");
            Message message = new Message(idExpediteur, idRecepteur, messageEnvoyeString);
            messageRepository.save(message);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return "ok";
    }
    // structure json
    /*
    * {
    *   statut:200,
    *   donnee:{}
    *   erreur:{}
    * }*/

    @GetMapping("/getSerndersName/{id}")
    public Object getSerndersName(@PathVariable String id){
        List<Utilisateur> listUtilisateur=Message.getListUserThatHaveSentAMessage(id,utilisateurRepository,messageRepository);
        List<HashMap<String,Object>> userList= new ArrayList<>();
        for (int i = 0; i < listUtilisateur.size(); i++) {
            userList.add(listUtilisateur.get(i).formatUser());
        }

        HashMap<String,Object> returnType=new HashMap<>();
        returnType.put("donnee",userList);
        returnType.put("statut",200);
        returnType.put("erreur",null);
        return returnType;
    }

    /**
     * structure response :
     * idExpediteur
     * idRecepteur*/
    @PostMapping("/getConversation")
    public Object getConversation(@RequestBody HashMap<String,Object> response){
        HashMap<String, Object> returnType = new HashMap<>();
        try {
            List<Message> listeConversation = Message.getConversation(String.valueOf(response.get("idExpediteur")).trim(), String.valueOf(response.get("idRecepteur")).trim(), messageRepository);
            // réarranger les donnees de connexions
            Utilisateur expediteur= Utilisateur.getOptionalUserById(String.valueOf(response.get("idExpediteur")).trim(),utilisateurRepository);
            Utilisateur recepteur= Utilisateur.getOptionalUserById(String.valueOf(response.get("idRecepteur")).trim(),utilisateurRepository);
            HashMap<String, Object> setData = new HashMap<>();
            setData.put("expediteur",expediteur.formatUser());
            setData.put("recepteur",recepteur.formatUser());
            setData.put("messages",listeConversation);


            returnType.put("donnee", setData);
            returnType.put("statut", 200);
            returnType.put("erreur", null);
        }catch (Exception e){

        }
        return returnType;
    }

}
