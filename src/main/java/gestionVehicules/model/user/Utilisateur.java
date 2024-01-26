package gestionVehicules.model.user;

import gestionVehicules.repository.user.UtilisateurRepository;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter

public class Utilisateur implements UserDetails {
    @Id
    @Column(name="id_user" , nullable = false)
    private String id_user;
    @NotBlank(message = "Le nom ne peut pas être vide")
    @NotNull(message = "Le nom est obligatoire")
    private String nom;
    @NotBlank(message = "Le prénom ne peut pas être vide")
    @NotNull(message = "Le prénom est obligatoire")
    private String prenom;

    @Past(message = "La date de naissance que vous avez entré ne peut pas être valide ")
    private Date date_naissance;

    @Pattern(regexp = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]{2,}\\.[a-z]{2,4}$", message = "Cet email n'est pas valide")
    @NotNull(message = "L'email est un champ obligatoire")
    @NotBlank(message = "L'email ne doit pas être vide")
    private String email;

    @NotNull(message = "Le numéro de télephone est un champ obligatoire")
    @NotBlank(message = "Le numéro de télephone ne doit pas être vide")
    @Pattern(regexp = "^03[0-9] [0-9]{2} [0-9]{3} [0-9]{2}$", message = "Le numéro de télephone doit correspondre au format suivant 03xx xx xxx xx")
    private String phone;

    @Size(min = 8, message = "le mot de passe doit contenir au moins 8 caractères")
    @NotNull(message = "Le mot de passe est obligatoire")
    @NotBlank(message = "Le mot de passe ne peut pas être vide")
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public static Utilisateur getOptionalUserById(String id,UtilisateurRepository utilisateurRepository) throws Exception {
        Optional<Utilisateur> optionalUtilisateur = utilisateurRepository.findById(id);
        if(optionalUtilisateur.isPresent()){
            return optionalUtilisateur.get();
        }
        throw new Exception("utilisateur inexistant");
    }


    public HashMap<String,Object> getNometPrenomIdUtilisateur(){
        HashMap<String,Object> utilisateur=new HashMap<>();
        utilisateur.put("id_user",this.getId_user());
        utilisateur.put("nom",this.getNom());
        utilisateur.put("prenom",this.getPrenom());
        return utilisateur;

    }
}
