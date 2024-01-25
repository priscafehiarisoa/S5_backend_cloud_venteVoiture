package gestionVehicules.model.vehicule;

import jakarta.persistence.*;
import gestionVehicules.repository.BoiteRepository;
import gestionVehicules.repository.ModeleRepository;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.ToString;

import java.util.Optional;

@Entity
@ToString

@SequenceGenerator(name = "boite_seq_g", sequenceName = "boite_seq", allocationSize = 1)

public class Boite {
    @Id
    @Column(name = "id_boite", nullable = false)

    private String id_boite;
    private String nom_boite;

    public Boite() {

    }

    public String getNom_boite() {
        return nom_boite;
    }

    public void setNom_boite(String nom_boite) {
        this.nom_boite = nom_boite;
    }

    public String getId_boite() {
        return id_boite;
    }

    public void setId_boite(String id_boite) {
        this.id_boite = id_boite;
    }

    public Boite(String id_boite, String nom_boite) {
        this.setId_boite(id_boite);
        this.setNom_boite(nom_boite);
    }

    public Boite(String nom_boite) {
        this.setNom_boite(nom_boite);
    }
    public Boite getBoiteById(String id, BoiteRepository boiteRepository) throws Exception {
        Optional<Boite> optional=boiteRepository.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }
        throw new Exception("le type de boite avec un id : "+id+" n'existe pas");
    }
    public static String getSequenceName(){
        return "boite_seq";
    }
}
