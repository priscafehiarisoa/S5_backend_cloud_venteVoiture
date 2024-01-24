package gestionVehicules;

import gestionVehicules.model.UtilisateurTest;
import gestionVehicules.model.vehicule.*;
import gestionVehicules.repository.*;
import gestionVehicules.repository.annonce.AnnonceRepository;
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
                                        UtilisateurTestRepository utilisateurTestRepository,
                                        BoiteRepository boiteRepository,
                                        CarburantRepository carburantRepository,
                                        CategorieRepository categorieRepository,
                                        CouleurRepository couleurRepository,
                                        MarqueRepository marqueRepository,
                                        ModeleRepository modeleRepository,
                                        MoteurRepository moteurRepository,
                                        PaysRepository paysRepository,
                                        VehiculeRepository vehiculeRepository,
                                        AnnonceRepository annonceRepository){
        return args -> {
//            Message m1= new Message();
//            m1.setIdExpediteur("1");
//            m1.setIdRecepteur("2");
//            m1.setDateEnvoiMessage(LocalDateTime.now());
//            m1.setContenuMesssage("blablabla");
////            messageRepository.save(m1);
//
//            List<Message> messageList=new ArrayList<>();
//            messageList.add(new Message("2","1","coucou toi"));
//            messageList.add(new Message("3","1","coucou 3"));
//            messageList.add(new Message("2","1","coucou toi4"));
//            messageList.add(new Message("4","1","coucou toi5"));
//            messageList.add(new Message("2","1","coucou toi6"));
//            messageList.add(new Message("6","1","coucou toi7"));
//            messageList.add(new Message("2","1","coucou toi8"));
//            messageList.add(new Message("5","1","coucou toi9"));
//
////            messageRepository.saveAll(messageList);
//            Optional<Message> m2=messageRepository.findById("message1");
//            if(m2.isPresent()){
//                System.out.println(m2.get());
//            }
////            System.out.println("distinct");
////            messageRepository.findSenderReceiverIds("2").forEach(System.out::println);
////            Message.getSenderReceiverIds("2",messageRepository).forEach(System.out::println);
////            System.out.println("message");
////            messageRepository.findByidRecepteurOrIdExpediteur("2","2").forEach(System.out::println);
//
//            List<UtilisateurTest> users=new ArrayList<>();
//            users.add(new UtilisateurTest("1","prisca"));
//            users.add(new UtilisateurTest("2","Jessy"));
//            users.add(new UtilisateurTest("3","Jeddy"));
//            users.add(new UtilisateurTest("4","Mitantsoa"));
//            users.add(new UtilisateurTest("5","Mendrika"));
//            users.add(new UtilisateurTest("6","Rado"));
//            utilisateurTestRepository.saveAll(users);
//
//            Message.getConversation("1","2",messageRepository).forEach(System.out::println);
            // donnees de tests
            List<Boite> boites = new ArrayList<>();
            boites.add(new Boite("B1", "Boite automatique"));
            boites.add(new Boite("B2", "Boite manuelle"));
            boiteRepository.saveAll(boites);

// Liste d'exemples pour la classe Carburant
            List<Carburant> carburants = new ArrayList<>();
            carburants.add(new Carburant("C1", "Essence"));
            carburants.add(new Carburant("C2", "Diesel"));
            carburantRepository.saveAll(carburants);

// Liste d'exemples pour la classe Categorie
            List<Categorie> categories = new ArrayList<>();
            categories.add(new Categorie("CAT1", "Compacte"));
            categories.add(new Categorie("CAT2", "Berline"));
            categorieRepository.saveAll(categories);

// Liste d'exemples pour la classe Couleur
            List<Couleur> couleurs = new ArrayList<>();
            couleurs.add(new Couleur("COU1", "Bleu"));
            couleurs.add(new Couleur("COU2", "Rouge"));
            couleurRepository.saveAll(couleurs);

// Liste d'exemples pour la classe Marque
            List<Marque> marques = new ArrayList<>();
            marques.add(new Marque("M1", "Toyota"));
            marques.add(new Marque("M2", "Honda"));
            marqueRepository.saveAll(marques);

// Liste d'exemples pour la classe Modele
            List<Modele> modeles = new ArrayList<>();
            modeles.add(new Modele("MOD1", "Corolla", marques.get(0)));
            modeles.add(new Modele("MOD2", "Civic", marques.get(1)));
            modeleRepository.saveAll(modeles);

// Liste d'exemples pour la classe Moteur
            List<Moteur> moteurs = new ArrayList<>();
            moteurs.add(new Moteur("MT1", "Essence 1.8L", 150.5));
            moteurs.add(new Moteur("MT2", "Diesel 2.0L", 180.0));
            moteurRepository.saveAll(moteurs);

// Liste d'exemples pour la classe Pays
            List<Pays> paysList = new ArrayList<>();
            paysList.add(new Pays("P1", "France"));
            paysList.add(new Pays("P2", "Allemagne"));
            paysRepository.saveAll(paysList);

// Liste d'exemples pour la classe Vehicule
            List<Vehicule> vehicules = new ArrayList<>();
            try {
                vehicules.add(new Vehicule("V1", "ABC123", 2022, 5000.0, 5, 1200.0, boites.get(0), carburants.get(0), categories.get(0), couleurs.get(0), modeles.get(0), moteurs.get(0), paysList.get(0)));
                vehicules.add(new Vehicule("V2", "XYZ456", 2020, 8000.0, 7, 1500.0, boites.get(1), carburants.get(1), categories.get(1), couleurs.get(1), modeles.get(1), moteurs.get(1), paysList.get(1)));
                vehiculeRepository.saveAll(vehicules);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // test recherche de vehicule

            annonceRepository.recherche_multi_critere_annonce(
                    List.of(marques.get(0)),
                    modeles,
                    0
                    ,1000000000,
                    LocalDateTime.now().minusDays(1),
                    LocalDateTime.now(),
                    1000,
                    2024,
                    100,
                    20000,
                    0,
                    100000000,
                    boites,
                    carburants,
                    couleurs,
                    moteurs,
                    paysList).forEach(System.out::println);

        };
    }
}
