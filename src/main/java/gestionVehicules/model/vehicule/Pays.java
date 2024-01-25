package gestionVehicules.model.vehicule;

import gestionVehicules.repository.PaysRepository;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.ToString;

import java.util.Optional;
import jakarta.persistence.*;

@Entity
@ToString

@SequenceGenerator(name = "pays_seq_g", sequenceName = "pays_seq", allocationSize = 1)
public class Pays {
    @Id
    @Column(name = "id_pays", nullable = false)

    private String id_pays;
    private String nom_pays;

    public Pays() {

    }

    public String getNom_pays() {
        return nom_pays;
    }

    public void setNom_pays(String nom_pays) throws Exception {
        if (!nom_pays.isEmpty()) {
            this.nom_pays = nom_pays;
        }
        else {
            throw new Exception("le champ ne peut pas etre vide");
        }
    }

    public String getId_pays() {
        return id_pays;
    }

    public void setId_pays(String id_pays) {
        this.id_pays = id_pays;
    }

    public Pays(String id_pays, String nom_pays) throws Exception {
        this.setId_pays(id_pays);
        this.setNom_pays(nom_pays);
    }
    public Pays getPaysById(String id, PaysRepository paysRepository) throws Exception {
        Optional<Pays> optional=paysRepository.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }
        throw new Exception("le type de Pays avec un id : "+id+" n'existe pas");
    }

    public Pays(String nom_pays) throws Exception {
        this.setNom_pays(nom_pays);
    }

    public static String getSequenceName(){
        return "pays_seq";
    }

}
