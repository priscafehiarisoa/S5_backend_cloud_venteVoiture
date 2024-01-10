package gestionVehicules.model.chat;

import gestionVehicules.model.utilisateur.UtilisateurSimple;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Message {
    @Id
    @Column(name = "idMessage", nullable = false)
    private String id;

    private LocalDateTime dateEnvoiMessage;
    private UtilisateurSimple expediteur;
    private UtilisateurSimple recepteur;

}
