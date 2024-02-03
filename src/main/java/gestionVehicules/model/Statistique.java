package gestionVehicules.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Statistique {
    int nombre;
    int moisOuAnnee;

    public Statistique(int nombre, int moisOuAnnee) {
        this.nombre = nombre;
        this.moisOuAnnee = moisOuAnnee;
    }
}
