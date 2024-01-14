package gestionVehicules;

import gestionVehicules.model.UtilisateurTest;
import gestionVehicules.repository.UtilisateurTestRepository;
import gestionVehicules.repository.chat.MessageRepository;
import gestionVehicules.model.chat.Message;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Configuration
public class Main {
    @Bean
    CommandLineRunner commandLineRunner(MessageRepository messageRepository,
                                        UtilisateurTestRepository utilisateurTestRepository){
        return args -> {
            Message m1= new Message();
            m1.setIdExpediteur("1");
            m1.setIdRecepteur("2");
            m1.setDateEnvoiMessage(LocalDateTime.now());
            m1.setContenuMesssage("blablabla");
//            messageRepository.save(m1);

            List<Message> messageList=new ArrayList<>();
            messageList.add(new Message("2","1","coucou toi"));
            messageList.add(new Message("3","1","coucou 3"));
            messageList.add(new Message("2","1","coucou toi4"));
            messageList.add(new Message("4","1","coucou toi5"));
            messageList.add(new Message("2","1","coucou toi6"));
            messageList.add(new Message("6","1","coucou toi7"));
            messageList.add(new Message("2","1","coucou toi8"));
            messageList.add(new Message("5","1","coucou toi9"));

//            messageRepository.saveAll(messageList);
            Optional<Message> m2=messageRepository.findById("message1");
            if(m2.isPresent()){
                System.out.println(m2.get());
            }
//            System.out.println("distinct");
//            messageRepository.findSenderReceiverIds("2").forEach(System.out::println);
//            Message.getSenderReceiverIds("2",messageRepository).forEach(System.out::println);
//            System.out.println("message");
//            messageRepository.findByidRecepteurOrIdExpediteur("2","2").forEach(System.out::println);

            List<UtilisateurTest> users=new ArrayList<>();
            users.add(new UtilisateurTest("1","prisca"));
            users.add(new UtilisateurTest("2","Jessy"));
            users.add(new UtilisateurTest("3","Jeddy"));
            users.add(new UtilisateurTest("4","Mitantsoa"));
            users.add(new UtilisateurTest("5","Mendrika"));
            users.add(new UtilisateurTest("6","Rado"));
            utilisateurTestRepository.saveAll(users);

            Message.getConversation("1","2",messageRepository).forEach(System.out::println);
        };
    }
}
