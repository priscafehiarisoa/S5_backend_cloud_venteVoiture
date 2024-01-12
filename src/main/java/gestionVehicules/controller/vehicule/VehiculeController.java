package gestionVehicules.controller.vehicule;

import gestionVehicules.repository.VehiculeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin()
@RestController
@RequestMapping("/vehicule")
public class VehiculeController {
    @Autowired
    VehiculeRepository vehiculeRepository;
}
