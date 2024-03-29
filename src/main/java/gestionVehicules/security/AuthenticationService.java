package gestionVehicules.security;

import gestionVehicules.model.user.Role;
import gestionVehicules.model.user.Utilisateur;
import gestionVehicules.repository.sequence.SequenceRepository;
import gestionVehicules.repository.user.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final SequenceRepository sequenceRepository;

    public AuthenticationResponse register(RegisterRequest request) throws Exception {
//        todo : tester si l'utilisateur existe avant de s'enregistrer
        try{
            Optional<Utilisateur> utilisateur= utilisateurRepository.findByEmail(request.getEmail());
            if(utilisateur.isPresent()){
                throw new Exception("un email est déjà inscrit sous cet email");
            }
                int sequence = utilisateurRepository.getNextval();
                var user = Utilisateur.builder()
                        .id_user(sequenceRepository.getSequence(3,"USR",new Utilisateur().getSequenceName()))
                        .nom(request.getNom())
                        .prenom(request.getPrenom())
                        .date_naissance(request.getDate_naissance())
                        .email(request.getEmail())
                        .phone(request.getPhone())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .role(Role.USER)
                        .build();
                utilisateurRepository.save(user);
                var jwt = jwtService.generateToken(user);
                return AuthenticationResponse.builder()
                        .token(jwt)
                        .build();

            }catch (Exception e){
            throw e;
        }

    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
        var user = utilisateurRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwt)
                .userId(user.getId_user())
                .build();
    }
}
