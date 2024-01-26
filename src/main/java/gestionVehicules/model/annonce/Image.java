package gestionVehicules.model.annonce;

import gestionVehicules.model.user.Utilisateur;
import jakarta.persistence.*;

@Entity
public class Image {
    @Id
    @Column(name = "id_image", nullable = false)
    private String id_image;
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "id_annonce")
    private Annonce annonce;

    public Image() {

    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Annonce getAnnonce() {
        return annonce;
    }

    public void setAnnonce(Annonce annonce) {
        this.annonce = annonce;
    }

    public String getId_image() {
        return id_image;
    }

    public void setId_image(String id_image) {
        this.id_image = id_image;
    }

    public String getPrefixes(){
        return "IMG";
    }
    public String getSequenceName(){
        return "image_seq";
    }

    public Image(String imageUrl, Annonce annonce) {
        this.setImageUrl(imageUrl);
        this.setAnnonce(annonce);
    }

    public Image(String id_image, String imageUrl, Annonce annonce) {
        this.setId_image(id_image);
        this.setImageUrl(imageUrl);
        this.setAnnonce(annonce);
    }
}
