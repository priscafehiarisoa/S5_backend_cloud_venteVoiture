package gestionVehicules.model.vehicule;

import gestionVehicules.repository.ModeleRepository;
import jakarta.persistence.*;
import lombok.ToString;

import javax.swing.text.html.Option;
import java.util.Optional;

@Entity
@ToString

public class Modele {
    @Id
    @Column(name = "id_modele", nullable = false)
    private String id_modele;

    private String nom_modele;
    @ManyToOne
    @JoinColumn(name = "id_marque")
    private Marque marque;

    public Marque getMarque() {
        return marque;
    }

    public void setMarque(Marque marque) {
        this.marque = marque;
    }

    public Modele() {

    }

    public String getNom_modele() {
        return nom_modele;
    }

    public void setNom_modele(String nom_modele) {
        this.nom_modele = nom_modele;
    }

    public String getId_modele() {
        return id_modele;
    }

    public void setId_modele(String id_modele) {
        this.id_modele = id_modele;
    }

    public Modele(String id_modele, String nom_modele,Marque marque) {
        this.setId_modele(id_modele);
        this.setNom_modele(nom_modele);
        this.setMarque(marque);
    }

    public Modele(String nom_modele,Marque marque) {
        this.setNom_modele(nom_modele);
        this.setMarque(marque);

    }

    public Modele getModeleById(String id, ModeleRepository modeleRepository) throws Exception {
        Optional<Modele> optionalModele=modeleRepository.findById(id);
        if(optionalModele.isPresent()){
            return optionalModele.get();
        }
        throw new Exception("le modele avec un id : "+id+" n'existe pas");
    }
}
