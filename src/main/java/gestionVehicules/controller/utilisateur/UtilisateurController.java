package gestionVehicules.controller.utilisateur;


import gestionVehicules.model.user.Utilisateur;
import gestionVehicules.repository.user.UtilisateurRepository;
import gestionVehicules.security.AuthenticationRequest;
import gestionVehicules.security.AuthenticationResponse;
import gestionVehicules.security.AuthenticationService;
import gestionVehicules.security.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
public class UtilisateurController {
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterRequest request) {
        AuthenticationResponse authenticationResponse=null;
        HashMap<String,Object> response=new HashMap<>();

        try
        {
            authenticationResponse=  authenticationService.register(request);
            return ResponseEntity.ok(authenticationResponse);

        }catch (Exception e){
            response.put("erreur",e.getMessage());
        }
        return ResponseEntity.ok(response);

    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse authenticationResponse=authenticationService.authenticate(request);
        return ResponseEntity.ok(authenticationResponse);
    }

    @PostMapping("/saveUser")
    public void saveUser(@RequestBody Utilisateur utilisateur) {
        int sequence = utilisateurRepository.getNextval();
        utilisateur.setId_user(utilisateurRepository.getSequence(5,"USR",sequence));
        utilisateurRepository.save(utilisateur);
    }

    @GetMapping("/users")
    public List<Utilisateur> getAllUser() {
        return utilisateurRepository.findAll();
    }
}
