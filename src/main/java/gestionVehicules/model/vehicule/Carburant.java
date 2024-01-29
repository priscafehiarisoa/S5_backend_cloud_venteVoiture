package gestionVehicules.model.vehicule;

import gestionVehicules.repository.BoiteRepository;
import gestionVehicules.repository.CarburantRepository;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.ToString;

import java.util.Optional;
import jakarta.persistence.*;

@Entity
@ToString

@SequenceGenerator(name = "carburant_seq_g", sequenceName = "carburant_seq", allocationSize = 1)

public class Carburant {
    @Id
    @Column(name = "id_carburant", nullable = false)

    private String id_carburant;
    private String nom_carburant;
    private int etat;

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }
    public void setEtat() {
        this.etat = 1;
    }

    public Carburant() {
    }

    public String getNom_carburant() {
        return nom_carburant;
    }

    public void setNom_carburant(String nom_carburant) {
        this.nom_carburant = nom_carburant;
    }

    public String getId_carburant() {
        return id_carburant;
    }

    public void setId_carburant(String id_carburant) {
        this.id_carburant = id_carburant;
    }

    public Carburant(String id_carburant, String nom_carburant) {
        this.setId_carburant(id_carburant);
        this.setNom_carburant(nom_carburant);
        this.setEtat();
    }

    public Carburant(String nom_carburant) {
        this.setNom_carburant(nom_carburant);
        this.setEtat();
    }
    public static String getSequenceName(){
        return "carburant_seq";
    }

    public Carburant getCarburantById(String id, CarburantRepository carburantRepository) throws Exception {
        Optional<Carburant> optional=carburantRepository.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }
        throw new Exception("le type de Carburant avec un id : "+id+" n'existe pas");
    }

}
