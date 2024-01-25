package gestionVehicules.model.vehicule;

import gestionVehicules.repository.CarburantRepository;
import gestionVehicules.repository.CategorieRepository;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.ToString;

import java.util.Optional;
import jakarta.persistence.*;

@Entity
@ToString

@SequenceGenerator(name = "categorie_seq_g", sequenceName = "categorie_seq", allocationSize = 1)

public class Categorie {
    @Id
    @Column(name = "id_categorie", nullable = false)

    private String id_categorie;
    private String nom_categorie;

    public Categorie() {

    }

    public String getNom_categorie() {
        return nom_categorie;
    }

    public void setNom_categorie(String nom_categorie) throws Exception {
        if (!nom_categorie.isEmpty()){
            this.nom_categorie = nom_categorie;

        }
        else {
            throw new Exception("le champ ne peut pas etre nul");
        }
    }

    public String getId_categorie() {
        return id_categorie;
    }

    public void setId_categorie(String id_categorie) {
        this.id_categorie = id_categorie;
    }

    public Categorie(String id_categorie, String nom_categorie) throws Exception {
        this.setId_categorie(id_categorie);
        this.setNom_categorie(nom_categorie);
    }
    public Categorie getCategorieById(String id, CategorieRepository categorieRepository) throws Exception {
        Optional<Categorie> optional=categorieRepository.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }
        throw new Exception("le type de Categorie avec un id : "+id+" n'existe pas");
    }

    public Categorie(String nom_categorie) throws Exception {
        this.setNom_categorie(nom_categorie);
    }
    public static String getSequenceName(){
        return "categorie_seq";
    }

}
