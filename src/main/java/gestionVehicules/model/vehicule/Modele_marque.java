package gestionVehicules.model.vehicule;

import jakarta.persistence.*;

@Entity
public class Modele_marque {
    @Id
    @Column(name = "id_modele_marque", nullable = false)
    private String id_modele_marque;

    @ManyToOne
    @JoinColumn(name = "id_modele")
    private Modele modele;

    @ManyToOne
    @JoinColumn(name = "id_marque")
    private Marque marque;


    public String getId_modele_marque() {
        return id_modele_marque;
    }

    public void setId_modele_marque(String id_modele_marque) {
        this.id_modele_marque = id_modele_marque;
    }

    public Modele getModele() {
        return modele;
    }

    public void setModele(Modele modele) {
        this.modele = modele;
    }

    public Marque getMarque() {
        return marque;
    }

    public void setMarque(Marque marque) {
        this.marque = marque;
    }

    public Modele_marque(String id_modele_marque, Modele modele, Marque marque) {
        this.setId_modele_marque(id_modele_marque);
        this.setModele(modele);
        this.setMarque(marque);
    }

    public Modele_marque(Modele modele, Marque marque) {
        this.setModele(modele);
        this.setMarque(marque);
    }
}
