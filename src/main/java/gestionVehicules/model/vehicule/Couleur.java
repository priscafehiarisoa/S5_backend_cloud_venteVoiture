package gestionVehicules.model.vehicule;

import gestionVehicules.repository.CouleurRepository;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.ToString;

import java.util.Optional;

@Entity
@ToString

public class Couleur {
    @Id
    @Column(name = "id_couleur", nullable = false)
    private String id_couleur;

    private String nom_couleur;

    public Couleur() {

    }

    public String getNom_couleur() {
        return nom_couleur;
    }

    public void setNom_couleur(String nom_couleur) {
        this.nom_couleur = nom_couleur;
    }

    public String getId_couleur() {
        return id_couleur;
    }

    public void setId_couleur(String id_couleur) {
        this.id_couleur = id_couleur;
    }

    public Couleur(String id_couleur, String nom_couleur) {
        this.setId_couleur(id_couleur);
        this.setNom_couleur(nom_couleur);
    }
    public Couleur getCouleurById(String id, CouleurRepository couleurRepository) throws Exception {
        Optional<Couleur> optional=couleurRepository.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }
        throw new Exception("le type de Couleur avec un id : "+id+" n'existe pas");
    }

    public Couleur(String nom_couleur) {
        this.setNom_couleur(nom_couleur);
    }

}
