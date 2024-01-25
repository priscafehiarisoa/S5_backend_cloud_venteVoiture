package gestionVehicules.model.vehicule;

import gestionVehicules.repository.MarqueRepository;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.ToString;

import java.util.Optional;
import jakarta.persistence.*;

@Entity
@ToString

@SequenceGenerator(name = "marque_seq_g", sequenceName = "marque_seq", allocationSize = 1)

public class Marque {
    @Id
    @Column(name = "id_marque", nullable = false)

    private String id_marque;
    private String nom_marque;
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

    public Marque() {

    }

    public String getNom_marque() {
        return nom_marque;
    }

    public void setNom_marque(String nom_marque) throws Exception {
        if(!nom_marque.isEmpty()){
            this.nom_marque = nom_marque;

        }
          else {
            throw new Exception("le champ ne peut pas etre nul");
        }
    }

    public String getId_marque() {
        return id_marque;
    }

    public void setId_marque(String id_marque) {
        this.id_marque = id_marque;
    }

    public Marque(String id_marque, String nom_marque) throws Exception {
        this.setId_marque(id_marque);
        this.setNom_marque(nom_marque);
        this.setEtat();
    }
    public Marque getMarqueById(String id, MarqueRepository marqueRepository) throws Exception {
        Optional<Marque> optional=marqueRepository.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }
        throw new Exception("le type de Marque avec un id : "+id+" n'existe pas");
    }

    public Marque(String nom_marque) throws Exception {
        this.setNom_marque(nom_marque);
        this.setEtat();
    }
    public static String getSequenceName(){
        return "marque_seq";
    }
}
