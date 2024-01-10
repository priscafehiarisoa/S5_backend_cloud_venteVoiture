package gestionVehicules.model.chat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class Message {
    @Id
    @Column(name = "idMessage", nullable = false)
    private String id;

    private LocalDateTime dateEnvoiMessage;
    private String  idExpediteur;
    private String  idRecepteur;

    // fonction get Expediteur
    // fonction get recepteur

}
