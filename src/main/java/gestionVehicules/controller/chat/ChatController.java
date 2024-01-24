package gestionVehicules.controller.chat;

import gestionVehicules.model.UtilisateurTest;
import gestionVehicules.repository.UtilisateurTestRepository;
import gestionVehicules.repository.chat.MessageRepository;
import gestionVehicules.model.chat.Message;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
@CrossOrigin()
@RestController
public class ChatController {

    private final MessageRepository messageRepository;
    private final UtilisateurTestRepository utilisateurTestRepository;

    public ChatController(MessageRepository messageRepository,
                          UtilisateurTestRepository utilisateurTestRepository) {
        this.messageRepository = messageRepository;
        this.utilisateurTestRepository = utilisateurTestRepository;
    }

    @PostMapping("/SendMessage")
    public Object getMessages (@RequestBody HashMap<String,String> messageEnvoye){
        // structure message :
            // "idExpediteur
            // "idRecepteur
            //  message
            //  token
        String idExpediteur= (String) messageEnvoye.get("idExpediteur");
        String idRecepteur= (String) messageEnvoye.get("idRecepteur");
        String messageEnvoyeString=(String) messageEnvoye.get("contenuMesssage");

        Message message= new Message(idExpediteur,idRecepteur,messageEnvoyeString);
        messageRepository.save(message);
        return "ok";
    }
    // structure json
    /*
    * {
    *   statuts:200,
    *   donnee:{}
    *   erreur:{}
    * }*/

    @GetMapping("/getSerndersName/{id}")
    public Object getSerndersName(@PathVariable String id){
        List<UtilisateurTest> listUtilisateur=Message.getListUserThatHaveSentAMessage(id,utilisateurTestRepository,messageRepository);
        HashMap<String,Object> returnType=new HashMap<>();
        returnType.put("donnee",listUtilisateur);
        returnType.put("statuts",200);
        returnType.put("errreur",null);
        return returnType;
    }

    /**
     * structure response :
     * idExpediteur
     * idRecepteur*/
    @PostMapping("/getConversation")
    public Object getConversation(@RequestBody HashMap<String,Object> response){
        List<Message> listeConversation= Message.getConversation(String.valueOf(response.get("idExpediteur")).trim(),String.valueOf(response.get("idRecepteur")).trim(),messageRepository);
        HashMap<String,Object> returnType=new HashMap<>();
        returnType.put("donnee",listeConversation);
        returnType.put("statuts",200);
        returnType.put("errreur",null);
        return returnType;
    }

}
