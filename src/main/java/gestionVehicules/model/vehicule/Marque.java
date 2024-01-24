package gestionVehicules.model.vehicule;

import gestionVehicules.repository.MarqueRepository;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.ToString;

import java.util.Optional;

@Entity
@ToString

public class Marque {
    @Id
    @Column(name = "id_marque", nullable = false)
    private String id_marque;
    private String nom_marque;

    public Marque() {

    }

    public String getNom_marque() {
        return nom_marque;
    }

    public void setNom_marque(String nom_marque) {
        this.nom_marque = nom_marque;
    }

    public String getId_marque() {
        return id_marque;
    }

    public void setId_marque(String id_marque) {
        this.id_marque = id_marque;
    }

    public Marque(String id_marque, String nom_marque) {
        this.setId_marque(id_marque);
        this.setNom_marque(nom_marque);
    }
    public Marque getMarqueById(String id, MarqueRepository marqueRepository) throws Exception {
        Optional<Marque> optional=marqueRepository.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }
        throw new Exception("le type de Marque avec un id : "+id+" n'existe pas");
    }

    public Marque(String nom_marque) {
        this.setNom_marque(nom_marque);
    }
}
