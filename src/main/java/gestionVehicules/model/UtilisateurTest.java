package gestionVehicules.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class UtilisateurTest {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    private String nom;

    public UtilisateurTest(String id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public UtilisateurTest() {
    }
}
